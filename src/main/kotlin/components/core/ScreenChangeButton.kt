package components.core

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import models.Screen

@Composable
fun ScreenChangeButton(screen: Screen, onScreenChange: (Screen) -> Unit) {
    Button({ onScreenChange(screen) }) {
        Text(screen.name)
    }
}