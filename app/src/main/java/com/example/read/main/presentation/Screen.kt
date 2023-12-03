package com.example.read.main.presentation

import androidx.annotation.StringRes
import com.example.read.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    data object Home : Screen("home", R.string.screen_home)
    data object Catalog : Screen("catalog", R.string.screen_catalog)
    data object Bookmarks : Screen("bookmarks", R.string.screen_bookmarks)
    data object Profile : Screen("profile", R.string.screen_profile)
    data object Detail : Screen("detail/{id}", R.string.screen_detail) {
        fun putIdArgument(id: String) = "detail/$id"
    }
    data object SignUp : Screen("sign_up", R.string.screen_sign_up)
    data object SignIn : Screen("sign_in", R.string.screen_sign_in)
}