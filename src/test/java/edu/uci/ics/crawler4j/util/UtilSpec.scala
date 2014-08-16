package edu.uci.ics.crawler4j.util

import org.specs2.mutable.Specification

/**
 * Created by jordanburke on 8/8/14.
 */
class UtilSpec extends Specification {

  "long2ByteArray" should {

    "convert 471977 into" in {
      Util.long2ByteArray(471977471977L) must equalTo(Array(0, 0, 0, 109, -28, 12, -81, -23))
    }

  }

  "int2ByteArray" should {

    "convert 471977 into" in {
      Util.int2ByteArray(471977) must equalTo(Array(0, 7, 51, -87))
    }

  }

}
