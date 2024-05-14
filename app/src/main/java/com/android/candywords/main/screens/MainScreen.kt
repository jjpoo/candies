package com.android.candywords.main.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.android.candywords.R
import com.android.candywords.connectivity.ConnectivityObserver
import com.android.candywords.state.CandyUiEvent
import com.android.candywords.state.CandyUiState
import com.android.candywords.state.ToolbarIcons
import com.android.candywords.utils.CustomProgressIndicator
import com.android.candywords.utils.GameToolbar
import com.android.candywords.utils.OutlinedText
import kotlinx.coroutines.delay

@Composable
fun MainScreen(
    state: CandyUiState,
    uiEvent: (CandyUiEvent) -> Unit = {},
    onPlayClicked: () -> Unit = {},
    onShopClicked: () -> Unit = {},
    closeApp: () -> Unit = {},
    saveSettings: () -> Unit = {},
    retry: () -> Unit = {}
) {

    if (state.connectivityStatus != ConnectivityObserver.Status.Available) {
        uiEvent(CandyUiEvent.ShowNoInternetConnectionPopup(true))
    }

    BackHandler {
        closeApp()
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    val shopItem = ToolbarIcons.entries.find { it.name == ToolbarIcons.SHOP.name }
    val settingsItem = ToolbarIcons.entries.find { it.name == ToolbarIcons.SETTINGS.name }

    val listOfItems = mutableListOf(shopItem, settingsItem)

    var hideContent by remember {
        mutableStateOf(false)
    }

    var isTextVisible by remember {
        mutableStateOf(true)
    }

    val yOffset = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = Unit) {
        expanded = !expanded
//        delay(2000L)
        hideContent = !hideContent
        isTextVisible = !isTextVisible
        uiEvent(CandyUiEvent.OnMenuScreenVisible)
    }

    LaunchedEffect(key1 = hideContent) {
        yOffset.animateTo(
            targetValue = if (hideContent) 350f else 0f,
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearEasing
            )
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_menu),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        AnimatedVisibility(
            visible = state.isMenuScreenVisible,
            enter = fadeIn(animationSpec = tween(1500)),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
        ) {
            GameToolbar(
                listOfToolbarItems = listOfItems.requireNoNulls(),
                toolbarIconModifier = Modifier,
                isGameScreen = false,
                onItemClicked = {
                    when (it) {
                        ToolbarIcons.SHOP.name -> onShopClicked()
                        ToolbarIcons.SETTINGS.name -> {
                            uiEvent(CandyUiEvent.OnSettingsClicked)
                        }
                    }
                }
            )
        }

        Image(
            painter = painterResource(id = R.drawable.img_rocket),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit,
            contentDescription = null
        )

        AnimatedVisibility(
            visible = state.isMenuScreenVisible,
            enter = fadeIn(animationSpec = tween(1500)),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 50.dp)
                .width(250.dp)
                .height(75.dp)
                .clickable {
                    onPlayClicked()
                },
        ) {
            Image(
                painter = painterResource(id = R.drawable.btn_play_hdpi),
                contentScale = ContentScale.Fit,
                contentDescription = null
            )
        }

        ConstraintLoadingImage(
            expanded = expanded,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp)
                .graphicsLayer {
                    translationY = yOffset.value
                }
        )

        AnimatedVisibility(
            visible = isTextVisible,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
        ) {
            OutlinedText(
                modifier = Modifier,
                text = stringResource(id = R.string.please_wait)
            )
        }

        if (state.isSettingsShown) {

            uiEvent(CandyUiEvent.UpdateToolbarButtonState(ToolbarIcons.SETTINGS.name))

            SettingsScreen(
                state = state,
                uiEvent = uiEvent,
                saveSettings = {
                    saveSettings()
                },
                onCrossClicked = {
                    uiEvent(CandyUiEvent.OnSettingsClicked)
                }
            )
        }

        if (state.isNoInternetPopupShown) {
            NoInternetConnectionScreen(
                state = state,
                uiEvent = uiEvent,
                retry = retry
            )
        }
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
    MainScreen(
        state = CandyUiState(isSettingsShown = true)
    )
}

