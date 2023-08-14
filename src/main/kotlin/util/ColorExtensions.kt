package util

import androidx.compose.ui.graphics.Color
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

const val COLOR_BASE_255 = 255

infix fun Color.distanceTo(other: Color): Double {
    return sqrt(
        (red - other.red).toDouble().pow(2.0) +
                (green - other.green).toDouble().pow(2.0) +
                (blue - other.blue).toDouble().pow(2.0)
    )
}

val Color.rgbText: String
    get() {
        val redIntValue = red toPercentageWithBase COLOR_BASE_255
        val greenIntValue = green toPercentageWithBase COLOR_BASE_255
        val blueIntValue = blue toPercentageWithBase COLOR_BASE_255
        val redText = redIntValue.toString().padStart(length = 3, padChar = '0')
        val greenText = greenIntValue.toString().padStart(length = 3, padChar = '0')
        val blueText = blueIntValue.toString().padStart(length = 3, padChar = '0')
        return "($redText, $greenText, $blueText)"
    }

infix fun Float.toPercentageWithBase(base: Int): Int {
    return (this * base)
        .toInt()
        .coerceAtLeast(minimumValue = 0)
        .coerceAtMost(maximumValue = 255)
}

fun Float.hueToColor() = Color.hsl(hue = this, saturation = 1F, lightness = 0.5F)

fun Float.luminanceToColor(): Color {
    val luminance = toPercentageWithBase(base = 255)
    return Color(red = luminance, green = luminance, blue = luminance)
}