/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.uci.ics.crawler4j.fetcher;

import com.sapienapps.scrawler.crawler.Configurable;
import com.sapienapps.scrawler.crawler.CrawlConfig;
import com.sapienapps.scrawler.fetcher.CustomFetchStatus;
import com.sapienapps.scrawler.fetcher.PageFetchResult;
import edu.uci.ics.crawler4j.url.URLCanonicalizer;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.zip.GZIPInputStream;

/**
 * @author Yasser Ganjisaffar <lastname at gmail dot com>
 */
public class PageFetcher extends Configurable {

    protected static final Logger logger = Logger.getLogger(PageFetcher.class);

    protected PoolingHttpClientConnectionManager connectionManager;

    protected HttpClient httpClient;

    protected final Object mutex = new Object();

    protected long lastFetchTime = 0;

    protected IdleConnectionMonitorThread connectionMonitorThread = null;

    public PageFetcher(CrawlConfig config) {
        super(config);
        // This should be disabled to prevent false SSL Errors:
        // http://bugs.java.com/bugdatabase/view_bug.do?bug_id=7127374
        //System.setProperty ("jsse.enableSNIExtension", "false");

        RequestConfig requestConfig = RequestConfig.copy(RequestConfig.DEFAULT)
                .setSocketTimeout(config.socketTimeout())
                .setConnectTimeout(config.connectionTimeout())
                .setRedirectsEnabled(false)
                .setRelativeRedirectsAllowed(config.followRedirects())
                .setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();

        SSLConnectionSocketFactory https = new SSLConnectionSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault(),
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", https).build();

        connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connectionManager.setMaxTotal(config.maxTotalConnections());
        connectionManager.setDefaultMaxPerRoute(config.maxConnectionsPerHost());

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        if (config.proxyHost() != null) {
            if (config.proxyUsername() != null) {
                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(
                        new AuthScope(config.proxyHost(), config.proxyPort()),
                        new UsernamePasswordCredentials(config.proxyUsername(), config.proxyPassword()));
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
            HttpHost proxy = new HttpHost(config.proxyHost(), config.proxyPort());
            httpClientBuilder.setProxy(proxy);
        }

        HttpResponseInterceptor responseInterceptor = new HttpResponseInterceptor() {
            @Override
            public void process(final HttpResponse response, final HttpContext context) throws HttpException, IOException {
                HttpEntity entity = response.getEntity();
                Header contentEncoding = entity.getContentEncoding();
                if (contentEncoding != null) {
                    HeaderElement[] codecs = contentEncoding.getElements();
                    for (HeaderElement codec : codecs) {
                        if (codec.getName().equalsIgnoreCase("gzip")) {
                            response.setEntity(new GzipDecompressingEntity(response.getEntity()));
                            return;
                        }
                    }
                }
            }
        };

        httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.67 Safari/537.36")
                .addInterceptorLast(responseInterceptor).build();


        if (connectionMonitorThread == null) {
            connectionMonitorThread = new IdleConnectionMonitorThread(connectionManager);
        }
        connectionMonitorThread.start();
    }

    public PageFetchResult fetchHeader(WebURL webUrl) {
        PageFetchResult fetchResult = new PageFetchResult();
        String toFetchURL = webUrl.getURL();
        HttpGet get = null;
        try {
            get = new HttpGet(toFetchURL);
            synchronized (mutex) {
                long now = (new Date()).getTime();
                if (now - lastFetchTime < config().politenessDelay()) {
                    Thread.sleep(config().politenessDelay() - (now - lastFetchTime));
                }
                lastFetchTime = (new Date()).getTime();
            }
            get.addHeader("Accept-Encoding", "gzip");
            HttpResponse response = httpClient.execute(get);
            fetchResult.entity_$eq(response.getEntity());
            fetchResult.responseHeaders_$eq(response.getAllHeaders());

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                if (statusCode != HttpStatus.SC_NOT_FOUND) {
                    // Let's capture all Redirects:
                    //if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                    if (statusCode >= 300 && statusCode < 400) {
                        Header header = response.getFirstHeader("Location");
                        if (header != null) {
                            String movedToUrl = header.getValue();
                            movedToUrl = URLCanonicalizer.getCanonicalURL(movedToUrl, toFetchURL);
                            fetchResult.movedToUrl_$eq(movedToUrl);
                        }
                        fetchResult.statusCode_$eq(statusCode);
                        return fetchResult;
                    }
                    logger.info("Failed: " + response.getStatusLine().toString() + ", while fetching " + toFetchURL);
                }
                fetchResult.statusCode_$eq(response.getStatusLine().getStatusCode());
                return fetchResult;
            }

            fetchResult.fetchedUrl_$eq(toFetchURL);
            String uri = get.getURI().toString();
            if (!uri.equals(toFetchURL)) {
                if (!URLCanonicalizer.getCanonicalURL(uri).equals(toFetchURL)) {
                    fetchResult.fetchedUrl_$eq(uri);
                }
            }

            if (fetchResult.entity() != null) {
                long size = fetchResult.entity().getContentLength();
                if (size == -1) {
                    Header length = response.getLastHeader("Content-Length");
                    if (length == null) {
                        length = response.getLastHeader("Content-length");
                    }
                    if (length != null) {
                        size = Integer.parseInt(length.getValue());
                    } else {
                        size = -1;
                    }
                }
                if (size > config().maxDownloadSize()) {
                    fetchResult.statusCode_$eq(CustomFetchStatus.PageTooBig());
                    get.abort();
                    return fetchResult;
                }

                fetchResult.statusCode_$eq(HttpStatus.SC_OK);
                return fetchResult;

            }

            get.abort();

        } catch (IOException e) {
            logger.error("Fatal transport error: " + e.getMessage() + " while fetching " + toFetchURL
                    + " (link found in doc #" + webUrl.getParentDocid() + ")");
            fetchResult.statusCode_$eq(CustomFetchStatus.FatalTransportError());
            return fetchResult;
        } catch (IllegalStateException e) {
            // ignoring exceptions that occur because of not registering https
            // and other schemes
        } catch (Exception e) {
            if (e.getMessage() == null) {
                logger.error("Error while fetching " + webUrl.getURL());
            } else {
                logger.error(e.getMessage() + " while fetching " + webUrl.getURL());
            }
        } finally {
            try {
                if (fetchResult.entity() == null && get != null) {
                    get.abort();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        fetchResult.statusCode_$eq(CustomFetchStatus.UnknownError());
        return fetchResult;
    }

    public synchronized void shutDown() {
        if (connectionMonitorThread != null) {
            connectionManager.shutdown();
            connectionMonitorThread.shutdown();
        }
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    private static class GzipDecompressingEntity extends HttpEntityWrapper {

        public GzipDecompressingEntity(final HttpEntity entity) {
            super(entity);
        }

        @Override
        public InputStream getContent() throws IOException, IllegalStateException {

            // the wrapped entity's getContent() decides about repeatability
            InputStream wrappedin = wrappedEntity.getContent();

            return new GZIPInputStream(wrappedin);
        }

        @Override
        public long getContentLength() {
            // length of ungzipped content is not known
            return -1;
        }

    }
}
