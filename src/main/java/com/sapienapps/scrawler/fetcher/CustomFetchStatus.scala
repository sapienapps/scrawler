/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"; you may not use this file except in compliance with
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

import org.apache.http.HttpStatus

/**
 * @author Yasser Ganjisaffar <lastname at gmail dot com>
 */
object CustomFetchStatus {
  def getStatusDescription(code: Int): String = {
    code match {
      case HttpStatus.SC_OK => "OK"
      case HttpStatus.SC_CREATED => "Created"
      case HttpStatus.SC_ACCEPTED => "Accepted"
      case HttpStatus.SC_NO_CONTENT => "No Content"
      case HttpStatus.SC_MOVED_PERMANENTLY => "Moved Permanently"
      case HttpStatus.SC_MOVED_TEMPORARILY => "Moved Temporarily"
      case HttpStatus.SC_NOT_MODIFIED => "Not Modified"
      case HttpStatus.SC_BAD_REQUEST => "Bad Request"
      case HttpStatus.SC_UNAUTHORIZED => "Unauthorized"
      case HttpStatus.SC_FORBIDDEN => "Forbidden"
      case HttpStatus.SC_NOT_FOUND => "Not Found"
      case HttpStatus.SC_INTERNAL_SERVER_ERROR => "Internal Server Error"
      case HttpStatus.SC_NOT_IMPLEMENTED => "Not Implemented"
      case HttpStatus.SC_BAD_GATEWAY => "Bad Gateway"
      case HttpStatus.SC_SERVICE_UNAVAILABLE => "Service Unavailable"
      case HttpStatus.SC_CONTINUE => "Continue"
      case HttpStatus.SC_TEMPORARY_REDIRECT => "Temporary Redirect"
      case HttpStatus.SC_METHOD_NOT_ALLOWED => "Method Not Allowed"
      case HttpStatus.SC_CONFLICT => "Conflict"
      case HttpStatus.SC_PRECONDITION_FAILED => "Precondition Failed"
      case HttpStatus.SC_REQUEST_TOO_LONG => "Request Too Long"
      case HttpStatus.SC_REQUEST_URI_TOO_LONG => "Request-URI Too Long"
      case HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE => "Unsupported Media Type"
      case HttpStatus.SC_MULTIPLE_CHOICES => "Multiple Choices"
      case HttpStatus.SC_SEE_OTHER => "See Other"
      case HttpStatus.SC_USE_PROXY => "Use Proxy"
      case HttpStatus.SC_PAYMENT_REQUIRED => "Payment Required"
      case HttpStatus.SC_NOT_ACCEPTABLE => "Not Acceptable"
      case HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED => "Proxy Authentication Required"
      case HttpStatus.SC_REQUEST_TIMEOUT => "Request Timeout"
      case PageTooBig => "Page size was too big"
      case FatalTransportError => "Fatal transport error"
      case UnknownError => "Unknown error"
      case _ => "(" + code + ")"
    }
  }

  final val PageTooBig: Int = 1001
  final val FatalTransportError: Int = 1005
  final val UnknownError: Int = 1006
}