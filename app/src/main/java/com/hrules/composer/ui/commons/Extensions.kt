package com.hrules.composer.ui.commons

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun Context.string(resStringId: Int) = this.getString(resStringId) ?: ""

fun EditText.safeText(): String = text?.toString() ?: ""
fun EditText.clearText() = setText("")
fun EditText.hideKeyboard() {
  val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}