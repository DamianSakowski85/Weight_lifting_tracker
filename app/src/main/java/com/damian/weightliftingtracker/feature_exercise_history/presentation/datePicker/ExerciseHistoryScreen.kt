package com.damian.weightliftingtracker.feature_exercise_history.presentation.datePicker

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.core.presentation.components.EmptyListLabel
import com.damian.weightliftingtracker.core.presentation.components.Header
import com.damian.weightliftingtracker.core.presentation.components.PreviousExerciseLogItem
import com.damian.weightliftingtracker.feature_exercise_history.data.repository.FakeExerciseHistoryRepository
import com.damian.weightliftingtracker.feature_exercise_history.domain.model.CalendarConstraintsModel
import com.damian.weightliftingtracker.feature_exercise_history.domain.use_case.CalculateVolumeUseCase
import com.damian.weightliftingtracker.feature_exercise_history.domain.use_case.ExerciseHistoryUseCases
import com.damian.weightliftingtracker.feature_exercise_history.domain.use_case.GetCalendarConstrainUseCase
import com.damian.weightliftingtracker.feature_exercise_history.domain.use_case.LoadLogUseCase
import com.damian.weightliftingtracker.feature_exercise_log.presentation.components.LogSummaryItem
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.flow.collectLatest
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun ExerciseDatePickerScreen(
    closeScreen: () -> Unit,
    viewModel: ExerciseHistoryViewModel,
) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    lateinit var datePicker: MaterialDatePicker<Long>
    val datePickerTitle = stringResource(id = R.string.select_date)

    fun setupCalendarConstraints(constraints: CalendarConstraintsModel): CalendarConstraints {
        return CalendarConstraints.Builder()
            .setStart(constraints.startDate.timeInMillis)
            .setEnd(constraints.endDate.timeInMillis)
            .setValidator(
                CustomCalendarValidatorKt(
                    constraints.dates
                )
            )
            .build()
    }

    fun setupDataPicker(calendarConstraintsData: CalendarConstraintsModel) {

        val calendarConstraints = setupCalendarConstraints(calendarConstraintsData)

        val startDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val parsedDate = LocalDate.parse(calendarConstraintsData.previousDate)

        val year = parsedDate.year
        val month = parsedDate.monthValue - 1
        val day = parsedDate.dayOfMonth
        startDate.set(year, month, day)

        datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(datePickerTitle)
            .setCalendarConstraints(calendarConstraints)
            .setSelection(startDate.timeInMillis)
            .setTheme(R.style.ThemeOverlay_App_DatePicker)
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val triggerTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(selection),
                TimeZone.getDefault().toZoneId()
            )
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            viewModel.onEvent(ExerciseHistoryEvent.OnDateClick(triggerTime.format(formatter)))
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ExerciseHistoryViewModel.UiEvent.ShowDatePicker -> {
                    val appCompatActivity = context as AppCompatActivity
                    viewModel.exerciseHistoryState.value.calendarConstraintsModel?.let {
                        setupDataPicker(it)
                        datePicker.show(
                            appCompatActivity.supportFragmentManager,
                            datePicker.toString()
                        )
                    }
                }
                is ExerciseHistoryViewModel.UiEvent.NavBack -> {
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
                            append(viewModel.exerciseHistoryState.value.exerciseName)
                            append(" • ")
                            append(stringResource(id = R.string.history))
                        }
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(ExerciseHistoryEvent.OnBackPressed)
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
        it.calculateBottomPadding()
        it.calculateTopPadding()

        Column {

            EmptyListLabel(
                isListEmpty = viewModel.exerciseHistoryState.value.isLogEmpty,
                text = stringResource(id = R.string.no_data_to_show),
                animationDuration = 0,
                modifier = Modifier.fillMaxSize()
            )

            Header(
                buildAnnotatedString {
                    append(stringResource(R.string.session_from))
                    append(" ")
                    append(viewModel.exerciseHistoryState.value.date)
                }.toString()
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {

                items(viewModel.exerciseHistoryState.value.logs) { log ->
                    PreviousExerciseLogItem(
                        exerciseLog = log,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Divider(startIndent = 0.dp)
                }
                item {
                    LogSummaryItem(
                        isVisible = true,
                        volume = viewModel.exerciseHistoryState.value.weightVolume,
                        pause = viewModel.exerciseHistoryState.value.pauseVolume
                    )
                }
                item {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 8.dp),
                        onClick = { viewModel.onEvent(ExerciseHistoryEvent.OnPickDate) },
                    ) {
                        Text(
                            text = stringResource(id = R.string.select_session),
                            fontSize = 18.sp,
                        )
                    }
                }
            }
        }
    }
}

@Preview("Exercise History Preview")
@Composable
fun ExerciseHistoryPreview() {
    val fakeRepo = FakeExerciseHistoryRepository()
    Surface {
        WeightLiftingTrackerTheme {
            ExerciseDatePickerScreen(
                closeScreen = {},
                viewModel = ExerciseHistoryViewModel(
                    SavedStateHandle(),
                    ExerciseHistoryUseCases(
                        loadLogUseCase = LoadLogUseCase(fakeRepo),
                        calculateVolumeUseCase = CalculateVolumeUseCase(),
                        getCalendarConstrainUseCase = GetCalendarConstrainUseCase(fakeRepo)
                    )
                )
            )
        }
    }
}