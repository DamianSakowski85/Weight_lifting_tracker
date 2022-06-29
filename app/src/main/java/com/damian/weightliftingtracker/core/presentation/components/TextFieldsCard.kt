package com.damian.weightliftingtracker.core.presentation.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.core.utli.TestTags
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme

@Composable
fun TextFieldsCard(
    name: String,
    description: String,
    onEnterName: ((name: String) -> Unit)?,
    onEnterDescription: ((desc: String) -> Unit)?,
) {
    Card(
        elevation = 0.dp,
        shape = RoundedCornerShape(4.dp)
    ) {
        Column {
            TextField(
                value = name,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                isError = false,
                onValueChange = { onEnterName?.invoke(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 8.dp, 8.dp, 4.dp)
                    .testTag(TestTags.NAME_TEXT_FIELD),
                label = { Text(stringResource(id = R.string.enter_name)) }
            )

            TextField(
                value = description,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text,
                ),

                onValueChange = { onEnterDescription?.invoke(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 4.dp, 8.dp, 8.dp)
                    .testTag(TestTags.DESC_TEXT_FIELD),
                label = { Text(stringResource(R.string.enter_description)) }
            )
        }
    }
}

@Preview("Text Field Card Preview")
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
private fun TextFieldCardPreview() {
    Surface {
        WeightLiftingTrackerTheme(darkTheme = true) {
            TextFieldsCard(
                name = "Bench Press",
                description = "Desc of bench press",
                onEnterName = {},
                onEnterDescription = {})
        }
    }
}