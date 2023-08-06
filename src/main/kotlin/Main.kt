import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
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
fun App(
    onExit: () -> Unit
) {
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

    fun resetProgress() {
        onImagePathSelected(newPath = "")
        screenManager.onScreenChange(Screen.Main)
    }

    MaterialTheme {
        AnimatedContent(currentScreen) { screen ->
            when(screen) {
                Screen.Main -> HomeScreen(
                    onScreenChange = screenManager::onScreenChange,
                    onExit = onExit
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
                    reset = ::resetProgress
                )
            }
        }
    }
}

fun main() = application {
    Window(
        title = AppConfig.APP_NAME,
        state = rememberWindowState(
            size = DpSize(
                width = 720.dp,
                height = 440.dp
            )
        ),
        onCloseRequest = ::exitApplication
    ) {
        App(
            onExit = ::exitApplication
        )
    }
}
