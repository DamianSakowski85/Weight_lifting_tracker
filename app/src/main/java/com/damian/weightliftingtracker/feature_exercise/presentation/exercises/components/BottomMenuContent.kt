package com.damian.weightliftingtracker.feature_exercise.presentation.exercises.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.core.presentation.components.Header

@Composable
fun BottomMenuContent(
    title: String?,
    onUpdateClick: () -> Unit,
    onShowBarChartClick: () -> Unit,
    onShowDatePickerClick: () -> Unit,
    onClearDataClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        if (title != null) {
            Header(
                text = title,
            )
        }

        Divider(startIndent = 0.dp)

        TextButton(
            onClick = onShowBarChartClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.BarChart,
                contentDescription = stringResource(id = R.string.show_chart),
                tint = MaterialTheme.colors.onSurface,
            )
            Text(
                text = stringResource(id = R.string.show_chart),
                fontSize = 18.sp,
                color = MaterialTheme.colors.onSurface,
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        TextButton(
            onClick = onShowDatePickerClick,
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = stringResource(id = R.string.show_all_sessions),
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp)
            )
            Text(
                text = stringResource(id = R.string.show_all_sessions),
                fontSize = 18.sp,
                color = MaterialTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        TextButton(
            onClick = onUpdateClick,
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = stringResource(id = R.string.edit),
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp)
            )
            Text(
                text = stringResource(id = R.string.edit),
                fontSize = 18.sp,
                color = MaterialTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        TextButton(
            onClick = onClearDataClick,
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = stringResource(id = R.string.clear_data),
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp)
            )
            Text(
                text = stringResource(id = R.string.clear_data),
                fontSize = 18.sp,
                color = MaterialTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        TextButton(
            onClick = onDeleteClick,
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(id = R.string.delete),
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp)
            )
            Text(
                text = stringResource(id = R.string.delete),
                fontSize = 18.sp,
                color = MaterialTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview("Exercise Menu Preview")
@Composable
private fun ExerciseMenuPreview() {
    Surface {
        BottomMenuContent(
            title = "Training X",
            onDeleteClick = {},
            onUpdateClick = {},
            onShowBarChartClick = {},
            onShowDatePickerClick = {},
            onClearDataClick = {},
            modifier = Modifier.padding(8.dp)
        )
    }
}