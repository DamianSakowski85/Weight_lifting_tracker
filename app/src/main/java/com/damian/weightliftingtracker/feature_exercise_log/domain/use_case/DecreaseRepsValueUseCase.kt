package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case

class DecreaseRepsValueUseCase {
    @Throws(InvalidPauseFormatException::class)
    operator fun invoke(value: String): String {
        if (value.isNotBlank()) {
            try {
                val intValue = value.toInt()
                if (intValue != 0) {
                    return (intValue - 1).toString()
                }
            } catch (exception: Exception) {
                throw InvalidRepsFormatException(exception.message.toString())
            }
        }
        return value
    }
}