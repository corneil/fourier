package fourier.algorithms

import fourier.models.Coordinate
import fourier.models.Phasor
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI

// Override + operator for Phasor.
inline operator fun Phasor.plus(value: Phasor): Phasor = this.add(value)
inline operator fun Phasor.times(value: Phasor):Phasor = this.multiply(value)
// Use object for class with only static methods
object FourierAlgorithms {
    private fun convertToPhasor(cartesian: List<Coordinate>): List<Phasor> {
        val interval = if (cartesian.size > 15000) 4 else 3
        val phasors = mutableListOf<Phasor>()
        var n = 0
        while (n < cartesian.size) {
            val phasor = Phasor(cartesian[n], 0.0)
            phasors.add(n / interval, phasor)
            n += interval
        }
        return phasors
    }

    @JvmStatic // Tell Kotlin to generate Java compatible signature
    fun discreteFourierTransform(imageCoordinates: List<Coordinate>): List<Phasor> {
        val input = convertToPhasor(imageCoordinates)
        val output = mutableListOf<Phasor>()
        val N = input.size
        for (k in 0 until N) {
            val sum = input.mapIndexed { n, p ->
                val theta = 2 * PI * k * n / N
                p * Phasor(Coordinate(cos(theta), -1 * sin(theta)), 0.0)// last expression is result
            }.fold(Phasor(Coordinate(0.0, 0.0), k.toDouble())) { acc, ph -> acc + ph }

            sum.terminal = Coordinate(sum.terminal.x / N, sum.terminal.y / N)
            output.add(k, sum)
        }
        return output.sortedWith { first: Phasor, second: Phasor ->
            second.magnitude.compareTo(first.magnitude)
        }
    }
}