/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.play.bootstrap.filters.frontend.deviceid

import akka.stream.Materializer
import javax.inject.{Inject, Named}
import play.api.Configuration
import uk.gov.hmrc.play.audit.http.connector.AuditConnector

import scala.concurrent.ExecutionContext

class DefaultDeviceIdFilter @Inject()(
  @Named("appName") val appName: String,
  val configuration: Configuration,
  val auditConnector: AuditConnector
)(
  implicit
  override val mat: Materializer,
  override val ec: ExecutionContext)
    extends DeviceIdFilter {

  private val currentSecretKey  = "cookie.deviceId.secret"
  private val previousSecretKey = "cookie.deviceId.previous.secret"

  override lazy val secret: String =
    configuration.underlying.getString(currentSecretKey)

  override lazy val previousSecrets: Seq[String] =
    configuration.getOptional[Seq[String]](previousSecretKey).getOrElse(Seq.empty)

}
