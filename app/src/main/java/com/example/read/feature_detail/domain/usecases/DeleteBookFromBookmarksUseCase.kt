package com.example.read.feature_detail.domain.usecases

import android.util.Log
import com.example.read.feature_bookmarks.domain.models.Bookmark
import com.example.read.feature_bookmarks.domain.repositories.BookmarksRepository
import com.example.read.feature_profile.domain.repositories.ProfileRepository
import com.example.read.feature_profile.domain.repositories.SessionStatusState
import com.example.read.utils.exceptions.NetworkErrorException
import javax.inject.Inject

class DeleteBookFromBookmarksUseCase @Inject constructor(
    private val bookmarksRepository: BookmarksRepository,
    private val profileRepository: ProfileRepository
) {

    suspend operator fun invoke(id: String): BookmarkResult {
        return when (profileRepository.sessionStatus) {
            is SessionStatusState.Authenticated -> {
                try {
                    val result = bookmarksRepository.deleteBookInBookmark(id)
                    Log.e("delete_bookmark", result.toString())
                    BookmarkResult.Success
                } catch (e: Exception) {
                    BookmarkResult.Error(e)
                }
            }

            SessionStatusState.NotAuthenticated -> BookmarkResult.AuthFailure

            SessionStatusState.LoadingFromStorage -> BookmarkResult.Success

            SessionStatusState.NetworkError -> BookmarkResult.Error(
                NetworkErrorException("Network error!")
            )
        }
    }
}