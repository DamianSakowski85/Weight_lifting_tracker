package com.damian.weightliftingtracker.feature_exercise_log.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.feature_exercise_log.domain.model.ExerciseLog
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme
import java.time.LocalDate

@Composable
fun ExerciseLogItem(
    exerciseLog: ExerciseLog,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    val tagDivider = " "

    Box(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                style = MaterialTheme.typography.h6,
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.set))
                    append(tagDivider)
                    append(exerciseLog.setNumber.toString())
                })

            Spacer(modifier = Modifier.padding(4.dp))

            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(id = R.string.goal),
                        style = MaterialTheme.typography.body1,
                    )

                    Text(
                        color = colorResource(id = R.color.goal),
                        text = buildAnnotatedString {
                            append(exerciseLog.weightGoal.toString())
                            append(tagDivider)
                            append(stringResource(id = R.string.kg))
                        })

                    Text(
                        color = colorResource(id = R.color.goal),
                        text = buildAnnotatedString {
                            append(exerciseLog.repsGoal.toString())
                            append(tagDivider)
                            append(stringResource(id = R.string.reps))
                        })

                    Text(
                        color = colorResource(id = R.color.goal),
                        text = buildAnnotatedString {
                            append(exerciseLog.pauseGoal.toString())
                            append(tagDivider)
                            append(stringResource(id = R.string.sec))
                            append(tagDivider)
                            append(stringResource(id = R.string.pause))
                        })
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(id = R.string.achieved),
                        style = MaterialTheme.typography.body1,
                    )

                    Text(
                        color = colorResource(id = R.color.achieved),
                        text = buildAnnotatedString {
                            append(exerciseLog.weightAchieved.toString())
                            append(tagDivider)
                            append(stringResource(id = R.string.kg))
                        })

                    Text(
                        color = colorResource(id = R.color.achieved),
                        text = buildAnnotatedString {
                            append(exerciseLog.repsAchieved.toString())
                            append(tagDivider)
                            append(stringResource(id = R.string.reps))
                        })

                    Text(
                        color = colorResource(id = R.color.achieved),
                        text = buildAnnotatedString {
                            append(exerciseLog.pauseAchieved.toString())
                            append(tagDivider)
                            append(stringResource(id = R.string.sec))
                            append(tagDivider)
                            append(stringResource(id = R.string.pause))
                        })
                }
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp, 4.dp, 0.dp, 0.dp)
        ) {
            IconButton(
                onClick = onDeleteClick,
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.delete_selected_set),
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }
    }
}

@Preview("Log Item Preview")
@Composable
fun LogItemPreview() {
    Surface {
        WeightLiftingTrackerTheme {
            ExerciseLogItem(
                exerciseLog = ExerciseLog(
                    1,
                    1,
                    8,
                    50.0,
                    120,
                    8,
                    50.0,
                    120,
                    LocalDate.now().toString()
                ),
                onDeleteClick = {}
            )
        }
    }
}