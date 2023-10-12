package com.example.read.utils.mapper

interface Mapper<F : Mappable, T : Mappable> {

    fun toModel(model: F): T

    fun fromModel(model: T): F
}

interface Mappable