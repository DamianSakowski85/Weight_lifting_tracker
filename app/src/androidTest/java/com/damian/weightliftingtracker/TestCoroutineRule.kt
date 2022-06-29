package com.damian.weightliftingtracker

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement


class TestCoroutineRule : TestRule {
    private val testCoroutineDispatcher = StandardTestDispatcher()

    private val testCoroutineScope = TestScope(testCoroutineDispatcher)

    override fun apply(base: Statement, description: Description): Statement = object : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            Dispatchers.setMain(testCoroutineDispatcher)

            base.evaluate()

            Dispatchers.resetMain()
            testCoroutineScope.cancel()
        }
    }

    fun runBlockingTest(block: suspend TestScope.() -> Unit) =
        testCoroutineScope.runTest { block() }
}