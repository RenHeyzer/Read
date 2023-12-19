package com.example.read.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object BookmarksRealtime {

    private val mutableChannel = MutableStateFlow<Changes>(Changes.Default)
    val channel = mutableChannel.asStateFlow()

    fun insert() {
        mutableChannel.value = Changes.Insert()
    }

    fun update() {
        mutableChannel.value = Changes.Update()
    }

    fun delete() {
        mutableChannel.value = Changes.Delete()
    }

    sealed interface Changes {
        data object Default : Changes
        class Insert : Changes
        class Update : Changes
        class Delete : Changes
    }
}