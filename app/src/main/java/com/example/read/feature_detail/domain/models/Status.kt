package com.example.read.feature_detail.domain.models

enum class Status(val status: String) {
    ANNOUNCEMENT("Анонс"),
    CONTINUES("Продолжается"),
    SUSPENDED("Приостановлен"),
    ABANDONED("Заброшен"),
    FINISHED("Завершён"),
}