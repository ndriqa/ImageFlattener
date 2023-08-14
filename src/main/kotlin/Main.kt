import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import data.AppConfig
import data.SelectableColors
import models.Screen
import models.ScreenController
import ui.app.PixelatorTheme
import ui.generatedimage.GeneratedImageScreen
import ui.home.HomeScreen
import ui.imagepixelator.ImagePixelatorScreen
import ui.imageselect.ImageSelectScreen
import ui.paletteselector.PaletteSelectorScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
@Preview
fun App(
    onExit: () -> Unit
) {
    val screenManager = remember { ScreenController() }
    val currentScreen by remember { screenManager.currentScreen }
    var selectedPalette by remember { mutableStateOf(emptyList<Color>()) }
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

    fun onPaletteSelected(newColors: List<Color>) {
        selectedPalette = newColors
    }

    PixelatorTheme {
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
                Screen.PaletteSelector -> PaletteSelectorScreen(
                    selectedPalette = selectedPalette,
                    onPaletteSelected = ::onPaletteSelected,
                    onScreenChange = screenManager::onScreenChange
                )
                Screen.Pixelator -> ImagePixelatorScreen(
                    selectedImagePath = selectedImagePath,
                    selectedColors = selectedPalette,
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
        icon = painterResource(resourcePath = AppConfig.APP_ICON_PATH),
        title = AppConfig.APP_NAME,
        state = rememberWindowState(
            size = AppConfig.APP_SIZE
        ),
        onCloseRequest = ::exitApplication
    ) {
        App(onExit = ::exitApplication)
    }
}
