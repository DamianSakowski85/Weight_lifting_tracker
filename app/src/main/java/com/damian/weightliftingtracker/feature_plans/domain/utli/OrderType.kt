package com.damian.weightliftingtracker.feature_plans.domain.utli

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}