package com.example.read.feature_home.data.remote.sources

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query

interface BooksRemoteDataSource {

    suspend fun getBooks(limit: Long, startAfter: DocumentSnapshot?): Query
}