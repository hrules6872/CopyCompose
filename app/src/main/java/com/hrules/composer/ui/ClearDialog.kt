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