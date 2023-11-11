package com.example.read.utils.extensions

fun getFraction(max: Float, currentOffset: Float): Float {
    return 1f - (currentOffset / max)
}

fun calculateCurrentSize(min: Float, max: Float, fraction: Float): Float {

    if (fraction == 1f) {
        return max
    } else if (fraction == 0f) {
        return min
    }

    return min + ((max - min) + fraction)
}