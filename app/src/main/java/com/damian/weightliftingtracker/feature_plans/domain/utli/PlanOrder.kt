package com.damian.weightliftingtracker.feature_plans.domain.utli

sealed class PlanOrder(var orderType: OrderType) {
    class Name(orderType: OrderType) : PlanOrder(orderType)
    class Date(orderType: OrderType) : PlanOrder(orderType)

    fun copy(orderType: OrderType): PlanOrder {
        return when (this) {
            is Name -> Name(orderType)
            is Date -> Date(orderType)
        }
    }
}