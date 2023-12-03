package com.example.read.feature_bookmarks.domain.models

enum class BookmarkType(val type: String) {
    All("Все"),
    READING("Читаю"),
    READ("Прочитано"),
    IN_THE_PLANS("В планах"),
    ABANDONED("Брошено"),
    FAVORITES("Избранные")
}