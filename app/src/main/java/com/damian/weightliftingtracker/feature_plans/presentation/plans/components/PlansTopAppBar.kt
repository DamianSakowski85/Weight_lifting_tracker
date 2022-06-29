package com.damian.weightliftingtracker.feature_plans.presentation.plans.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme

@Composable
fun PlansTopAppBar(
    modifier: Modifier = Modifier,
    onToggleOrderSection: () -> Unit
) {
    TopAppBar(modifier = modifier.wrapContentSize(),
        title = {
            Text(
                text = stringResource(id = R.string.your_plans),
            )
        },
        actions = {
            IconButton(
                onClick = onToggleOrderSection
            ) {
                Icon(
                    imageVector = Icons.Default.Sort,
                    contentDescription = stringResource(R.string.sort)
                )
            }
        }
    )
}

@Preview("Plans Top AppBar Preview")
@Composable
private fun PlansTopAppBarPreview() {
    Surface {
        WeightLiftingTrackerTheme(darkTheme = true) {
            PlansTopAppBar(onToggleOrderSection = {})
        }
    }
}