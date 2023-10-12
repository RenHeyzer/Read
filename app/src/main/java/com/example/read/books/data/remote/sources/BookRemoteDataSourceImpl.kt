package com.example.read.books.data.remote.sources

import com.example.read.utils.AppDispatchers
import com.example.read.utils.constants.Constants
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val appDispatchers: AppDispatchers
) : BookRemoteDataSource {

    override fun queryBooks() = firestore.collection(Constants.BOOK_COLLECTION_PATH).limit(20)
}