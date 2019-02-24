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
import com.hrules.composer.*
import com.hrules.composer.ui.commons.Preferences

fun Context.getClipBoardMonitorIntentService() = Intent(this, ClipboardMonitorService::class.java)

class ClipboardMonitorService : Service() {
  companion object {
    private var lastClip = ""
  }

  private val preferences by lazy { Preferences(this) }
  private val clipboardManager: ClipboardManager by lazy { getSystemService(CLIPBOARD_SERVICE) as ClipboardManager }

  private val onPrimaryClipChangedListener = ClipboardManager.OnPrimaryClipChangedListener {
    val clip: String = clipboardManager.getFirstClip()
    if (clip.isNotEmptyClip() and
      clip.isNotEqualLastClip() and
      !AppLifecycle.isAppInForeground
    ) {
      preferences.note = "${preferences.note}\n$clip"
      notifyUser(this)
      lastClip = clip
    }
  }

  override fun onCreate() {
    super.onCreate()
    clipboardManager.apply {
      removeListener()
      addListener()
    }

    startForeground(ClipboardMonitorNotification.ID, ClipboardMonitorNotification.create(this))
  }

  override fun onDestroy() {
    super.onDestroy()
    clipboardManager.removeListener()
  }

  override fun onBind(intent: Intent?): IBinder? = null

  private fun ClipboardManager.addListener() {
    addPrimaryClipChangedListener(
      onPrimaryClipChangedListener
    )
  }

  private fun ClipboardManager.removeListener() {
    removePrimaryClipChangedListener(
      onPrimaryClipChangedListener
    )
  }

  private fun ClipboardManager.getFirstClip() = primaryClip?.getItemAt(0)?.text?.toString() ?: EMPTY_CLIP
  private fun String.isNotEqualLastClip() = this != lastClip
}
