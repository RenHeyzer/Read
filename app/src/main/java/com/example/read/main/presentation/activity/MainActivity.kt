package com.example.read.main.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.read.R
import com.example.read.feature_bookmarks.presentation.screens.BookmarksScreen
import com.example.read.feature_catalog.presentation.screens.CatalogScreen
import com.example.read.feature_detail.presentation.screens.DetailScreen
import com.example.read.feature_home.presentation.components.Search
import com.example.read.feature_home.presentation.screens.HomeScreen
import com.example.read.feature_login.presentation.screens.sign_in.SignInScreen
import com.example.read.feature_login.presentation.screens.sign_up.SignUpScreen
import com.example.read.feature_profile.presentation.screens.ProfileScreen
import com.example.read.main.presentation.Screen
import com.example.read.ui.theme.Purple50
import com.example.read.ui.theme.ReadTheme
import com.example.read.ui.theme.Rubik
import com.example.read.utils.mappers.asDomain
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.handleDeeplinks
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var supabaseClient: SupabaseClient
    private val viewModel by viewModels<MainViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supabaseClient.handleDeeplinks(intent) { session ->
            viewModel.updateUserSession(session.asDomain())
        }
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
                    val snackbarHostState = remember { SnackbarHostState() }
                    val scrollBehavior =
                        TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
                    var isSearchBarActive by rememberSaveable {
                        mutableStateOf(false)
                    }

                    Scaffold(
                        snackbarHost = { SnackbarHost(snackbarHostState) },
                        topBar = {
                            if (!isSearchBarActive) {
                                TopAppBar(
                                    colors = TopAppBarDefaults.topAppBarColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                        titleContentColor = MaterialTheme.colorScheme.secondary
                                    ),
                                    title = {
                                        Text(
                                            text = stringResource(id = R.string.app_name),
                                            fontSize = 18.sp,
                                            fontFamily = Rubik,
                                            fontWeight = FontWeight.Bold
                                        )
                                    },
                                    navigationIcon = {

                                    },
                                    actions = {
                                        IconButton(onClick = {
                                            isSearchBarActive = true
                                        }) {
                                            Icon(
                                                imageVector = Icons.Outlined.Search,
                                                contentDescription = stringResource(R.string.search_icon_content_description),
                                                tint = Color.White,
                                            )
                                        }
                                    },
                                    scrollBehavior = scrollBehavior
                                )
                            }
                        },
                        bottomBar = {
                            items.forEach { screen ->
                                if (currentDestination?.route == screen.route) {
                                    BottomNavigation(
                                        modifier = Modifier
                                            .padding(horizontal = 4.dp)
                                            .clip(
                                                shape = RoundedCornerShape(
                                                    topStart = 10.dp,
                                                    topEnd = 10.dp
                                                )
                                            )
                                            .background(Color.Transparent),
                                        backgroundColor = Purple50
                                    ) {
                                        items.forEach { screen ->
                                            BottomNavigationItem(
                                                selectedContentColor = Color.White,
                                                unselectedContentColor = Color.LightGray,
                                                alwaysShowLabel = false,
                                                label = {
                                                    Text(
                                                        modifier = Modifier.padding(top = 4.dp),
                                                        text = stringResource(id = screen.resourceId),
                                                        fontSize = 12.sp,
                                                        fontFamily = Rubik,
                                                        color = Color.White,
                                                        fontWeight = FontWeight.Normal
                                                    )
                                                },
                                                selected = currentDestination.hierarchy.any { it.route == screen.route },
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
                        if (isSearchBarActive) {
                            val searchResultPagingItems =
                                viewModel.searchResultState.collectAsLazyPagingItems()
                            Search(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(innerPadding),
                                pagingHits = searchResultPagingItems,
                                isSearchBarActive = isSearchBarActive,
                                onActiveChange = { isSearchBarActive = it },
                                onQueryChange = { viewModel.setSearchQuery(it) },
                                onSearch = { viewModel.setSearchQuery(it) },
                                onItemClick = { id ->
                                    navController.navigate(Screen.Detail.putIdArgument(id))
                                })
                        }

                        NavHost(
                            navController = navController,
                            startDestination = Screen.Home.route,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(Screen.Home.route) {
                                HomeScreen(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    navController = navController,
                                    isSearchBarActive = isSearchBarActive
                                )
                            }
                            composable(Screen.Catalog.route) {
                                CatalogScreen(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    navController = navController
                                )
                            }
                            composable(Screen.Bookmarks.route) {
                                BookmarksScreen(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    navController = navController
                                )
                            }
                            composable(Screen.Profile.route) {
                                ProfileScreen(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    navController = navController,
                                    logout = {
                                        recreate()
                                    }
                                )
                            }
                            composable(
                                route = Screen.Detail.route,
                                arguments = listOf(navArgument("id") {
                                    type = NavType.StringType
                                })
                            ) {
                                DetailScreen(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    navController = navController,
                                    snackbarHostState = snackbarHostState
                                )
                            }
                            composable(Screen.SignUp.route) {
                                SignUpScreen(
                                    modifier = Modifier.fillMaxSize(),
                                    navController = navController,
                                    snackbarHostState = snackbarHostState
                                )
                            }
                            composable(Screen.SignIn.route) {
                                SignInScreen(
                                    modifier = Modifier.fillMaxSize(),
                                    navController = navController,
                                    snackbarHostState = snackbarHostState
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
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.ic_catalog
                ),
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