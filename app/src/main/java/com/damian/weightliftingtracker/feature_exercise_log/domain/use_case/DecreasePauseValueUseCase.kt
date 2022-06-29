package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case

class DecreasePauseValueUseCase {
    @Throws(InvalidPauseFormatException::class)
    operator fun invoke(value: String): String {
        if (value.isNotBlank()) {
            try {
                val intValue = value.toInt()
                if (intValue > 4) {
                    return (intValue - 5).toString()
                }
            } catch (exception: Exception) {
                throw InvalidPauseFormatException(exception.message.toString())
            }
        }
        return value
    }
}