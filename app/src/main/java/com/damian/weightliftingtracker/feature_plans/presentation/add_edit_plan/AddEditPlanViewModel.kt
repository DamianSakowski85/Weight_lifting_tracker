package com.damian.weightliftingtracker.feature_plans.presentation.add_edit_plan

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.core.data_source.StringProvider
import com.damian.weightliftingtracker.feature_plans.domain.model.InvalidPlanException
import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import com.damian.weightliftingtracker.feature_plans.domain.use_case.PlanUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val STATE_KEY_PLAN_ID = "planId"
const val STATE_KEY_NAME = "entered_plan_name"
const val STATE_KEY_DESCRIPTION = "entered_plan_description"

@HiltViewModel
class AddEditPlanViewModel @Inject constructor(
    private val planUseCases: PlanUseCases,
    private val savedStateHandle: SavedStateHandle,
    private val stringProvider: StringProvider
) : ViewModel() {

    private var _currentPlan: Plan? = null
    private val _addEditPlanState = mutableStateOf(AddEditPlanState())
    val addEditPlanState: State<AddEditPlanState> = _addEditPlanState

    init {
        viewModelScope.launch {
            savedStateHandle.get<Int>(STATE_KEY_PLAN_ID)?.let {
                _currentPlan = planUseCases.getPlanByIdUseCase(it)
            }
            _addEditPlanState.value = addEditPlanState.value.copy(
                name = savedStateHandle.get<String>(STATE_KEY_NAME)
                    ?: _currentPlan?.planName ?: "",

                description = savedStateHandle.get<String>(STATE_KEY_DESCRIPTION)
                    ?: _currentPlan?.planDescription ?: ""
            )
        }
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: AddEditPlanEvent) {
        when (event) {
            is AddEditPlanEvent.OnEnterName -> updateNameState(event.value)

            is AddEditPlanEvent.OnEnterDescription -> updateDescriptionState(event.value)

            is AddEditPlanEvent.OnSavePlan -> savePlan()

            is AddEditPlanEvent.OnClose -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.NavBack)
                }
            }
        }
    }

    private fun savePlan() {
        viewModelScope.launch {
            try {
                _currentPlan?.let {
                    val planToUpdate = it.copy(
                        planName = addEditPlanState.value.name,
                        planDescription = addEditPlanState.value.description
                    )
                    planUseCases.updatePlanUseCase(planToUpdate)

                    _eventFlow.emit(
                        UiEvent.ShowMessage(
                            message = stringProvider.getString(R.string.plan_updated)
                        )
                    )
                    _eventFlow.emit(UiEvent.NavBack)
                } ?: run {
                    planUseCases.addPlanUseCase(
                        Plan(
                            planName = addEditPlanState.value.name,
                            planDescription = addEditPlanState.value.description,
                            timestamp = System.currentTimeMillis()
                        )
                    )
                    _eventFlow.emit(
                        UiEvent.ShowMessage(
                            message = stringProvider.getString(R.string.plan_added)
                        )
                    )
                    _eventFlow.emit(UiEvent.NavBack)
                }

            } catch (exception: InvalidPlanException) {
                exception.message?.let {
                    _eventFlow.emit(
                        UiEvent.ShowMessage(
                            message = it
                        )
                    )
                }
            }
        }
    }

    private fun updateNameState(name: String) {
        _addEditPlanState.value = addEditPlanState.value.copy(
            name = name
        )
        savedStateHandle[STATE_KEY_NAME] = name
    }

    private fun updateDescriptionState(description: String) {
        _addEditPlanState.value = addEditPlanState.value.copy(
            description = description
        )
        savedStateHandle[STATE_KEY_DESCRIPTION] = description
    }

    sealed class UiEvent {
        data class ShowMessage(val message: String) : UiEvent()
        object NavBack : UiEvent()
    }
}