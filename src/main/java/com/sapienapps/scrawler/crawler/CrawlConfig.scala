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

class CrawlConfig {

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

  /**
   * The folder which will be used by crawler for storing the intermediate
   * crawl data. The content of this folder should not be modified manually.
   */
  var crawlStorageFolder: String = null
  /**
   * If this feature is enabled, you would be able to resume a previously
   * stopped/crashed crawl. However, it makes crawling slightly slower
   */
  var resumableCrawling: Boolean = false
  /**
   * Maximum depth of crawling For unlimited depth this parameter should be
   * set to -1
   */
  var maxDepthOfCrawling: Int = -1
  /**
   * Maximum number of pages to fetch For unlimited number of pages, this
   * parameter should be set to -1
   */
  var maxPagesToFetch: Int = -1
  /**
   * user-agent string that is used for representing your crawler to web
   * servers. See http://en.wikipedia.org/wiki/User_agent for more details
   */
  var userAgentString: String = "crawler4j (http://code.google.com/p/crawler4j/)"
  /**
   * Politeness delay in milliseconds (delay between sending two requests to
   * the same host).
   */
  var politenessDelay: Int = 200
  /**
   * Should we also crawl https pages?
   */
  var includeHttpsPages: Boolean = false
  /**
   * Should we fetch binary content such as images, audio, ...?
   */
  var includeBinaryContentInCrawling: Boolean = false
  /**
   * Maximum Connections per host
   */
  var maxConnectionsPerHost: Int = 100
  /**
   * Maximum total connections
   */
  var maxTotalConnections: Int = 100
  /**
   * Socket timeout in milliseconds
   */
  var socketTimeout: Int = 20000
  /**
   * Connection timeout in milliseconds
   */
  var connectionTimeout: Int = 30000
  /**
   * Max number of outgoing links which are processed from a page
   */
  var maxOutgoingLinksToFollow: Int = 5000
  /**
   * Max allowed size of a page. Pages larger than this size will not be
   * fetched.
   */
  var maxDownloadSize: Int = 1048576
  /**
   * Should we follow redirects?
   */
  var followRedirects: Boolean = true
  /**
   * If crawler should run behind a proxy, this parameter can be used for
   * specifying the proxy host.
   */
  var proxyHost: String = null
  /**
   * If crawler should run behind a proxy, this parameter can be used for
   * specifying the proxy port.
   */
  var proxyPort: Int = 80
  /**
   * If crawler should run behind a proxy and user/pass is needed for
   * authentication in proxy, this parameter can be used for specifying the
   * username.
   */
  var proxyUsername: String = null
  /**
   * If crawler should run behind a proxy and user/pass is needed for
   * authentication in proxy, this parameter can be used for specifying the
   * password.
   */
  var proxyPassword: String = null
}