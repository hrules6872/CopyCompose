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

package com.hrules.composer.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.*
import androidx.appcompat.widget.AppCompatEditText
import com.hrules.composer.R.string
import com.hrules.composer.ui.commons.*

class ContextMenuEditText @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

  init {
    customSelectionActionModeCallback =
      CustomSelectionActionModeCallback(context, this)
  }

  class CustomSelectionActionModeCallback(val context: Context, private val editText: AppCompatEditText) : ActionMode.Callback {
    private var menuLowerCaseId = 0

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
      addLowerCaseMenuEntry(menu)
      return true
    }

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu) = true

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean = when (item.itemId) {
      menuLowerCaseId -> {
        try {
          val selectedText = editText.safeText().substring(editText.selectionStart, editText.selectionEnd)
          editText.editableText.replace(editText.selectionStart, editText.selectionEnd, selectedText.toLowerCase())
        } catch (e: IndexOutOfBoundsException) {
        }
        true
      }
      else -> false
    }

    override fun onDestroyActionMode(mode: ActionMode) {}

    private fun addLowerCaseMenuEntry(menu: Menu) {
      while (menu.findItem(menuLowerCaseId) != null) {
        menuLowerCaseId++
      }
      if (menu.findItem(menuLowerCaseId) == null) {
        menu.add(0, menuLowerCaseId, 0, context.string(string.action_lower_case))
      }
    }
  }
}