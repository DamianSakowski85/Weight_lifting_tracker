package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case

class IncreasePauseValueUseCase {
    @Throws(InvalidPauseFormatException::class)
    operator fun invoke(value: String): String {
        if (value.isNotBlank()) {
            return try {
                val intValue = value.toInt()
                (intValue + 5).toString()
            } catch (exception: Exception) {
                throw InvalidPauseFormatException(exception.message.toString())
            }
        }
        return value
    }
}