package com.damian.weightliftingtracker.feature_exercise_log.domain.use_case

class DecreaseWeightValueUseCase {

    @Throws(InvalidWeightFormatException::class)
    operator fun invoke(value: String): String {
        if (value.isNotBlank()) {
            try {
                val doubleValue = value.toDouble()
                if (doubleValue != 0.0) {
                    return (doubleValue - 0.5).toString()
                }
            } catch (exception: Exception) {
                throw InvalidWeightFormatException(exception.message.toString())
            }
        }
        return value
    }
}