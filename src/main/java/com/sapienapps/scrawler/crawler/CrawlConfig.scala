/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sapienapps.scrawler.crawler


/**
 * @param crawlStorageFolder The folder which will be used by crawler for storing the intermediate
 *                           crawl data. The content of this folder should not be modified manually.
 * @param resumableCrawling If this feature is enabled, you would be able to resume a previously
 *                          stopped/crashed crawl. However, it makes crawling slightly slower
 * @param maxDepthOfCrawling Maximum depth of crawling For unlimited depth this parameter should be
 *                           set to -1
 * @param maxPagesToFetch Maximum number of pages to fetch For unlimited number of pages, this
 *                        parameter should be set to -1
 * @param userAgentString user-agent string that is used for representing your crawler to web
 *                        servers. See http://en.wikipedia.org/wiki/User_agent for more details
 * @param politenessDelay Politeness delay in milliseconds (delay between sending two requests to
 *                        the same host).
 * @param includeHttpsPages Should we also crawl https pages?
 * @param includeBinaryContentInCrawling Should we fetch binary content such as images, audio, ...?
 * @param maxConnectionsPerHost Maximum Connections per host
 * @param maxTotalConnections Maximum total connections
 * @param socketTimeout Socket timeout in milliseconds
 * @param connectionTimeout Connection timeout in milliseconds
 * @param maxOutgoingLinksToFollow Max number of outgoing links which are processed from a page
 * @param maxDownloadSize Max allowed size of a page. Pages larger than this size will not be
 *                        fetched.
 * @param followRedirects Should we follow redirects?
 * @param proxyHost If crawler should run behind a proxy, this parameter can be used for
 *                  specifying the proxy host.
 * @param proxyPort If crawler should run behind a proxy, this parameter can be used for
 *                  specifying the proxy port.
 * @param proxyUsername If crawler should run behind a proxy and user/pass is needed for
 *                      authentication in proxy, this parameter can be used for specifying the
 *                      username.
 * @param proxyPassword If crawler should run behind a proxy and user/pass is needed for
 *                      authentication in proxy, this parameter can be used for specifying the
 *                      password.
 */
case class CrawlConfig(crawlStorageFolder: String,
                       resumableCrawling: Boolean = false,
                       maxDepthOfCrawling: Int = -1,
                       maxPagesToFetch: Int = -1,
                       userAgentString: String = "crawler4j (http://code.google.com/p/crawler4j/)",
                       politenessDelay: Int = 200,
                       includeHttpsPages: Boolean = false,
                       includeBinaryContentInCrawling: Boolean = false,
                       maxConnectionsPerHost: Int = 100,
                       maxTotalConnections: Int = 100,
                       socketTimeout: Int = 20000,
                       connectionTimeout: Int = 30000,
                       maxOutgoingLinksToFollow: Int = 5000,
                       maxDownloadSize: Int = 1048576,
                       followRedirects: Boolean = true,
                       proxyHost: String = null,
                       proxyPort: Int = 80,
                       proxyUsername: String = null,
                       proxyPassword: String = null) {

  /**
   * Validates the configs specified by this instance.
   *
   * @throws Exception
   */
  def validate() {
    if (crawlStorageFolder == null) {
      throw new Exception("Crawl storage folder is not set in the CrawlConfig.")
    }
    if (politenessDelay < 0) {
      throw new Exception("Invalid value for politeness delay: " + politenessDelay)
    }
    if (maxDepthOfCrawling < -1) {
      throw new Exception("Maximum crawl depth should be either a positive number or -1 for unlimited depth.")
    }
    if (maxDepthOfCrawling > java.lang.Short.MAX_VALUE) {
      throw new Exception("Maximum value for crawl depth is " + java.lang.Short.MAX_VALUE)
    }
  }

  override def toString: String = {
    val sb: StringBuilder = new StringBuilder
    sb.append("Crawl storage folder: " + crawlStorageFolder + "\n")
    sb.append("Resumable crawling: " + resumableCrawling + "\n")
    sb.append("Max depth of crawl: " + maxDepthOfCrawling + "\n")
    sb.append("Max pages to fetch: " + maxPagesToFetch + "\n")
    sb.append("User agent string: " + userAgentString + "\n")
    sb.append("Include https pages: " + includeHttpsPages + "\n")
    sb.append("Include binary content: " + includeBinaryContentInCrawling + "\n")
    sb.append("Max connections per host: " + maxConnectionsPerHost + "\n")
    sb.append("Max total connections: " + maxTotalConnections + "\n")
    sb.append("Socket timeout: " + socketTimeout + "\n")
    sb.append("Max total connections: " + maxTotalConnections + "\n")
    sb.append("Max outgoing links to follow: " + maxOutgoingLinksToFollow + "\n")
    sb.append("Max download size: " + maxDownloadSize + "\n")
    sb.append("Should follow redirects?: " + followRedirects + "\n")
    sb.append("Proxy host: " + proxyHost + "\n")
    sb.append("Proxy port: " + proxyPort + "\n")
    sb.append("Proxy username: " + proxyUsername + "\n")
    sb.append("Proxy password: " + proxyPassword + "\n")
    sb.toString()
  }

}