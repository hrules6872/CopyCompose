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

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.hrules.composer.R

object ClearDialog {
  fun build(
    context: Context,
    callback: () -> Unit
  ): AlertDialog = AlertDialog.Builder(context)
    .setTitle(R.string.dialog_clear_title)
    .setNegativeButton(R.string.action_cancel) { dialog, _ -> dialog.dismiss() }
    .setPositiveButton(R.string.action_clear) { dialog, _ ->
      callback()
      dialog.dismiss()
    }
    .create()
}