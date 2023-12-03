package com.example.read.utils.extensions

import android.os.CountDownTimer

fun countDownTimer(
    millsInFuture: Long,
    countDownInterval: Long,
    onTick: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null
) = object : CountDownTimer(millsInFuture, countDownInterval) {

    override fun onTick(millisUntilFinished: Long) {
        onTick?.let {
            it()
        }
    }

    override fun onFinish() {
        onFinish?.let {
            it()
        }
    }
}