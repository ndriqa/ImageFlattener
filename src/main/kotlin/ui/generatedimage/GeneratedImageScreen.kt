package ui.generatedimage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import models.Screen
import java.io.File

@Composable
fun GeneratedImageScreen(
    generatedImagePath: String,
    reset: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageFile = try { File(generatedImagePath) } catch (_: Exception) { null }

        AnimatedVisibility(visible = imageFile?.isFile == true) {
            imageFile ?: return@AnimatedVisibility
            Column(
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Generated image path:",
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = generatedImagePath
                )
                Image(
                    bitmap = loadImageBitmap(
                        inputStream = imageFile.inputStream()
                    ),
                    contentDescription = "Generated Image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.height(150.dp)
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Button(onClick = reset) { Text(text = "Home") }
        }
    }
}
