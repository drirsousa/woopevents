package br.com.rosait.woopevents.common.base

import com.squareup.picasso.Callback
import java.lang.Exception

abstract class CustomCallback : Callback {

    override fun onSuccess() {
        onFinish()
    }

    override fun onError(e: Exception?) {
        onFinish()
    }

    abstract fun onFinish()
}