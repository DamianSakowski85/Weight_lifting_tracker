package com.damian.weightliftingtracker.feature_sessions.presentation.sessions.components

import androidx.compose.foundation.clickable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.damian.weightliftingtracker.feature_sessions.domain.model.Session
import com.damian.weightliftingtracker.feature_sessions.presentation.sessions.SessionsState
import com.damian.weightliftingtracker.ui.theme.WeightLiftingTrackerTheme
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SessionItemTest {

    private val _state = mutableStateOf(SessionsState())
    private val state: State<SessionsState> = _state

    private val sessionToAdd = Session(
        1,
        1,
        "Push",
        "Monday"
    )
    private var isMenuVisible: Boolean = false

    @get: Rule
    val composeRule = createComposeRule()

    @Before
    fun setUp() {
        composeRule.setContent {
            WeightLiftingTrackerTheme {
                SessionItem(
                    session = sessionToAdd,
                    modifier = Modifier.clickable {
                        _state.value = state.value.copy(
                            selectedSession = sessionToAdd
                        )
                    },
                    onMenuClick = {
                        isMenuVisible = true
                    },
                )
            }
        }
    }

    @Test
    fun itemClickTest() {
        composeRule.onNodeWithContentDescription(sessionToAdd.sessionName).assertHasClickAction()
        composeRule.onNodeWithContentDescription(sessionToAdd.sessionName).performClick()
        Assert.assertEquals(sessionToAdd, _state.value.selectedSession)
    }

    @Test
    fun menuClickTest() {
        composeRule.onNodeWithTag(sessionToAdd.sessionName).assertHasClickAction()
        composeRule.onNodeWithTag(sessionToAdd.sessionName).performClick()
        Assert.assertTrue(isMenuVisible)
    }
}