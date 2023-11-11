package com.example.read.feature_home.data.remote.sources.books

import com.example.read.feature_home.data.remote.dtos.BookItemDto
import com.example.read.utils.constants.Constants
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.coroutines.suspendFind
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BooksRemoteDataSourceImpl @Inject constructor() : BooksRemoteDataSource {

    override suspend fun fetchComics(limit: Int, skip: Int, searchQuery: String) =
        ParseQuery.getQuery<ParseObject>(Constants.COMICS_CLASS_NAME).apply {
            this.limit = limit
            this.skip = skip
            this.cachePolicy = ParseQuery.CachePolicy.CACHE_ELSE_NETWORK
            whereContains("title", searchQuery)
        }.suspendFind()

    override suspend fun fetchManga(limit: Int, skip: Int, searchQuery: String) =
        ParseQuery.getQuery<ParseObject>(Constants.MANGA_CLASS_NAME).apply {
            this.limit = limit
            this.skip = skip
            this.cachePolicy = ParseQuery.CachePolicy.CACHE_ELSE_NETWORK
            whereContains("title", searchQuery)
        }.suspendFind()

    override suspend fun addBook(item: BookItemDto) {
        /*val queryInfo = ParseQuery.getQuery<ParseObject>(Constants.INFO_CLASS_NAME).whereContains("type", "Манга")
        queryInfo.findInBackground().await().forEach { parseObject ->
            parseObject?.objectId?.let {
                val latestChapter =
                    parseObject.getRelation<ParseObject>("chapters").query.findInBackground()
                        .await().last()
                item.copy(id = it).toParseObject(Constants.MANGA_CLASS_NAME, latestChapter)
                    .saveInBackground { exception ->
                        exception?.message?.let { message ->
                            Log.e("info", message)
                        }
                    }
            }
        }*/
    }

    /*override suspend fun deleteBooks() {
        withContext(appDispatchers.io) {
            firestore.collection(Constants.BOOK_COLLECTION_PATH).apply {
                get().await().documents.forEach {
                    if (it.id != "book1" && it.id != "book2") {
                        document(it.id).delete()
                    }
                }
            }
        }
    }*/
}