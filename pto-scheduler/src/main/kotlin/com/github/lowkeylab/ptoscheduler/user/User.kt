package com.github.lowkeylab.ptoscheduler.user

import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import kotlin.random.Random

@Entity
@Table(name = "users")
class User(
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val maxPtoDays: Int,
    @ElementCollection(fetch = FetchType.EAGER)
    val ptoDays: MutableSet<LocalDate> = mutableSetOf(),
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
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
