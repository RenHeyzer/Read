package com.example.read.di

import com.example.read.common.constants.Constants
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object FirestoreModule {

    @Named(Constants.BOOKS_COLLECTION_PATH)
    @Provides
    fun provideBooksCollection(firestore: FirebaseFirestore): CollectionReference =
        firestore.collection(
            Constants.BOOKS_COLLECTION_PATH
        )

    @Named(Constants.MANGA_CATALOG_COLLECTION_PATH)
    @Provides
    fun provideMangaCatalogCollection(firestore: FirebaseFirestore): CollectionReference =
        firestore.collection(
            Constants.MANGA_CATALOG_COLLECTION_PATH
        )

    @Named(Constants.COMICS_CATALOG_COLLECTION_PATH)
    @Provides
    fun provideComicsCatalogCollection(firestore: FirebaseFirestore): CollectionReference =
        firestore.collection(
            Constants.COMICS_CATALOG_COLLECTION_PATH
        )
}