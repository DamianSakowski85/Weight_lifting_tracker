package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case


class IncreaseWeightValueUseCase {

    @Throws(InvalidWeightFormatException::class)
    operator fun invoke(value: String): String {
        if (value.isNotBlank()) {
            return try {
                val doubleValue = value.toDouble()
                (doubleValue + 0.5).toString()
            } catch (exception: Exception) {
                throw InvalidWeightFormatException(exception.message.toString())
            }
        }
        return value
    }
}