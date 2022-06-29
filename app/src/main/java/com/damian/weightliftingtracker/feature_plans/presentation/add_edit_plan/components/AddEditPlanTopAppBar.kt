package com.damian.weightliftingtracker.feature_plans.presentation.add_edit_plan.components

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
fun AddEditPlanTopAppBar(
    modifier: Modifier = Modifier,
    closeAction: () -> Unit
) {
    TopAppBar(modifier = modifier.wrapContentSize(),
        title = {
            Text(
                text = stringResource(id = R.string.plan)
            )
        },
        navigationIcon = {
            IconButton(
                onClick = closeAction
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close)
                )
            }
        }
    )
}

@Preview("Add Edit Plan Top AppBar Preview")
@Composable
private fun AddEditPlanTopAppBarPreview() {
    Surface {
        WeightLiftingTrackerTheme(darkTheme = true) {
            AddEditPlanTopAppBar(closeAction = {})
        }
    }
}