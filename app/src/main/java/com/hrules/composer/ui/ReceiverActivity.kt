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

package com.hrules.composer.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hrules.composer.notifyUser
import com.hrules.composer.ui.commons.Preferences

class ReceiverActivity : AppCompatActivity() {
  private val preferences by lazy { Preferences(this) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    intent?.let { safeIntent ->
      when {
        safeIntent.isActionSend() -> save(safeIntent.extraText())
        safeIntent.isActionProcessText() -> save(safeIntent.extraProcessText())
      }
    }
    finish()
  }

  private fun save(clip: String) {
    preferences.note = "${preferences.note}\n$clip"
    notifyUser(this)
  }

  private fun Intent.isActionSend() =
    action == Intent.ACTION_SEND && "text/plain" == type && extraText().isNotEmpty()

  private fun Intent.isActionProcessText() =
    action == Intent.ACTION_PROCESS_TEXT && "text/plain" == type && extraProcessText().isNotEmpty()

  private fun Intent.extraText() = getStringExtra(Intent.EXTRA_TEXT) ?: ""
  private fun Intent.extraProcessText() = getStringExtra(Intent.EXTRA_PROCESS_TEXT) ?: ""
}

