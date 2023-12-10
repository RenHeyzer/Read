package com.example.read.feature_detail.domain.usecases

import com.example.read.feature_bookmarks.domain.models.Bookmark
import com.example.read.feature_bookmarks.domain.repositories.BookmarksRepository
import com.example.read.feature_profile.domain.repositories.ProfileRepository
import com.example.read.feature_profile.domain.repositories.SessionStatusState
import javax.inject.Inject

class AddBookToBookmarkUseCase @Inject constructor(
    private val bookmarksRepository: BookmarksRepository,
    private val userRepository: ProfileRepository
) {

    suspend operator fun invoke(bookmark: Bookmark, upsert: Boolean = false): BookmarkResult {
        return try {
            return when (userRepository.sessionStatus) {
                is SessionStatusState.Authenticated -> {
                    bookmarksRepository.addBookToBookmark(bookmark, upsert)
                    BookmarkResult.Success
                }
                SessionStatusState.NotAuthenticated -> BookmarkResult.AuthFailure
                else -> BookmarkResult.Else
            }
        } catch (e: Exception) {
            BookmarkResult.Error(e.message ?: "Unknown error!")
        }
    }
}