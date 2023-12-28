package com.example.read.feature_home.data.remote.sources

import com.example.read.common.constants.Constants
import com.example.read.common.data.utils.FirestoreGetRemoteDataSource
import com.example.read.common.data.utils.FirestoreGetRemoteDataSourceImpl
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import javax.inject.Inject
import javax.inject.Named

class BooksRemoteDataSourceImpl @Inject constructor(
    @Named(Constants.BOOKS_COLLECTION_PATH) private val booksCollection: CollectionReference
) : BooksRemoteDataSource, FirestoreGetRemoteDataSource by FirestoreGetRemoteDataSourceImpl() {

    override suspend fun getBooks(limit: Long, startAfter: DocumentSnapshot?): Query =
        get(collection = booksCollection, limit = limit, startAfter = startAfter)
}