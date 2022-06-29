package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case

class IncreaseRepsValueUseCase {
    @Throws(InvalidRepsFormatException::class)
    operator fun invoke(value: String): String {
        if (value.isNotBlank()) {
            return try {
                val intValue = value.toInt()
                (intValue + 1).toString()
            } catch (exception: Exception) {
                throw InvalidRepsFormatException(exception.message.toString())
            }
        }
        return value
    }
}