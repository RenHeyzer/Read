package com.example.read.common.data.utils

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query

interface FirestoreGetRemoteDataSource {

    suspend fun get(
        collection: CollectionReference,
        limit: Long,
        startAfter: DocumentSnapshot?
    ): Query
}

class FirestoreGetRemoteDataSourceImpl : FirestoreGetRemoteDataSource {

    override suspend fun get(
        collection: CollectionReference,
        limit: Long,
        startAfter: DocumentSnapshot?
    ): Query =
        collection.limit(limit).startAfter(startAfter)
}