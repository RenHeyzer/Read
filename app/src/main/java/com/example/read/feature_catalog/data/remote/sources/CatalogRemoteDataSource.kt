package com.example.read.feature_catalog.data.remote.sources

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query

interface CatalogRemoteDataSource {

    suspend fun getMangaCatalog(limit: Long, startAfter: DocumentSnapshot?): Query

    suspend fun getComicsCatalog(limit: Long, startAfter: DocumentSnapshot?): Query
}