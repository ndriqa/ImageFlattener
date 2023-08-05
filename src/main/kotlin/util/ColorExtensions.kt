package util

import androidx.compose.ui.graphics.Color
import kotlin.math.pow
import kotlin.math.sqrt

infix fun Color.distanceTo(other: Color): Double {
    return sqrt(
        (red - other.red).toDouble().pow(2.0) +
                (green - other.green).toDouble().pow(2.0) +
                (blue - other.blue).toDouble().pow(2.0)
    )
}