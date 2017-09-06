/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.libraryupgradeexamplefrontend.config

import play.api.Configuration
import uk.gov.hmrc.libraryupgradeexamplefrontend.{AppConfig, WithAppConfig}
import uk.gov.hmrc.play.test.UnitSpec

class WithAppConfigSpec extends UnitSpec {

  "Configuration object should be created if full configuration provided" in {
    val config = new WithAppConfig {
      override def configuration = Configuration(
        ("google-analytics.token", "TOKEN"),
        ("google-analytics.host", "HOST"),
        ("contact-frontend.host", "contact-frontend")
      )
    }

    config.appConfig shouldBe AppConfig(
      analyticsHost = "HOST",
      analyticsToken = "TOKEN",
      reportAProblemPartialUrl = "contact-frontend/contact/problem_reports_ajax?service=MyService",
      reportAProblemNonJSUrl = "contact-frontend/contact/problem_reports_nonjs?service=MyService"
    )
  }

  "Exception should be thrown if token property not provided" in {
    val config = new WithAppConfig {
      override def configuration = Configuration(
        ("google-analytics.host", "HOST")
      )
    }

    an [Exception] shouldBe thrownBy (config.appConfig)
  }

  "Exception should be thrown if host property not provided" in {
    val config = new WithAppConfig {
      override def configuration = Configuration(
        ("google-analytics.token", "TOKEN")
      )
    }

    an [Exception] shouldBe thrownBy (config.appConfig)
  }

  "Configuration object with relative links should be created if contact-frontend URL not proviced" in {
    val config = new WithAppConfig {
      override def configuration = Configuration(
        ("google-analytics.token", "TOKEN"),
        ("google-analytics.host", "HOST")
      )
    }

    config.appConfig shouldBe AppConfig(
      analyticsHost = "HOST",
      analyticsToken = "TOKEN",
      reportAProblemPartialUrl = "/contact/problem_reports_ajax?service=MyService",
      reportAProblemNonJSUrl = "/contact/problem_reports_nonjs?service=MyService"
    )
  }

}
