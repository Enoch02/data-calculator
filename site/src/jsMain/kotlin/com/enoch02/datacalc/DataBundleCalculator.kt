package com.enoch02.datacalc

import kotlin.math.round

object DataBundleCalculator {
    /**
     * Calculates how much is being paid for each GB of data.
     * @param price Total price of the data bundle
     * @param dataAmount Total amount of data in GB
     */
    fun calculateValuePerGB(price: Double, dataAmount: Double): String {
        val valuePerGB = price / dataAmount

        return valuePerGB.roundToTwoDecimalPlaces().toString()
    }

    /**
     * Calculates the daily cost of the data bundle.
     * @param price Total price of the data bundle
     * @param validityDays Validity period in days
     */
    fun calculateValuePerDay(price: Double, validityDays: Int): String {
        val valuePerDay = price / validityDays

        return valuePerDay.roundToTwoDecimalPlaces().toString()
    }

    /**
     * Calculates the cost efficiency metric (cost per GB per day).
     * @param price Total price of the data bundle
     * @param dataAmount Total amount of data in GB
     * @param validityDays Validity period in days
     */
    fun calculateValuePerGBPerDay(price: Double, dataAmount: Double, validityDays: Int): String {
        val valuePerGBPerDay = price / (dataAmount * validityDays)

        return valuePerGBPerDay.roundToTwoDecimalPlaces().toString()
    }

    /**
     * Calculates how long each GB should last if used evenly.
     * @param dataAmount Total amount of data in GB
     * @param validityDays Validity period in days
     */
    fun calculateDaysPerGB(dataAmount: Double, validityDays: Int): String {
        val daysPerGB = validityDays / dataAmount

        return daysPerGB.roundToTwoDecimalPlaces().toString()
    }

    /**
     * Calculates the average daily data allowance.
     * @param dataAmount Total amount of data in GB
     * @param validityDays Validity period in days
     */
    fun calculateGBPerDay(dataAmount: Double, validityDays: Int): String {
        val gbPerDay = dataAmount / validityDays

        return gbPerDay.roundToTwoDecimalPlaces().toString()
    }

    /**
     * Calculates all metrics for a data bundle and returns them as a map.
     * @param price Total price of the data bundle
     * @param dataAmount Total amount of data in GB
     * @param validityDays Validity period in days
     * @return Map containing all calculated metrics
     */
    fun calculateAllMetrics(price: Double, dataAmount: Double, validityDays: Int): Map<String, String> {
        return mapOf(
            "valuePerGB" to calculateValuePerGB(price, dataAmount),
            "valuePerDay" to calculateValuePerDay(price, validityDays),
            "valuePerGBPerDay" to calculateValuePerGBPerDay(price, dataAmount, validityDays),
            "daysPerGB" to calculateDaysPerGB(dataAmount, validityDays),
            "gbPerDay" to calculateGBPerDay(dataAmount, validityDays)
        )
    }
}

fun Double.roundToTwoDecimalPlaces(): Double {
    return round(this * 100) / 100
}