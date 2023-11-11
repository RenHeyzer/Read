package com.example.read.utils.mappers

interface Mapper<F : Mappable, T : Mappable> {

    fun to(model: F): T
    fun from(model: T): F
}

interface Mappable