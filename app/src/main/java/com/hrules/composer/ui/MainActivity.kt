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
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.hrules.composer.R
import com.hrules.composer.services.getClipBoardMonitorIntentService
import com.hrules.composer.ui.commons.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_action_switch.view.action_switch

class MainActivity : AppCompatActivity() {
  private val preferences by lazy { Preferences(this) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setupToolbar()
    setupFab()
    setupService(preferences.serviceActive)
  }

  override fun onResume() {
    super.onResume()
    loadNote()
  }

  override fun onPause() {
    super.onPause()
    saveNote()
  }

  private fun setupToolbar() {
    toolbar.apply {
      title = string(R.string.app_name)
      setupMenu()
    }
  }

  private fun Toolbar.setupMenu() {
    inflateMenu(R.menu.menu_main)
    setOnMenuItemClickListener { menuItem ->
      when (menuItem.itemId) {
        R.id.action_clear -> ClearDialog.build(this@MainActivity) {
          note.clearText()
          preferences.note = ""
        }.show()

        R.id.action_about -> {
          try {
            val aboutIntent = Intent(Intent.ACTION_VIEW, Uri.parse(string(R.string.homepage)))
            startActivity(aboutIntent)
          } catch (e: Exception) {
            Toast.makeText(
              this@MainActivity,
              string(R.string.error_no_browser),
              Toast.LENGTH_SHORT
            ).show()
          }
        }
      }
      super.onOptionsItemSelected(menuItem)
    }

    val menuItemActionSwitch = menu.findItem(R.id.action_switch)
    menuItemActionSwitch.actionView.action_switch.apply {
      isChecked = preferences.serviceActive
      setOnCheckedChangeListener { _, isChecked ->
        preferences.serviceActive = isChecked
        setupService(isChecked)
      }
    }
  }

  private fun setupFab() {
    fab.setOnClickListener {
      val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, note.safeText())
      }
      startActivity(Intent.createChooser(shareIntent, string(R.string.dialog_share_title)))
    }
  }

  private fun setupService(state: Boolean) {
    when {
      state -> ContextCompat.startForegroundService(this, getClipBoardMonitorIntentService())
      else -> stopService(getClipBoardMonitorIntentService())
    }
  }

  private fun loadNote() {
    note.apply {
      setText(preferences.note)
      setSelection(preferences.noteCursorPosition)
    }
  }

  private fun saveNote() {
    preferences.apply {
      note = this@MainActivity.note.safeText()
      noteCursorPosition = this@MainActivity.note.selectionStart
    }
  }
}
