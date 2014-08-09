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

import java.nio.charset.Charset

import edu.uci.ics.crawler4j.parser.ParseData
import edu.uci.ics.crawler4j.url.WebURL
import org.apache.http.{Header, HttpEntity}
import org.apache.http.entity.ContentType
import org.apache.http.util.EntityUtils

/**
 * This class contains the data for a fetched and parsed page.
 *
 * @author Yasser Ganjisaffar <lastname at gmail dot com>
 */
class Page() {

  /**
   * The URL of this page.
   */
  var url: WebURL = null
  /**
   * The content of this page in binary format.
   */
  var contentData: Array[Byte] = null
  /**
   * The ContentType of this page.
   * For example: "text/html; charset=UTF-8"
   */
  var contentType: String = null
  /**
   * The encoding of the content.
   * For example: "gzip"
   */
  var contentEncoding: String = null
  /**
   * The charset of the content.
   * For example: "UTF-8"
   */
  var contentCharset: String = null
  /**
   * Headers which were present in the response of the
   * fetch request
   */
  var fetchResponseHeaders: Array[Header] = null
  /**
   * The parsed data populated by parsers
   */
  var parseData: ParseData = null

  def this(url: WebURL) {
    this()
    this.url = url
  }

  /**
   * Loads the content of this page from a fetched
   * HttpEntity.
   */
  def load(entity: HttpEntity) {
    contentType = null
    val `type`: Header = entity.getContentType
    if (`type` != null) {
      contentType = `type`.getValue
    }
    contentEncoding = null
    val encoding: Header = entity.getContentEncoding
    if (encoding != null) {
      contentEncoding = encoding.getValue
    }
    val charset: Charset = ContentType.getOrDefault(entity).getCharset
    if (charset != null) {
      contentCharset = charset.displayName
    }
    contentData = EntityUtils.toByteArray(entity)
  }
}