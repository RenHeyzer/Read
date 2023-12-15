package com.example.read.feature_detail.domain.usecases

import com.example.read.feature_bookmarks.domain.models.Bookmark
import com.example.read.feature_bookmarks.domain.models.BookmarkType
import com.example.read.feature_bookmarks.domain.repositories.BookmarksRepository
import com.example.read.feature_profile.domain.repositories.ProfileRepository
import com.example.read.feature_profile.domain.repositories.SessionStatusState
import com.example.read.utils.exceptions.NetworkErrorException
import java.util.UUID
import javax.inject.Inject

class AddBookToBookmarksUseCase @Inject constructor(
    private val bookmarksRepository: BookmarksRepository,
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(bookId: String, type: BookmarkType): BookmarkResult {
        return when (val status = profileRepository.sessionStatus) {
            is SessionStatusState.Authenticated -> {
                try {
                    val userId = checkNotNull(status.session.user?.id)
                    val bookmark =
                        Bookmark(bookId = bookId, type = type.type, userId = userId)
                    val inBookmark =
                        bookmarksRepository.checkBookInBookmarks(bookId = bookId, userId = userId)
                    if (inBookmark == null) {
                        bookmarksRepository.addBookToBookmark(bookmark)
                        BookmarkResult.Success
                    } else {
                        if (inBookmark.type != bookmark.type) {
                            bookmarksRepository.addBookToBookmark(
                                bookmark = bookmark.copy(id = inBookmark.id),
                                upsert = true
                            )
                            BookmarkResult.Success
                        } else {
                            BookmarkResult.AlreadyExist
                        }
                    }
                } catch (e: Exception) {
                    BookmarkResult.Error(e)
                }
            }

            SessionStatusState.NotAuthenticated -> {
                BookmarkResult.AuthFailure
            }

            SessionStatusState.LoadingFromStorage -> {
                BookmarkResult.Success
            }

            SessionStatusState.NetworkError -> {
                BookmarkResult.Error(NetworkErrorException("Network error!"))
            }
        }
    }
}

sealed interface BookmarkResult {

    data object Success : BookmarkResult

    data object AuthFailure : BookmarkResult

    class Error(val exception: Exception) : BookmarkResult

    data object AlreadyExist : BookmarkResult
}
