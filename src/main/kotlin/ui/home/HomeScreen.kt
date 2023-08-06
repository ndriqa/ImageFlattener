package ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import components.core.ScreenChangeButton
import models.Screen

@Composable
fun HomeScreen(
    onScreenChange: (screen: Screen) -> Unit,
    onExit: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenChangeButton(screen = Screen.ImageSelect, onScreenChange = onScreenChange)
        Button(onClick = onExit) { Text(text = "Exit") }
    }
}