package com.android.candywords

import android.app.GameState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.android.candywords.state.CandyUiState
import com.android.candywords.utils.GameToolbar

@Composable
fun MenuScreen(
    state: CandyUiState
) {
    Column {
        GameToolbar(
            state = state
        )
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
        }
    }
}

@Preview
@Composable
fun MenuScreenPreview() {
    MenuScreen(
        state = CandyUiState(),

    )
}
