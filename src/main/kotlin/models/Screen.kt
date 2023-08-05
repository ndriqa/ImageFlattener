package models

import androidx.compose.runtime.mutableStateOf

enum class Screen {
    Main, ImageSelect, Pixelator, GeneratedImage
}

class ScreenController {
    var currentScreen = mutableStateOf(Screen.Main)

    fun onScreenChange(screen: Screen) {
        currentScreen.value = screen
    }
}