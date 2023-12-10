package com.example.read.utils.mappers

import com.example.read.feature_profile.domain.models.User
import com.example.read.feature_profile.domain.models.UserMetadata
import com.google.gson.Gson
import io.github.jan.supabase.gotrue.user.UserInfo

fun UserInfo.asDomain(): User {
    val userMetadata = Gson().fromJson(userMetadata.toString(), UserMetadata::class.java)
    return User(
        id = id,
        email = email,
        metadata = userMetadata
    )
}