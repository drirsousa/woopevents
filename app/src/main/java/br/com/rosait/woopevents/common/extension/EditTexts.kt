package br.com.rosait.woopevents.common.extension

import android.widget.EditText
import br.com.rosait.woopevents.R

fun EditText.isValidInput() : Boolean {
    val text = this.text.toString()
    if(text.isNullOrEmpty()) {
        this.error = this.context.resources.getString(R.string.label_empty_error_message)
        requestFocus()
        return false
    }
    else if(error != null) {
        error = this.context.resources.getString(R.string.message_enter_valid_info)
        requestFocus()
        return false
    }
    return true
}

fun EditText.setCustomError(isInvalid: Boolean, errorMessage: String) : Boolean {
    if(isInvalid) {
        error = errorMessage
        requestFocus()
    }
    return !isInvalid
}