package com.damian.weightliftingtracker.feature_sessions.presentation.sessions.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme

@Composable
fun SessionsTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackArrowClick: () -> Unit
) {
    TopAppBar(modifier = modifier.wrapContentSize(),
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackArrowClick
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.nav_back),
                )
            }
        }
    )
}

@Preview("Sessions Top AppBar Preview")
@Composable
private fun SessionsTopAppBarPreview() {
    Surface {
        WeightLiftingTrackerTheme(darkTheme = true) {
            SessionsTopAppBar(title = "Push Pull Legs", onBackArrowClick = {})
        }
    }
}