package com.damian.weightliftingtracker.core.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.core.utli.TestTags
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme

@Composable
fun DeleteDialog(
    title: String,
    message: String,
    isVisible: Boolean,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    if (isVisible) {
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
            AlertDialog(
                onDismissRequest = { onDismissRequest() },
                title = {
                    Text(title)
                },
                text = {
                    Text(message)
                },
                confirmButton = {
                    TextButton(
                        onClick = onConfirmClick,
                    ) {
                        Text(stringResource(R.string.yes))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = onDismissClick,
                    ) {
                        Text(stringResource(R.string.cancel))
                    }
                },
                modifier = Modifier.testTag(TestTags.ALERT_DIALOG_SECTION)
            )
        }
    }
}

@Preview("Delete Dialog")
@Composable
private fun DeleteDialogPreview() {
    Surface {
        WeightLiftingTrackerTheme(darkTheme = true) {
            DeleteDialog(
                title = "Delete",
                message = "Delete selected item?",
                isVisible = true,
                onDismissRequest = {},
                onConfirmClick = {},
                onDismissClick = {}
            )
        }
    }
}