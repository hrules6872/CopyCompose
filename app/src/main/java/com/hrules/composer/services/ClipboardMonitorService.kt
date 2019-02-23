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

import android.app.Service
import android.content.*
import android.os.IBinder
import android.widget.Toast
import com.hrules.composer.*
import com.hrules.composer.ui.commons.*

fun Context.getClipBoardMonitorService() = Intent(this, ClipboardMonitorService::class.java)

class ClipboardMonitorService : Service() {
  companion object {
    private const val EMPTY_CLIP = ""
  }

  private val preferences by lazy { Preferences(this) }
  private var clipboardManager: ClipboardManager? = null

  private val onPrimaryClipChangedListener = ClipboardManager.OnPrimaryClipChangedListener {
    val clip = clipboardManager?.getFirstClip() ?: EMPTY_CLIP
    if (clip != EMPTY_CLIP && !AppLifecycle.isAppInForeground) {
      preferences.note = "${preferences.note}\n$clip"
      notifyUser(this)
    }
  }

  override fun onCreate() {
    super.onCreate()
    if (preferences.serviceActive) {
      clipboardManager = (getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).apply {
        addPrimaryClipChangedListener(
          onPrimaryClipChangedListener
        )
      }
    }
  }

  override fun onStartCommand(intent: Intent, flags: Int, startId: Int) = Service.START_STICKY

  override fun onDestroy() {
    super.onDestroy()
    clipboardManager?.removePrimaryClipChangedListener(
      onPrimaryClipChangedListener
    )
  }

  override fun onBind(intent: Intent?): IBinder? = null

  private fun notifyUser(context: Context) {
    Toast.makeText(context, context.string(R.string.info_notify), Toast.LENGTH_LONG).show()
  }

  private fun ClipboardManager.getFirstClip() = primaryClip?.getItemAt(0)?.text ?: EMPTY_CLIP
}
