package ui.imagepixelator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import components.core.ScreenChangeButton
import models.Screen

@Composable
fun ImagePixelatorScreen(
    selectedImagePath: String,
    onScreenChange: (Screen) -> Unit
) {
    var pixelating by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenChangeButton(screen = Screen.Main, onScreenChange = onScreenChange)
        ScreenChangeButton(screen = Screen.ImageSelect, onScreenChange = onScreenChange)
    }
}