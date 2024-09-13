package com.github.lowkeylab.ptoscheduler.user

import java.time.LocalDate
import kotlin.random.Random

class User(
    val name: String,
    val maxPtoDays: Int,
    val ptoDays: MutableSet<LocalDate> = mutableSetOf(),
    val id: Long? = null,
) {
    private val usedPtoDays: Int
        get() = ptoDays.size

    val ptoDaysLeft
        get() = maxPtoDays - usedPtoDays

    init {
        validateState()
    }

    fun usePtoDay(date: LocalDate) {
        validateState()
        require(usedPtoDays < maxPtoDays) { "No more PTO days left" }

        ptoDays.add(date)
    }

    fun randomizePtoDaysAfter(date: LocalDate) {
        validateState()

        while (usedPtoDays < maxPtoDays) {
            usePtoDay(LocalDate.ofYearDay(date.year, Random.nextInt(date.dayOfYear + 1, date.lengthOfYear())))
        }
    }

    private fun validateState() {
        require(maxPtoDays > 0) { "Max PTO days must be greater than 0" }
        require(usedPtoDays >= 0) { "Used PTO days must be greater than or equal to 0" }
        require(usedPtoDays <= maxPtoDays) { "Used PTO days must be less than or equal to max PTO days" }
    }
}
