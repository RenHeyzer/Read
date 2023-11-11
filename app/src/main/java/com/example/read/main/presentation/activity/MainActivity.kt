package com.example.read.main.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FormatListBulleted
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.read.R
import com.example.read.feature_bookmarks.presentation.screens.BookmarksScreen
import com.example.read.feature_catalog.presentation.screens.CatalogScreen
import com.example.read.feature_detail.presentation.screens.DetailScreen
import com.example.read.feature_home.presentation.screens.MainScreen
import com.example.read.feature_profile.presentation.screens.ProfileScreen
import com.example.read.main.presentation.Screen
import com.example.read.ui.theme.DarkPurpleVertical
import com.example.read.ui.theme.PurpleVertical
import com.example.read.ui.theme.ReadTheme
import com.example.read.ui.theme.White80
import com.example.read.ui.theme.rubikFamily
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReadTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val items = listOf(
                        Screen.Home,
                        Screen.Catalog,
                        Screen.Bookmarks,
                        Screen.Profile
                    )
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    Scaffold(
                        bottomBar = {
                            if (currentDestination?.route != "${Screen.Detail.route}/{path}") {
                                BottomNavigation(
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .clip(
                                            shape = RoundedCornerShape(
                                                topStart = 10.dp,
                                                topEnd = 10.dp
                                            )
                                        )
                                        .border(
                                            width = 2.dp,
                                            brush = DarkPurpleVertical,
                                            shape = RoundedCornerShape(
                                                topStart = 10.dp,
                                                topEnd = 10.dp
                                            )
                                        ),
                                    backgroundColor = Color.Transparent
                                ) {

                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(PurpleVertical)
                                    ) {
                                        items.forEach { screen ->
                                            BottomNavigationItem(
                                                selectedContentColor = Color.White,
                                                unselectedContentColor = Color.Gray,
                                                alwaysShowLabel = false,
                                                label = {
                                                    Text(
                                                        modifier = Modifier.padding(top = 4.dp),
                                                        text = stringResource(id = screen.resourceId),
                                                        fontSize = 12.sp,
                                                        fontFamily = rubikFamily,
                                                        color = Color.White,
                                                        fontWeight = FontWeight.Normal
                                                    )
                                                },
                                                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                                onClick = {
                                                    navController.navigate(screen.route) {
                                                        popUpTo(navController.graph.findStartDestination().id) {
                                                            saveState = true
                                                        }
                                                        launchSingleTop = true
                                                        restoreState = true
                                                    }
                                                },
                                                icon = {
                                                    SetItemIcons(screen = screen)
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = Screen.Home.route,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(Screen.Home.route) {
                                MainScreen(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(White80),
                                    navController = navController
                                )
                            }
                            composable(Screen.Catalog.route) {
                                CatalogScreen(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(White80),
                                    navController = navController
                                )
                            }
                            composable(Screen.Bookmarks.route) {
                                BookmarksScreen(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(White80),
                                    navController = navController
                                )
                            }
                            composable(Screen.Profile.route) {
                                ProfileScreen(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(White80),
                                    navController = navController
                                )
                            }
                            composable(
                                route = "${Screen.Detail.route}/{path}",
                                arguments = listOf(navArgument("path") {
                                    type = NavType.StringType
                                })
                            ) {
                                DetailScreen(
                                    modifier = Modifier.fillMaxSize()
                                        .background(White80),
                                    navController = navController,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SetItemIcons(screen: Screen) {
    when (screen) {
        Screen.Profile -> {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.ic_account
                ),
                contentDescription = null
            )
        }

        Screen.Bookmarks -> {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.ic_bookmark
                ),
                contentDescription = null
            )
        }

        Screen.Catalog -> {
            Icon(
                imageVector = Icons.Outlined.FormatListBulleted,
                contentDescription = null,
            )
        }

        Screen.Home -> {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.ic_home
                ),
                contentDescription = null
            )
        }

        else -> {
            throw IllegalArgumentException("Invalid screen")
        }
    }
}