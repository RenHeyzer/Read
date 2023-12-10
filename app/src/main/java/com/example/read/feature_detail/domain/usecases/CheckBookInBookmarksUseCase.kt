package com.example.read.feature_detail.domain.usecases

import com.example.read.feature_bookmarks.domain.models.Bookmark
import com.example.read.feature_bookmarks.domain.repositories.BookmarksRepository
import com.example.read.feature_profile.domain.repositories.ProfileRepository
import com.example.read.feature_profile.domain.repositories.SessionStatusState
import javax.inject.Inject

class CheckBookInBookmarksUseCase @Inject constructor(
    private val bookmarksRepository: BookmarksRepository,
    private val profileRepository: ProfileRepository
) {

    suspend operator fun invoke(id: String, bookmark: Bookmark): BookmarkResult {
        return try {
            when (profileRepository.sessionStatus) {
                is SessionStatusState.Authenticated -> {
                    val inBookmark = bookmarksRepository.checkBookInBookmarks(id)
                    if (inBookmark == null) {
                        bookmarksRepository.addBookToBookmark(bookmark)
                        BookmarkResult.Success
                    } else {
                        if (inBookmark.type != bookmark.type) {
                            bookmarksRepository.addBookToBookmark(
                                bookmark = bookmark,
                                upsert = true
                            )
                            BookmarkResult.Success
                        } else {
                            BookmarkResult.AlreadyExist
                        }
                    }
                }

                SessionStatusState.NotAuthenticated -> BookmarkResult.AuthFailure
                else -> BookmarkResult.Else
            }
        } catch (e: Exception) {
            BookmarkResult.Error(e.message ?: "Unknown error!")
        }
    }

}

sealed interface BookmarkResult {

    data object Success : BookmarkResult

    data object AuthFailure : BookmarkResult

    class Error(val message: String) : BookmarkResult

    data object AlreadyExist : BookmarkResult

    data object Else : BookmarkResult
}