package com.example.read.feature_catalog.data.remote.sources

import com.example.read.common.constants.Constants
import com.example.read.common.data.utils.FirestoreGetRemoteDataSource
import com.example.read.common.data.utils.FirestoreGetRemoteDataSourceImpl
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import javax.inject.Inject
import javax.inject.Named

class CatalogRemoteDataSourceImpl @Inject constructor(
    @Named(Constants.MANGA_CATALOG_COLLECTION_PATH)
    private val mangaCollection: CollectionReference,
    @Named(Constants.COMICS_CATALOG_COLLECTION_PATH)
    private val comicsCollection: CollectionReference
) : CatalogRemoteDataSource, FirestoreGetRemoteDataSource by FirestoreGetRemoteDataSourceImpl() {

    override suspend fun getMangaCatalog(limit: Long, startAfter: DocumentSnapshot?): Query =
        get(collection = mangaCollection, limit = limit, startAfter = startAfter)

    override suspend fun getComicsCatalog(limit: Long, startAfter: DocumentSnapshot?): Query =
        get(collection = comicsCollection, limit = limit, startAfter = startAfter)
}