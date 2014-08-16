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
package com.sapienapps.scrawler.fetcher

import java.io.{EOFException, IOException}

import com.sapienapps.scrawler.crawler.Page
import org.apache.http.util.EntityUtils
import org.apache.http.{Header, HttpEntity}
import org.apache.log4j.Logger

/**
 * @author Yasser Ganjisaffar <lastname at gmail dot com>
 */

class PageFetchResult {

  protected var statusCode: Int = 0
  protected var entity: HttpEntity = null
  protected var responseHeaders: Array[Header] = null
  protected var fetchedUrl: String = null
  protected var movedToUrl: String = null

  def fetchContent(page: Page): Boolean = {
    try {
      page.load(entity)
      page.fetchResponseHeaders_$eq(responseHeaders)
      return true
    }
    catch {
      case e: Exception => {
        PageFetchResult.logger.info("Exception while fetching content for: " + page.url.getURL + " [" + e.getMessage + "]")
      }
    }
    false
  }

  def discardContentIfNotConsumed() {
    try {
      if (entity != null) {
        EntityUtils.consume(entity)
      }
    } catch {
      case e: EOFException =>
      // We can ignore this exception. It can happen on compressed streams
      // which are not repeatable
      case e: IOException =>
      // We can ignore this exception. It can happen if the stream is
      // closed.
      case e: Exception =>
        e.printStackTrace()
    }
  }
}

object PageFetchResult {
  protected final val logger: Logger = Logger.getLogger(classOf[PageFetchResult])
}