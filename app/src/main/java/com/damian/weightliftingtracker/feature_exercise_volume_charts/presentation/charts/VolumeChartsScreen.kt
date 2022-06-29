package com.damian.weightliftingtracker.feature_exercise_volume_charts.presentation.charts

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.core.presentation.components.EmptyListLabel
import com.damian.weightliftingtracker.core.utli.TestTags
import com.damian.weightliftingtracker.feature_exercise_volume_charts.data.repository.FakeExerciseVolumeRepo
import com.damian.weightliftingtracker.feature_exercise_volume_charts.domain.use_case.GetVolumeListUseCase
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot
import kotlinx.coroutines.flow.collectLatest

@Composable
fun BarChartsVolumeScreen(
    closeScreen: () -> Unit,
    viewModel: VolumeChartsViewModel
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is VolumeChartsViewModel.UiEvent.PopBackStack -> {
                    closeScreen()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = buildAnnotatedString {
                            append(viewModel.volumeChartsState.value.exerciseName)
                            append(" • ")
                            append(stringResource(id = R.string.volume))
                        }
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(VolumeChartsEvent.OnBackPressed)
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = stringResource(id = R.string.close),
                        )
                    }
                }
            )
        },
        scaffoldState = scaffoldState
    ) {
        it.calculateTopPadding()

        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            EmptyListLabel(
                isListEmpty = viewModel.volumeChartsState.value.isBarDataEmpty,
                text = stringResource(id = R.string.no_data_to_show),
                animationDuration = 0,
                modifier = Modifier.fillMaxSize()
            )

            androidx.compose.animation.AnimatedVisibility(
                visible = viewModel.volumeChartsState.value.showVolumeChart,
                enter = fadeIn(
                    animationSpec = tween(durationMillis = 0)
                ),
                exit = fadeOut(
                    animationSpec = tween(durationMillis = 0)
                ),
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.volume_kg),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    viewModel.volumeChartsState.value.volumeTest?.let { volumeList ->
                        LineGraph(
                            plot = LinePlot(
                                listOf(
                                    LinePlot.Line(
                                        volumeList,
                                        LinePlot.Connection(Color.Green, 2.dp),
                                        LinePlot.Intersection(Color.Green, 5.dp),
                                        LinePlot.Highlight(Color.Green, 5.dp),
                                        LinePlot.AreaUnderLine(Color.Green, 0.3f)
                                    )
                                ),

                                paddingRight = 8.dp,
                                selection = LinePlot.Selection(
                                    highlight = LinePlot.Connection(
                                        Color.Green,
                                        strokeWidth = 2.dp,
                                        pathEffect = PathEffect.dashPathEffect(
                                            floatArrayOf(
                                                40f,
                                                20f
                                            )
                                        )
                                    )
                                ),
                                xAxis = LinePlot.XAxis(unit = 1f),
                                yAxis = LinePlot.YAxis(steps = 8, roundToInt = true),
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(260.dp)
                                .testTag(TestTags.VOLUME_TEST_TAG)
                        )
                    }
                }
            }

            Divider(startIndent = 0.dp, modifier = Modifier.padding(vertical = 8.dp))

            androidx.compose.animation.AnimatedVisibility(
                visible = viewModel.volumeChartsState.value.showVolumeChart,
                enter = fadeIn(
                    animationSpec = tween(durationMillis = 0)
                ),
                exit = fadeOut(
                    animationSpec = tween(durationMillis = 0)
                ),
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.pause_time_sec),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    viewModel.volumeChartsState.value.pauseTime?.let { pauseList ->
                        LineGraph(
                            plot = LinePlot(
                                listOf(
                                    LinePlot.Line(
                                        pauseList,
                                        LinePlot.Connection(Color.Green, 2.dp),
                                        LinePlot.Intersection(Color.Green, 5.dp),
                                        LinePlot.Highlight(Color.Green, 5.dp),
                                        LinePlot.AreaUnderLine(Color.Green, 0.3f)
                                    )
                                ),
                                selection = LinePlot.Selection(
                                    highlight = LinePlot.Connection(
                                        Color.Green,
                                        strokeWidth = 2.dp,
                                        pathEffect = PathEffect.dashPathEffect(
                                            floatArrayOf(
                                                40f,
                                                20f
                                            )
                                        )
                                    )
                                ),
                                xAxis = LinePlot.XAxis(unit = 1f),
                                yAxis = LinePlot.YAxis(steps = 8, roundToInt = true),
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(260.dp)
                                .testTag(TestTags.VOLUME_PAUSE_TEST_TAG),
                        )
                    }
                }
            }
        }
    }
}

@Preview("Exercise Volume Charts Preview")
@Composable
fun ExerciseVolumeChartsPreview() {
    val fakeRepo = FakeExerciseVolumeRepo()
    Surface {
        WeightLiftingTrackerTheme {
            BarChartsVolumeScreen(
                closeScreen = {},
                viewModel = VolumeChartsViewModel(
                    SavedStateHandle(),
                    GetVolumeListUseCase(fakeRepo)
                )
            )
        }
    }
}

@Preview("Line Graph Preview")
@Composable
fun LineGraphPreview() {
    val volumeList = listOf(
        DataPoint(1.0f, 50f),
        DataPoint(2.0f, 75f),
        DataPoint(3.0f, 50f),
        DataPoint(4.0f, 75f),
        DataPoint(5.0f, 50f),
        DataPoint(6.0f, 75f)
    )
    Surface {
        WeightLiftingTrackerTheme {
            Column(Modifier.padding(16.dp)) {
                LineGraph(
                    plot = LinePlot(
                        listOf(
                            LinePlot.Line(
                                volumeList,
                                LinePlot.Connection(Color.Green, 2.dp),
                                LinePlot.Intersection(Color.Green, 5.dp),
                                LinePlot.Highlight(Color.Green, 5.dp),
                                LinePlot.AreaUnderLine(Color.Green, 0.3f)
                            )
                        ),

                        paddingRight = 8.dp,
                        selection = LinePlot.Selection(
                            highlight = LinePlot.Connection(
                                Color.Green,
                                strokeWidth = 2.dp,
                                pathEffect = PathEffect.dashPathEffect(
                                    floatArrayOf(
                                        40f,
                                        20f
                                    )
                                )
                            )
                        ),
                        xAxis = LinePlot.XAxis(unit = 1f),
                        yAxis = LinePlot.YAxis(steps = 8, roundToInt = true),
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .testTag(TestTags.VOLUME_TEST_TAG)
                )
            }
        }
    }
}