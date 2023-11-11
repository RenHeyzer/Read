package com.example.read.utils.mapper

interface Mapper<F : Mappable, T : Mappable> {

    fun to(model: F): T
    fun from(model: T): F
}

interface Mappable