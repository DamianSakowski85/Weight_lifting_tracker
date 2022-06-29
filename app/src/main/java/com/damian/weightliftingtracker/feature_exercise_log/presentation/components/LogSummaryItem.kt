package com.damian.weightliftingtracker.feature_exercise_log.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme

@Composable
fun LogSummaryItem(
    isVisible: Boolean,
    volume: String,
    pause: String
) {
    val divider = " : "
    val tagDivider = " "

    val weightText = buildAnnotatedString {
        append(stringResource(id = R.string.volume))
        append(divider)

        val tagStyle = MaterialTheme.typography.overline.toSpanStyle().copy(
            background = MaterialTheme.colors.primary.copy(alpha = 0.1f)
        )

        withStyle(tagStyle) {
            append(text = volume)
            append(tagDivider)
            append(stringResource(id = R.string.kg))
        }
    }

    val pauseText = buildAnnotatedString {
        append(stringResource(id = R.string.pause_time))
        append(divider)

        val tagStyle = MaterialTheme.typography.overline.toSpanStyle().copy(
            background = MaterialTheme.colors.primary.copy(alpha = 0.1f)
        )

        withStyle(tagStyle) {
            append(text = pause)
            append(tagDivider)
            append(stringResource(id = R.string.sec))
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(
            modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp)
        ) {
            Row {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = weightText,
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
            Spacer(modifier = Modifier.padding(2.dp))
            Row {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = pauseText,
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
        }
    }
}

@Preview("Log Summary Item Preview")
@Composable
fun LogSummaryItemPreview() {
    Surface {
        WeightLiftingTrackerTheme {
            LogSummaryItem(isVisible = true, volume = "1200", pause = "120")
        }
    }
}