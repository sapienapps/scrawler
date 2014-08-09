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

  def getCrawlStorageFolder: String = crawlStorageFolder

  /**
   * The folder which will be used by crawler for storing the intermediate
   * crawl data. The content of this folder should not be modified manually.
   */
  def setCrawlStorageFolder(crawlStorageFolder: String) {
    this.crawlStorageFolder = crawlStorageFolder
  }

  def isResumableCrawling: Boolean = {
    resumableCrawling
  }

  /**
   * If this feature is enabled, you would be able to resume a previously
   * stopped/crashed crawl. However, it makes crawling slightly slower
   */
  def setResumableCrawling(resumableCrawling: Boolean) {
    this.resumableCrawling = resumableCrawling
  }

  def getMaxDepthOfCrawling: Int = {
    maxDepthOfCrawling
  }

  /**
   * Maximum depth of crawling For unlimited depth this parameter should be
   * set to -1
   */
  def setMaxDepthOfCrawling(maxDepthOfCrawling: Int) {
    this.maxDepthOfCrawling = maxDepthOfCrawling
  }

  def getMaxPagesToFetch: Int = {
    maxPagesToFetch
  }

  /**
   * Maximum number of pages to fetch For unlimited number of pages, this
   * parameter should be set to -1
   */
  def setMaxPagesToFetch(maxPagesToFetch: Int) {
    this.maxPagesToFetch = maxPagesToFetch
  }

  def getUserAgentString: String = {
    userAgentString
  }

  /**
   * user-agent string that is used for representing your crawler to web
   * servers. See http://en.wikipedia.org/wiki/User_agent for more details
   */
  def setUserAgentString(userAgentString: String) {
    this.userAgentString = userAgentString
  }

  def getPolitenessDelay: Int = {
    politenessDelay
  }

  /**
   * Politeness delay in milliseconds (delay between sending two requests to
   * the same host).
   *
   * @param politenessDelay
	 * the delay in milliseconds.
   */
  def setPolitenessDelay(politenessDelay: Int) {
    this.politenessDelay = politenessDelay
  }

  def isIncludeHttpsPages: Boolean = {
    includeHttpsPages
  }

  /**
   * Should we also crawl https pages?
   */
  def setIncludeHttpsPages(includeHttpsPages: Boolean) {
    this.includeHttpsPages = includeHttpsPages
  }

  def isIncludeBinaryContentInCrawling: Boolean = {
    includeBinaryContentInCrawling
  }

  /**
   * Should we fetch binary content such as images, audio, ...?
   */
  def setIncludeBinaryContentInCrawling(includeBinaryContentInCrawling: Boolean) {
    this.includeBinaryContentInCrawling = includeBinaryContentInCrawling
  }

  def getMaxConnectionsPerHost: Int = {
    maxConnectionsPerHost
  }

  /**
   * Maximum Connections per host
   */
  def setMaxConnectionsPerHost(maxConnectionsPerHost: Int) {
    this.maxConnectionsPerHost = maxConnectionsPerHost
  }

  def getMaxTotalConnections: Int = {
    maxTotalConnections
  }

  /**
   * Maximum total connections
   */
  def setMaxTotalConnections(maxTotalConnections: Int) {
    this.maxTotalConnections = maxTotalConnections
  }

  def getSocketTimeout: Int = {
    socketTimeout
  }

  /**
   * Socket timeout in milliseconds
   */
  def setSocketTimeout(socketTimeout: Int) {
    this.socketTimeout = socketTimeout
  }

  def getConnectionTimeout: Int = {
    connectionTimeout
  }

  /**
   * Connection timeout in milliseconds
   */
  def setConnectionTimeout(connectionTimeout: Int) {
    this.connectionTimeout = connectionTimeout
  }

  def getMaxOutgoingLinksToFollow: Int = {
    maxOutgoingLinksToFollow
  }

  /**
   * Max number of outgoing links which are processed from a page
   */
  def setMaxOutgoingLinksToFollow(maxOutgoingLinksToFollow: Int) {
    this.maxOutgoingLinksToFollow = maxOutgoingLinksToFollow
  }

  def getMaxDownloadSize: Int = {
    maxDownloadSize
  }

  /**
   * Max allowed size of a page. Pages larger than this size will not be
   * fetched.
   */
  def setMaxDownloadSize(maxDownloadSize: Int) {
    this.maxDownloadSize = maxDownloadSize
  }

  def isFollowRedirects: Boolean = {
    followRedirects
  }

  /**
   * Should we follow redirects?
   */
  def setFollowRedirects(followRedirects: Boolean) {
    this.followRedirects = followRedirects
  }

  def getProxyHost: String = {
    proxyHost
  }

  /**
   * If crawler should run behind a proxy, this parameter can be used for
   * specifying the proxy host.
   */
  def setProxyHost(proxyHost: String) {
    this.proxyHost = proxyHost
  }

  def getProxyPort: Int = {
    proxyPort
  }

  /**
   * If crawler should run behind a proxy, this parameter can be used for
   * specifying the proxy port.
   */
  def setProxyPort(proxyPort: Int) {
    this.proxyPort = proxyPort
  }

  def getProxyUsername: String = {
    proxyUsername
  }

  /**
   * If crawler should run behind a proxy and user/pass is needed for
   * authentication in proxy, this parameter can be used for specifying the
   * username.
   */
  def setProxyUsername(proxyUsername: String) {
    this.proxyUsername = proxyUsername
  }

  def getProxyPassword: String = {
    proxyPassword
  }

  /**
   * If crawler should run behind a proxy and user/pass is needed for
   * authentication in proxy, this parameter can be used for specifying the
   * password.
   */
  def setProxyPassword(proxyPassword: String) {
    this.proxyPassword = proxyPassword
  }

  override def toString: String = {
    val sb: StringBuilder = new StringBuilder
    sb.append("Crawl storage folder: " + getCrawlStorageFolder + "\n")
    sb.append("Resumable crawling: " + isResumableCrawling + "\n")
    sb.append("Max depth of crawl: " + getMaxDepthOfCrawling + "\n")
    sb.append("Max pages to fetch: " + getMaxPagesToFetch + "\n")
    sb.append("User agent string: " + getUserAgentString + "\n")
    sb.append("Include https pages: " + isIncludeHttpsPages + "\n")
    sb.append("Include binary content: " + isIncludeBinaryContentInCrawling + "\n")
    sb.append("Max connections per host: " + getMaxConnectionsPerHost + "\n")
    sb.append("Max total connections: " + getMaxTotalConnections + "\n")
    sb.append("Socket timeout: " + getSocketTimeout + "\n")
    sb.append("Max total connections: " + getMaxTotalConnections + "\n")
    sb.append("Max outgoing links to follow: " + getMaxOutgoingLinksToFollow + "\n")
    sb.append("Max download size: " + getMaxDownloadSize + "\n")
    sb.append("Should follow redirects?: " + isFollowRedirects + "\n")
    sb.append("Proxy host: " + getProxyHost + "\n")
    sb.append("Proxy port: " + getProxyPort + "\n")
    sb.append("Proxy username: " + getProxyUsername + "\n")
    sb.append("Proxy password: " + getProxyPassword + "\n")
    sb.toString
  }

  /**
   * The folder which will be used by crawler for storing the intermediate
   * crawl data. The content of this folder should not be modified manually.
   */
  private var crawlStorageFolder: String = null
  /**
   * If this feature is enabled, you would be able to resume a previously
   * stopped/crashed crawl. However, it makes crawling slightly slower
   */
  private var resumableCrawling: Boolean = false
  /**
   * Maximum depth of crawling For unlimited depth this parameter should be
   * set to -1
   */
  private var maxDepthOfCrawling: Int = -1
  /**
   * Maximum number of pages to fetch For unlimited number of pages, this
   * parameter should be set to -1
   */
  private var maxPagesToFetch: Int = -1
  /**
   * user-agent string that is used for representing your crawler to web
   * servers. See http://en.wikipedia.org/wiki/User_agent for more details
   */
  private var userAgentString: String = "crawler4j (http://code.google.com/p/crawler4j/)"
  /**
   * Politeness delay in milliseconds (delay between sending two requests to
   * the same host).
   */
  private var politenessDelay: Int = 200
  /**
   * Should we also crawl https pages?
   */
  private var includeHttpsPages: Boolean = false
  /**
   * Should we fetch binary content such as images, audio, ...?
   */
  private var includeBinaryContentInCrawling: Boolean = false
  /**
   * Maximum Connections per host
   */
  private var maxConnectionsPerHost: Int = 100
  /**
   * Maximum total connections
   */
  private var maxTotalConnections: Int = 100
  /**
   * Socket timeout in milliseconds
   */
  private var socketTimeout: Int = 20000
  /**
   * Connection timeout in milliseconds
   */
  private var connectionTimeout: Int = 30000
  /**
   * Max number of outgoing links which are processed from a page
   */
  private var maxOutgoingLinksToFollow: Int = 5000
  /**
   * Max allowed size of a page. Pages larger than this size will not be
   * fetched.
   */
  private var maxDownloadSize: Int = 1048576
  /**
   * Should we follow redirects?
   */
  private var followRedirects: Boolean = true
  /**
   * If crawler should run behind a proxy, this parameter can be used for
   * specifying the proxy host.
   */
  private var proxyHost: String = null
  /**
   * If crawler should run behind a proxy, this parameter can be used for
   * specifying the proxy port.
   */
  private var proxyPort: Int = 80
  /**
   * If crawler should run behind a proxy and user/pass is needed for
   * authentication in proxy, this parameter can be used for specifying the
   * username.
   */
  private var proxyUsername: String = null
  /**
   * If crawler should run behind a proxy and user/pass is needed for
   * authentication in proxy, this parameter can be used for specifying the
   * password.
   */
  private var proxyPassword: String = null
}