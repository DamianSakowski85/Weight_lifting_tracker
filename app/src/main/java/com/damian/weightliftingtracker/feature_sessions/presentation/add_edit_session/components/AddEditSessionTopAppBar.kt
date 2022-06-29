package com.damian.weightliftingtracker.feature_sessions.presentation.add_edit_session.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme

@Composable
fun AddEditSessionTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onCloseClick: () -> Unit
) {
    TopAppBar(modifier = modifier.wrapContentSize(),
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onCloseClick
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close),
                )
            }
        }
    )
}

@Preview("Add Edit Session TopAppBar Preview")
@Composable
private fun AddEditSessionTopAppBarPreview() {
    Surface {
        WeightLiftingTrackerTheme(darkTheme = true) {
            AddEditSessionTopAppBar(title = "Session", onCloseClick = {})
        }
    }
}