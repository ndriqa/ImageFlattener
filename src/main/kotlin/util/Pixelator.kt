package util

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.loadImageBitmap
import kotlinx.coroutines.*
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class Pixelator(
    private val imagePath: String,
    private val pixelatorConfig: Configuration
) {
    var imageFile: File? = null

    val progress = mutableStateOf(0)
    val pixelating = mutableStateOf(false)
    val error = mutableStateOf<Exception?>(null)
    var generatedPixelatedFilePath = mutableStateOf("")

    fun startPixelating() {
        CoroutineScope(Dispatchers.IO).launch {
            pixelating.value = true

            val bitmapInitializationJob = async {
                imageFile = try {
                    File(imagePath)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
                (imageFile != null) && (imageFile?.exists() == true)
            }

            val pixelatingImageJob = async {
                bitmapInitializationJob.await()
                pixelateImage()
            }

            val jobs = awaitAll(
                bitmapInitializationJob,
                pixelatingImageJob
            )

            if (jobs.all { it }) {
                pixelating.value = false
            }
        }
    }

    private suspend fun pixelateImage(): Boolean {
        val imageFile = imageFile ?: return false

        return withContext(Dispatchers.IO) {
            val image = loadImageBitmap(imageFile.inputStream())
            val imagePixels = image.toPixelMap()
            val width = image.width
            val height = image.height
            val imageSize = width * height

            val pixelatedBitmap = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)

            for (y in 0 until height) {
                for (x in 0 until width) {
                    val currentPixelNumber = y * width + x
                    val currentPixel = imagePixels[x, y]
                    updateProgress(currentPixelNumber, imageSize)
                    val newColor = pixelatorConfig.pixelatingColors.minBy { currentPixel distanceTo it }
                    pixelatedBitmap.setRGB(x, y, newColor.toArgb())
                }
            }

            val outputFile = File(imageFile.parent,"${imageFile.nameWithoutExtension}-pixelated.png")
            ImageIO.write(pixelatedBitmap, "png", outputFile)
            generatedPixelatedFilePath.value = outputFile.absolutePath
            generatedPixelatedFilePath.value.isBlank().not()
        }
    }

    private fun updateProgress(currentPixelNumber: Int, imageSize: Int) {
        progress.value = ((currentPixelNumber.toFloat() / imageSize.toFloat()) * 100).toInt()
    }

    data class Configuration(
        val pixelatingColors: List<Color>,
        val downsampleType: DownsampleType = DownsampleType.AVERAGE,
    )

    enum class DownsampleType {
        AVERAGE, MODE
    }
}