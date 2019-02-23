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

package com.hrules.composer.ui.commons

import android.content.*
import android.content.SharedPreferences.Editor
import android.preference.PreferenceManager

class Preferences(private val context: Context) {
  companion object {
    private const val KEY_SERVICE_ACTIVE = "key_service_active"
    private const val KEY_NOTE = "key_note"
    private const val KEY_NOTE_CURSOR_POSITION = "key_note_cursor_position"

    private const val DEFAULT_KEY_SERVICE_ACTIVE = true
    private const val DEFAULT_KEY_NOTE = ""
    private const val DEFAULT_KEY_NOTE_CURSOR_POSITION = 0
  }

  var serviceActive: Boolean
    get() = getBoolean(KEY_SERVICE_ACTIVE, DEFAULT_KEY_SERVICE_ACTIVE)
    set(value) = putBoolean(KEY_SERVICE_ACTIVE, value)

  var note: String
    get() = getString(KEY_NOTE, DEFAULT_KEY_NOTE)
    set(value) = putString(KEY_NOTE, value)

  var noteCursorPosition: Int
    get() = getInt(KEY_NOTE_CURSOR_POSITION, DEFAULT_KEY_NOTE_CURSOR_POSITION)
    set(value) = putInt(KEY_NOTE_CURSOR_POSITION, value)

  private val preferences: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(context)

  private val editor: Editor
    get() = preferences.edit()

  @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
  private fun getString(
    key: String,
    defaultValue: String
  ): String = preferences.getString(key, defaultValue)

  private fun putString(
    key: String,
    value: String
  ) {
    editor.putString(key, value)
      .apply()
  }

  private fun getInt(
    key: String,
    defaultValue: Int
  ): Int = preferences.getInt(key, defaultValue)

  private fun putInt(
    key: String,
    value: Int
  ) {
    editor.putInt(key, value)
      .apply()
  }

  private fun getBoolean(
    key: String,
    defaultValue: Boolean
  ): Boolean = preferences.getBoolean(key, defaultValue)

  private fun putBoolean(
    key: String,
    value: Boolean
  ) {
    editor.putBoolean(key, value)
      .apply()
  }
}