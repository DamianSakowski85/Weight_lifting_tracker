package com.damian.weightliftingtracker.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EmptyListLabel(
    isListEmpty: Boolean,
    text: String,
    animationDuration: Int,
    modifier: Modifier
) {
    AnimatedVisibility(
        visible = isListEmpty,
        enter = fadeIn(
            animationSpec = tween(durationMillis = animationDuration)
        ),
        exit = fadeOut(
            animationSpec = tween(durationMillis = animationDuration)
        ),
    ) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
            )
        }
    }
}

@Preview("Empty list label")
@Composable
private fun EmptyListLabelPreview() {
    Surface {
        EmptyListLabel(
            isListEmpty = true,
            text = "The list is empty",
            animationDuration = 0,
            modifier = Modifier.fillMaxSize()
        )
    }
}