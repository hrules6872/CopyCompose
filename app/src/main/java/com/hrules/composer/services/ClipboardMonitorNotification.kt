/*
 * Copyright (c) 2019. Héctor de Isidro - hrules6872
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

package com.hrules.composer.services

import android.app.*
import android.content.Context
import android.os.Build.*
import androidx.core.app.NotificationCompat
import com.hrules.composer.R
import com.hrules.composer.R.string

object ClipboardMonitorNotification {
  const val ID = 6872
  private const val PRIMARY_CHANNEL = "default"

  fun create(
    context: Context
  ): Notification {
    createNotificationChannel(context)
    return NotificationCompat.Builder(context, PRIMARY_CHANNEL)
      .setSmallIcon(R.drawable.ic_notification)
      .build()
  }

  private fun createNotificationChannel(context: Context) {
    if (isApi26OrAbove()) {
      val notificationChannel = NotificationChannel(
        PRIMARY_CHANNEL,
        context.getString(string.app_name), NotificationManager.IMPORTANCE_MIN
      ).apply {
        lockscreenVisibility = Notification.VISIBILITY_PRIVATE
      }
      notificationManager(context).createNotificationChannel(notificationChannel)
    }
  }

  private fun isApi26OrAbove() = VERSION.SDK_INT >= VERSION_CODES.O
  private fun notificationManager(context: Context) = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
}