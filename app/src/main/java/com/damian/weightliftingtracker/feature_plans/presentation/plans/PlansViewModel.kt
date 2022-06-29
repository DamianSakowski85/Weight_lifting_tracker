package com.damian.weightliftingtracker.feature_plans.presentation.plans

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damian.weightliftingtracker.R
import com.damian.weightliftingtracker.core.data_source.StringProvider
import com.damian.weightliftingtracker.feature_plans.domain.model.Plan
import com.damian.weightliftingtracker.feature_plans.domain.use_case.PlanUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlansViewModel @Inject constructor(
    private val planUseCases: PlanUseCases,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _state = mutableStateOf(PlansState())
    val state: State<PlansState> = _state

    private var getPlansJob: Job? = null
    private var orderJob: Job? = null

    init {
        getOrder()
        getPlans()
    }

    fun onEvent(event: PlansEvent) {
        when (event) {
            is PlansEvent.OnAddPlan -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.NavToCreatePlan)
                }
            }
            is PlansEvent.OnPlanMenuClick -> {
                _state.value = state.value.copy(
                    selectedPlan = event.selectedPlan
                )
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.OpenContextMenu(event.selectedPlan.planName))
                }
            }
            is PlansEvent.OnEditPlan -> {
                _state.value.selectedPlan?.let {
                    viewModelScope.launch {
                        _eventFlow.emit(UiEvent.CloseContextMenu)
                        _eventFlow.emit(UiEvent.NavToUpdateScreen(it.id))
                    }
                }
            }
            is PlansEvent.OnDelete -> {
                _state.value = state.value.copy(
                    isDeleteDialogVisible = true
                )
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.CloseContextMenu)
                }
            }
            is PlansEvent.OnConfirmDeletion -> {
                _state.value.selectedPlan?.let {
                    viewModelScope.launch {
                        deletePlan(it)
                    }
                }
            }
            is PlansEvent.OnDismissDialog -> {
                _state.value = state.value.copy(
                    selectedPlan = null,
                    isDeleteDialogVisible = false
                )
            }
            is PlansEvent.OnPlanClick -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.NavToItemDetails(event.planId, event.planName))
                }
            }
            is PlansEvent.OnToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
            is PlansEvent.OnOrderChange -> {
                if (state.value.planOrder::class == event.planOrder::class &&
                    state.value.planOrder.orderType == event.planOrder.orderType
                ) {
                    return
                }
                viewModelScope.launch {
                    planUseCases.updateSortOrderInPrefUseCase(event.planOrder)
                }
            }
        }
    }

    private suspend fun deletePlan(plan: Plan) {
        kotlin.runCatching {
            planUseCases.deletePlanUseCase(plan.id)
        }.onSuccess {
            _eventFlow.emit(
                UiEvent.ShowMessage(
                    message = plan.planName + " " + stringProvider.getString(R.string.deleted)
                )
            )
        }.onFailure {
            _eventFlow.emit(
                UiEvent.ShowMessage(
                    message = it.message.toString()
                )
            )
        }
        _state.value = state.value.copy(
            selectedPlan = null,
            isDeleteDialogVisible = false
        )
    }

    private fun getOrder() {
        orderJob?.cancel()

        orderJob = planUseCases.getSortOrderFromPrefUseCase().onEach { order ->
            _state.value = state.value.copy(
                planOrder = order
            )
        }.launchIn(viewModelScope)
    }

    private fun getPlans() {
        getPlansJob?.cancel()

        getPlansJob = planUseCases.getPlanUseCases().onEach { plans ->
            if (plans.isNotEmpty()) {
                _state.value = state.value.copy(
                    isEmptyListLabelVisible = false,
                    plans = plans
                )
            } else {
                _state.value = state.value.copy(
                    isEmptyListLabelVisible = true,
                    plans = plans
                )
            }
        }.launchIn(viewModelScope)
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowMessage(val message: String) : UiEvent()
        object NavToCreatePlan : UiEvent()
        data class OpenContextMenu(val planName: String) : UiEvent()
        object CloseContextMenu : UiEvent()
        data class NavToUpdateScreen(val planId: Int) : UiEvent()
        data class NavToItemDetails(val planId: Int, val planName: String) : UiEvent()
    }
}