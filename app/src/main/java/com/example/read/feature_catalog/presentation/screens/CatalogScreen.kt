package com.example.read.feature_catalog.presentation.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.StarRate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.read.R
import com.example.read.feature_detail.domain.models.Chapter
import com.example.read.feature_home.domain.models.BookItem
import com.example.read.ui.theme.DarkPurpleVertical
import com.example.read.ui.theme.PurpleVertical
import com.example.read.ui.theme.White80
import com.example.read.ui.theme.WhiteVertical
import com.example.read.ui.theme.Yellow
import com.example.read.ui.theme.rubikFamily
import com.example.read.utils.extensions.ConfigureAsLazyPagingItemsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import java.util.Date

@Composable
fun CatalogScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: CatalogViewModel = hiltViewModel()
) {

    val comicsPagingItems = viewModel.comicsState.collectAsLazyPagingItems()
    val mangaPagingItems = viewModel.mangaState.collectAsLazyPagingItems()
    var checkedCatalog by remember {
        mutableStateOf(false)
    }

    Column(modifier) {
        CatalogSwitch(
            modifier = Modifier
                .padding(top = 10.dp, start = 4.dp, end = 4.dp)
                .fillMaxWidth()
                .height(40.dp),
            checkedCatalog = { checked ->
                checkedCatalog = checked
            },
            checked = checkedCatalog
        )
        if (!checkedCatalog) {
            ConfigureAsLazyPagingItemsState(
                pagingItems = comicsPagingItems,
                loadState = comicsPagingItems.loadState.refresh,
                error = {
                    Log.e("error", it)
                },
                loading = {
                },
                notLoading = {
                    CatalogContent(
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .fillMaxSize(),
                        data = it
                    )
                }
            )
        } else {
            ConfigureAsLazyPagingItemsState(
                pagingItems = mangaPagingItems,
                loadState = mangaPagingItems.loadState.refresh,
                error = {
                    Log.e("error", it)
                },
                loading = {
                },
                notLoading = {
                    CatalogContent(
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .fillMaxSize(),
                        data = it
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewCatalogScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White80)
    ) {
        CatalogSwitch(
            modifier = Modifier
                .padding(top = 10.dp, start = 4.dp, end = 4.dp)
                .fillMaxWidth()
                .weight(0.1f)
        )
        PreviewCatalogContent(
            modifier = Modifier
                .padding(top = 6.dp)
                .fillMaxWidth()
                .weight(1.4f)
        )
    }
}

@Composable
fun CatalogSwitch(
    modifier: Modifier = Modifier,
    checkedCatalog: (checked: Boolean) -> Unit = {},
    checked: Boolean = false
) {

    Row(modifier) {
        Button(
            modifier = Modifier
                .fillMaxHeight()
                .width(0.dp)
                .weight(1f),
            contentPadding = PaddingValues(),
            shape = RoundedCornerShape(
                topStart = 10.dp,
                bottomStart = 10.dp,
                topEnd = 0.dp,
                bottomEnd = 0.dp
            ),
            border = BorderStroke(2.dp, brush = DarkPurpleVertical),
            colors = ButtonDefaults.buttonColors(contentColor = Color.Transparent),
            onClick = {
                checkedCatalog(false)
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(if (!checked) PurpleVertical else WhiteVertical),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.comics_catalog),
                    fontSize = 16.sp,
                    color = if (!checked) Color.White else Color.Black,
                    fontFamily = rubikFamily,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxHeight()
                .width(0.dp)
                .weight(1f),
            contentPadding = PaddingValues(),
            shape = RoundedCornerShape(
                topStart = 0.dp,
                bottomStart = 0.dp,
                topEnd = 10.dp,
                bottomEnd = 10.dp
            ),
            border = BorderStroke(2.dp, brush = DarkPurpleVertical),
            colors = ButtonDefaults.buttonColors(contentColor = Color.Transparent),
            onClick = {
                checkedCatalog(true)
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(if (checked) PurpleVertical else WhiteVertical),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.manga_catalog),
                    fontSize = 16.sp,
                    color = if (checked) Color.White else Color.Black,
                    fontFamily = rubikFamily,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCatalogSwitch() {
    CatalogSwitch(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    )
}

@Composable
fun CatalogContent(modifier: Modifier = Modifier, data: LazyPagingItems<BookItem>) {
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Fixed(3),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(data.itemCount) { index ->
            if (data[index] == null) return@items
            data[index]?.let {
                CatalogItem(
                    modifier = Modifier
                        .wrapContentSize(), item = it
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCatalogContent(modifier: Modifier = Modifier) {
    val data = List(50) {
        BookItem(
            id = "d9Iu94Q2SSvnBQWf8IzF",
            title = "Пойдем в караоке!",
            coverImage = "https://cover.imglib.info/uploads/cover/karaoke-iko/cover/jQPCea6qyp1o_250x350.jpg",
            releaseYear = 2023,
            status = "Завершён",
            rating = 9.0,
            latestChapter = Chapter(
                tome = 1,
                number = 2.5,
                releaseDate = Date(1537287840000)
            ),
        )
    }
    val flow = MutableStateFlow(PagingData.from(data))
    CompositionLocalProvider(value = LocalInspectionMode provides true) {
        val pagingItems = flow.collectAsLazyPagingItems(StandardTestDispatcher())
        CatalogContent(modifier = modifier, data = pagingItems)
    }
}

@Composable
fun CatalogItem(modifier: Modifier = Modifier, item: BookItem) = with(item) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, brush = DarkPurpleVertical)
    ) {
        Box {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(162.dp)
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                    .padding(top = 2.dp, start = 2.dp, end = 2.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(coverImage)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(id = R.string.book_cover_image_content_description),
                filterQuality = FilterQuality.High,
                contentScale = ContentScale.FillBounds,
                onError = {
                    Log.e("catalog_cover_image", it.result.throwable.message.toString())
                }
            )
            rating?.let {
                Rating(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 10.dp, end = 10.dp)
                        .shadow(20.dp),
                    it = it
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(PurpleVertical)
                .border(
                    border = BorderStroke(2.dp, DarkPurpleVertical),
                    shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                )
                .padding(vertical = 4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            title?.let { title ->
                val maxLength = 17
                val wrappedTitle = if (title.length > maxLength) {
                    "${title.removeRange(maxLength, title.length)}\n${
                        title.removeRange(
                            0,
                            maxLength
                        )
                    }"
                } else {
                    title
                }
                Text(
                    text = wrappedTitle,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontFamily = rubikFamily,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (releaseYear != null && status != null) {
                Text(
                    text = "$releaseYear $status",
                    color = Color.LightGray,
                    fontSize = 10.sp,
                    fontFamily = rubikFamily,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            latestChapter?.number?.let { number ->
                Text(
                    text = stringResource(id = R.string.book_chapter_number_text, number),
                    color = Color.LightGray,
                    fontSize = 10.sp,
                    fontFamily = rubikFamily,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun Rating(modifier: Modifier = Modifier, it: Double) {
    val rating = it.toString().removeSuffix(".0")
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Sharp.StarRate,
            contentDescription = stringResource(id = R.string.rating_icon_content_description),
            tint = Yellow,
            modifier = Modifier.size(12.dp)
        )
        Text(
            text = rating,
            fontSize = 10.sp,
            color = Color.White,
            fontFamily = rubikFamily,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview
@Composable
fun PreviewCatalogItem() {
    CatalogItem(
        modifier = Modifier.wrapContentSize(),
        item = BookItem(
            id = "d9Iu94Q2SSvnBQWf8IzF",
            title = "Пойдем в караоке!",
            coverImage = "https://cover.imglib.info/uploads/cover/karaoke-iko/cover/jQPCea6qyp1o_250x350.jpg",
            releaseYear = 2023,
            status = "Завершён",
            rating = 9.0,
            latestChapter = Chapter(
                tome = 1,
                number = 2.5,
                releaseDate = Date(1537287840000)
            ),
        )
    )
}
