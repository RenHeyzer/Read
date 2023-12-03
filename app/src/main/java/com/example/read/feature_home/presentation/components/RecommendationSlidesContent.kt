package com.example.read.feature_home.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.read.R
import com.example.read.feature_home.domain.models.RecommendationItem
import com.example.read.ui.theme.Purple70
import com.example.read.ui.theme.ReadTheme
import com.example.read.ui.theme.White80
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecommendationSlides(
    modifier: Modifier = Modifier,
    slides: List<RecommendationItem>
) {
    val pagerState = rememberPagerState(pageCount = { slides.size })

    val slideImages = remember {
        mutableStateListOf<String>()
    }

    slides.forEach {
        it.posterImage?.let { posterImage -> slideImages.add(posterImage) }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            if (nextPage == 0) {
                pagerState.animateScrollToPage(nextPage)
            } else {
                pagerState.animateScrollToPage(nextPage, animationSpec = tween(500))
            }
        }
    }

    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            key = { slideImages[it] },
            contentPadding = PaddingValues(start = 10.dp, end = 10.dp),
        ) { index ->
            /* val pageOffset =
                 (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction*/
            val slideSize by animateFloatAsState(
                label = "Slide float animation size",
                targetValue = if (index == pagerState.currentPage) 1f else 0.9f,
                animationSpec = tween(durationMillis = 300)
            )

            SlideItem(
                modifier = Modifier
                    .align(Alignment.Center)
                    .shadow(4.dp, RoundedCornerShape(10.dp))
                    .graphicsLayer {
                        scaleX = slideSize
                        scaleY = slideSize
                    },
                image = slideImages[index]
            )
        }
        Row(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(10.dp),
                    ambientColor = Color.LightGray,
                    spotColor = Color.DarkGray
                ),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Purple70 else White80

                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewRecommendationSlides() {
    ReadTheme {
        RecommendationSlides(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .background(Color.White),
            slides = emptyList()
        )
    }
}

@Composable
fun SlideItem(
    modifier: Modifier = Modifier,
    image: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(id = R.string.recommendation_cover_image_content_description),
            filterQuality = FilterQuality.High,
            contentScale = ContentScale.FillBounds,
        )
    }
}

@Preview
@Composable
fun PreviewSlideItem() {
    ReadTheme {
        SlideItem(
            modifier = Modifier,
            image = "https://p4.wallpaperbetter.com/wallpaper/448/476/880/moon-red-dark-planet-landscape-hd-wallpaper-preview.jpg"
        )
    }
}
