package ui.imagepixelator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import components.core.ScreenChangeButton
import data.SelectableColors
import models.Screen
import util.Pixelator
import kotlin.reflect.KFunction1

@Composable
fun ImagePixelatorScreen(
    selectedImagePath: String,
    onGeneratedImagePathCreated: (String) -> Unit,
    onScreenChange: (Screen) -> Unit
) {
    val pixelator = remember { Pixelator(
        imagePath = selectedImagePath,
        pixelatorConfig = Pixelator.Configuration(
            pixelatingColors = SelectableColors.MapColors,
            downsampleType = Pixelator.DownsampleType.AVERAGE
        )
    ) }
    val pixelating by remember { pixelator.pixelating }
    val progress by remember { pixelator.progress }
    val generatedImagePath by remember { pixelator.generatedPixelatedFilePath }
    val error by remember { pixelator.error }

    LaunchedEffect(generatedImagePath) {
        if (generatedImagePath.isNotBlank()) {
            onGeneratedImagePathCreated(generatedImagePath)
            onScreenChange(Screen.GeneratedImage)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = pixelating) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                Text(
                    text = "Progress:"
                )
                Text(
                    text = "$progress"
                )
            }
        }
        AnimatedVisibility(visible = !pixelating && generatedImagePath.isBlank()) {
            Button(onClick = pixelator::startPixelating) {
                Text(text = "Start pixelating")
            }
        }
        AnimatedVisibility(visible = false) {

        }
        ScreenChangeButton(screen = Screen.Main, onScreenChange = onScreenChange)
    }
}