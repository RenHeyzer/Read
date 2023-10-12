package com.example.read.books.data.remote.sources

import com.google.firebase.firestore.Query

interface BookRemoteDataSource {

    fun queryBooks(): Query
}