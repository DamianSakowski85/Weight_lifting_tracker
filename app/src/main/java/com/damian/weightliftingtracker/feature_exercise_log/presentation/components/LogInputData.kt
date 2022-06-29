package com.damian.weightliftingtracker.feature_exercise_log.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme
@Composable
fun LogInputData(
    modifier: Modifier = Modifier,
    label: String,
    value: String,

    onDecreaseValue: () -> Unit,
    onEnterValue: ((value: String) -> Unit)?,
    onIncreaseValue: () -> Unit,
    keyboardOptions: KeyboardOptions
) {
    Row(modifier = modifier) {
        IconButton(
            onClick = onDecreaseValue,
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = stringResource(id = R.string.remove),
                tint = MaterialTheme.colors.onSurface,
            )
        }
        OutlinedTextField(
            value = value,
            label = { Text(text = label) },
            onValueChange = { onEnterValue?.invoke(it) },
            modifier = Modifier.weight(1f),
            keyboardOptions = keyboardOptions
        )
        IconButton(
            onClick = onIncreaseValue,
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.add),
                tint = MaterialTheme.colors.onSurface
            )
        }
    }
}


@Preview("Log Input Data Preview")
@Composable
fun LogInputDataPreview() {
    Surface {
        WeightLiftingTrackerTheme {
            LogInputData(
                modifier = Modifier.padding(8.dp),
                label = "Goal",
                value = "8",
                onDecreaseValue = {  },
                onEnterValue = {},
                onIncreaseValue = {},
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number,
                )
            )
        }
    }
}