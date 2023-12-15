package com.example.read.utils.state_holders

sealed class Either<out L, out R> {

    data class Left<out L>(val error: L? = null): Either<L, Nothing>()

    data class Right<out R>(val data: R? = null): Either<Nothing, R>()
}