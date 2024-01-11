package com.example.read.feature_auth.presentation.utils

import android.os.CountDownTimer

fun countDownTimer(
    millsInFuture: Long,
    countDownInterval: Long,
    onTick: ((millisUntilFinished: Long) -> Unit)? = null,
    onFinish: (() -> Unit)? = null
) = object : CountDownTimer(millsInFuture, countDownInterval) {

    override fun onTick(millisUntilFinished: Long) {
        onTick?.let {
            it(millisUntilFinished)
        }
    }

    override fun onFinish() {
        onFinish?.let {
            it()
        }
    }
}