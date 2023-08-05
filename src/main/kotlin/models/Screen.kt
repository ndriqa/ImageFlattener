package models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

enum class Screen {
    Main, ImageSelect, ImageGenerated
}

class ScreenController {
    var currentScreen = mutableStateOf(Screen.Main)

    fun onScreenChange(screen: Screen) {
        currentScreen.value = screen
    }
}