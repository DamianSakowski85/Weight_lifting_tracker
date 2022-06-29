package com.damian.weightliftingtracker.feature_exercise.presentation.exercises.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.feature_exercise.domain.model.Exercise
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme

@Composable
fun ExerciseItem(
    exercise: Exercise,
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit
) {
    Card(
        elevation = 0.dp,
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = exercise.exerciseName }
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = exercise.exerciseName,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))

                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(Alignment.CenterVertically),
                        text = exercise.exerciseDescription,
                        style = MaterialTheme.typography.body2,
                        maxLines = 10,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                TextButton(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .testTag(exercise.exerciseName),
                    onClick = onMenuClick
                ) {
                    Text(
                        text = stringResource(id = R.string.menu_icon),
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
    }
}

@Preview("Exercise Item Preview")
@Composable
private fun ExerciseItemPreview() {
    Surface {
        WeightLiftingTrackerTheme(darkTheme = true) {
            ExerciseItem(
                exercise = Exercise(1, 1, "Training X", "desc"),
                onMenuClick = {}
            )
        }
    }
}

