package com.damian.weightliftingtracker.feature_exercise_log.presentation.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.damian.weightliftingtracker.R

@Composable
fun LogTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onCloseScreen: () -> Unit
) {
    TopAppBar(modifier = modifier.wrapContentSize(),
        title = {
            Text(
                text = title,
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    onCloseScreen()
                }
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close)
                )
            }
        }
    )
}

@Preview("Log Top App Bar Preview")
@Composable
fun LogTopAppBarPreview() {
    LogTopAppBar(
        title = "Bench Press",
        onCloseScreen = { }
    )
}