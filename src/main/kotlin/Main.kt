import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import components.core.ScreenChangeButton
import models.Screen
import models.ScreenController

@OptIn(ExperimentalAnimationApi::class)
@Composable
@Preview
fun App() {
    val screenManager = remember { ScreenController() }
    val currentScreen by remember { screenManager.currentScreen }

    MaterialTheme {
        AnimatedContent(currentScreen) { screen ->
            when(screen) {
                Screen.Main -> MainScreen(
                    onScreenChange = screenManager::onScreenChange
                )
                Screen.ImageSelect -> GeneratingWeightsScreen(
                    onScreenChange = screenManager::onScreenChange
                )
                Screen.ImageGenerated -> SlowingVideoScreen(
                    onScreenChange = screenManager::onScreenChange
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    onScreenChange: (screen: Screen) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenChangeButton(screen = Screen.ImageSelect, onScreenChange = onScreenChange)
        ScreenChangeButton(screen = Screen.ImageGenerated, onScreenChange = onScreenChange)
    }
}

@Composable
fun GeneratingWeightsScreen(onScreenChange: (screen: Screen) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenChangeButton(screen = Screen.Main, onScreenChange = onScreenChange)
        ScreenChangeButton(screen = Screen.ImageGenerated, onScreenChange = onScreenChange)
    }
}

@Composable
fun SlowingVideoScreen(onScreenChange: (Screen) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenChangeButton(screen = Screen.Main, onScreenChange = onScreenChange)
        ScreenChangeButton(screen = Screen.ImageSelect, onScreenChange = onScreenChange)
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
