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

package uk.gov.hmrc.libraryupgradeexamplefrontend

import play.api.Configuration

case class AppConfig (
  analyticsToken: String,
  analyticsHost: String,
  reportAProblemPartialUrl: String,
  reportAProblemNonJSUrl: String
  )

trait WithAppConfig {

  private val contactHost = configuration.getString(s"contact-frontend.host").getOrElse("")
  private val contactFormServiceIdentifier = "MyService"

  def configuration : Configuration

  implicit lazy val appConfig: AppConfig = AppConfig(
      analyticsToken = getRequiredConfigValue("google-analytics.token"),
      analyticsHost = getRequiredConfigValue("google-analytics.host"),
      reportAProblemPartialUrl = s"$contactHost/contact/problem_reports_ajax?service=$contactFormServiceIdentifier",
      reportAProblemNonJSUrl = s"$contactHost/contact/problem_reports_nonjs?service=$contactFormServiceIdentifier"
    )

  private def getRequiredConfigValue(key: String) = configuration.getString(key).getOrElse(throw new Exception(s"Missing configuration key: $key"))

}
