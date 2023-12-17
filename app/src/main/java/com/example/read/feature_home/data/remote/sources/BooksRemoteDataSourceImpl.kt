package com.example.read.feature_home.data.remote.sources

import com.example.read.utils.constants.Constants
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class BooksRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : BooksRemoteDataSource {

    override suspend fun getBooks(limit: Long) =
        firestore.collection(Constants.BOOKS_COLLECTION_PATH).limit(
            limit
        )
}