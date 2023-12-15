package com.example.read.feature_detail.domain.usecases

import com.example.read.feature_bookmarks.domain.models.Bookmark
import com.example.read.feature_bookmarks.domain.repositories.BookmarksRepository
import com.example.read.feature_profile.domain.repositories.ProfileRepository
import com.example.read.utils.state_holders.Either
import javax.inject.Inject

/*
class CheckBookInBookmarksUseCase @Inject constructor(
    private val bookmarksRepository: BookmarksRepository,
    private val profileRepository: ProfileRepository
) {

    suspend operator fun invoke(id: String): Either<Exception, Bookmark> {
        profileRepository.getSessionStatus(
            authenticated = {
                try {
                    val inBookmark = bookmarksRepository.checkBookInBookmarks(id)
                    Either.Right(inBookmark)
                } catch (e: Exception) {
                    Either.Left(e)
                }
            },
        )
    }
}*/
