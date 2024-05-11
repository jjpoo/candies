package com.android.candywords.launcher

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.android.candywords.R

@Composable
fun CustomProgressIndicator(
    modifier: Modifier = Modifier,
    imageResFilled: Int,
    imageResUnFilled: Int,
    progressMinWidth: Dp,
    progressMaxWidth: Dp,
    expanded: Boolean
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (box, imageNotFilled, imageFilled) = createRefs()

        Image(
            painter = painterResource(id = imageResUnFilled),
            modifier = Modifier
                .constrainAs(imageNotFilled) {
                    start.linkTo(box.start)
                    top.linkTo(box.top)
                }
                .width(248.dp)
                .height(40.dp),
            contentDescription = null
        )
        Image(
            painter = painterResource(id = imageResFilled),
            modifier = Modifier
                .constrainAs(imageFilled) {
                    start.linkTo(imageNotFilled.start)
                    top.linkTo(imageNotFilled.top)
                    bottom.linkTo(imageNotFilled.bottom)
                }
                .animateContentSize(
                    animationSpec = spring(
                        stiffness = 40f
                    )
                )
                .padding(7.dp)
                .width(width = if (expanded) progressMaxWidth else progressMinWidth)
                .height(20.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomProgressIndPreview() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Blue)) {
        CustomProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            imageResFilled = R.drawable.loading_fill,
            imageResUnFilled = R.drawable.loading_bg,
            progressMinWidth = 0.dp,
            progressMaxWidth = 234.dp,
            expanded = true
        )
    }

}

