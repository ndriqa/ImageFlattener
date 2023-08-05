import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.AppConfig
import models.Screen
import models.ScreenController
import ui.generatedimage.GeneratedImageScreen
import ui.home.HomeScreen
import ui.imagepixelator.ImagePixelatorScreen
import ui.imageselect.ImageSelectScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
@Preview
fun App() {
    val screenManager = remember { ScreenController() }
    val currentScreen by remember { screenManager.currentScreen }
    var selectedImagePath by remember { mutableStateOf("") }
    var generatedImagePath by remember { mutableStateOf("") }

    fun onImagePathSelected(newPath: String) {
        selectedImagePath = newPath
    }

    fun onGeneratedImagePathCreated(newPath: String) {
        generatedImagePath = newPath
    }

    MaterialTheme {
        AnimatedContent(currentScreen) { screen ->
            when(screen) {
                Screen.Main -> HomeScreen(
                    onScreenChange = screenManager::onScreenChange
                )
                Screen.ImageSelect -> ImageSelectScreen(
                    selectedImagePath = selectedImagePath,
                    onImagePathSelected = ::onImagePathSelected,
                    onScreenChange = screenManager::onScreenChange
                )
                Screen.Pixelator -> ImagePixelatorScreen(
                    selectedImagePath = selectedImagePath,
                    onGeneratedImagePathCreated = ::onGeneratedImagePathCreated,
                    onScreenChange = screenManager::onScreenChange
                )
                Screen.GeneratedImage -> GeneratedImageScreen(
                    generatedImagePath = generatedImagePath,
                    onScreenChange = screenManager::onScreenChange
                )
            }
        }
    }
}

fun main() = application {
    Window(
        title = AppConfig.APP_NAME,
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}
