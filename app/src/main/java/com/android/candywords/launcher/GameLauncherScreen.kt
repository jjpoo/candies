package com.android.candywords.launcher

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.android.candywords.R
import com.android.candywords.utils.OutlinedText
import kotlinx.coroutines.delay

@Composable
fun GameLauncherScreen(
    text: String,
    launcherBacImageRes: Int,
    modifier: Modifier = Modifier,
    expanded: Boolean,
    navigateToMainScreen: () -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        expanded = !expanded
        delay(2000L)
        navigateToMainScreen()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_menu),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Image(
            painter = painterResource(id = R.drawable.img_rocket),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit,
            contentDescription = null
        )
        ConstraintLoadingImage(
            expanded = expanded,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp)
        )

        OutlinedText(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            text = stringResource(id = R.string.please_wait)
        )
    }
}


@Composable
fun ConstraintLoadingImage(
    expanded: Boolean,
    modifier: Modifier = Modifier
) {

    ConstraintLayout(
        modifier = modifier
    ) {
        val (image, loader) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.popup_bear),
            modifier = Modifier
                .width(355.dp)
                .height(256.dp)
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            contentScale = ContentScale.Fit,
            contentDescription = null
        )
        CustomProgressIndicator(
            modifier = Modifier
                .constrainAs(loader) {
                    start.linkTo(image.start)
                    end.linkTo(image.end)
                    bottom.linkTo(image.bottom)
                }
                .padding(bottom = 20.dp),
            imageResFilled = R.drawable.loading_fill,
            imageResUnFilled = R.drawable.loading_bg,
            progressMinWidth = 0.dp,
            progressMaxWidth = 234.dp,
            expanded = expanded
        )
    }
}

@Preview
@Composable
fun GameLauncherScreenPreview() {
    GameLauncherScreen(
        text = "Loading",
        R.drawable.bg_menu,
        expanded = true,
        navigateToMainScreen = {}
    )
}

