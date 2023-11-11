package com.example.read.feature_home.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.read.R
import com.example.read.feature_home.domain.models.RecommendationItem
import com.example.read.ui.theme.DarkPurpleVertical
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecommendationSlides(
    modifier: Modifier = Modifier,
    slides: List<RecommendationItem>
) {
    val pageState = rememberPagerState(pageCount = { slides.size })

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, brush = DarkPurpleVertical)
    ) {

        val slideImages = remember {
            mutableStateListOf<String>()
        }

        slides.forEach {
            it.posterImage?.let { posterImage -> slideImages.add(posterImage) }
        }

        LaunchedEffect(Unit) {
            while (true) {
                delay(5000)
                val nextPage = (pageState.currentPage + 1) % pageState.pageCount
                pageState.animateScrollToPage(nextPage)
            }
        }

        /* HorizontalPagerIndicator(
             pageCount = pageState.pageCount,
             currentPage = pageState.currentPage,
             targetPage = pageState.targetPage,
             currentPageOffsetFraction = pageState.currentPageOffsetFraction
         )*/

        HorizontalPager(
            state = pageState,
            key = { slideImages[it] },
            contentPadding = PaddingValues(2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(204.dp)
        ) { index ->
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(slideImages[index])
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(id = R.string.recommendation_cover_image_content_description),
                filterQuality = FilterQuality.High,
                contentScale = ContentScale.FillBounds,
            )
        }
    }
}

@Composable
fun HorizontalPagerIndicator(
    pageCount: Int,
    currentPage: Int,
    targetPage: Int,
    currentPageOffsetFraction: Float,
    modifier: Modifier = Modifier,
    indicatorColor: Color = Color.DarkGray,
    unselectedIndicatorSize: Dp = 8.dp,
    selectedIndicatorSize: Dp = 10.dp,
    indicatorCornerRadius: Dp = 2.dp,
    indicatorPadding: Dp = 2.dp
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .wrapContentSize()
            .height(selectedIndicatorSize + indicatorPadding * 2)
    ) {

        // draw an indicator for each page
        repeat(pageCount) { page ->
            // calculate color and size of the indicator
            val (color, size) =
                if (currentPage == page || targetPage == page) {
                    // calculate page offset
                    val pageOffset =
                        ((currentPage - page) + currentPageOffsetFraction).absoluteValue
                    // calculate offset percentage between 0.0 and 1.0
                    val offsetPercentage = 1f - pageOffset.coerceIn(0f, 1f)

                    val size =
                        unselectedIndicatorSize + ((selectedIndicatorSize - unselectedIndicatorSize) * offsetPercentage)

                    indicatorColor.copy(
                        alpha = offsetPercentage
                    ) to size
                } else {
                    indicatorColor.copy(alpha = 0.1f) to unselectedIndicatorSize
                }

            // draw indicator
            Box(
                modifier = Modifier
                    .padding(
                        // apply horizontal padding, so that each indicator is same width
                        horizontal = ((selectedIndicatorSize + indicatorPadding * 2) - size) / 2,
                        vertical = size / 4
                    )
                    .clip(RoundedCornerShape(indicatorCornerRadius))
                    .background(color)
                    .width(size)
                    .height(size / 2)
            )
        }
    }
}

@Preview
@Composable
fun PreviewRecommendationSlides() {
    RecommendationSlides(
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 10.dp),
        slides = emptyList()
    )
}