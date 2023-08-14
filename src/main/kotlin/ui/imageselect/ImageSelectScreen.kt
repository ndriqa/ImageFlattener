package ui.imageselect

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
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
import java.awt.FileDialog
import java.awt.Frame
import java.io.File

@Composable
fun ImageSelectScreen(
    selectedImagePath: String,
    onImagePathSelected: (String) -> Unit,
    onScreenChange: (screen: Screen) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageFile = try { File(selectedImagePath) } catch (_: Exception) { null }

        AnimatedVisibility(visible = imageFile?.isFile == true) {
            imageFile ?: return@AnimatedVisibility
            Column(
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Selected image path:",
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = selectedImagePath
                )
                Image(
                    bitmap = loadImageBitmap(
                        inputStream = imageFile.inputStream()
                    ),
                    contentDescription = "Selected Image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.height(150.dp)
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Button(
                onClick = {
                    openFileSelectDialog(
                        onImagePathSelected = onImagePathSelected
                    )
                },
            ) {
                Text(
                    text = "Select Image file"
                )
            }
            AnimatedVisibility(
                selectedImagePath.isNotBlank()
            ) {
                Button(
                    onClick = { onScreenChange(Screen.PaletteSelector) }
                ) {
                    Text(
                        text = "Proceed to palette selection"
                    )
                }
            }
        }
    }
}

fun openFileSelectDialog(
    onImagePathSelected: (String) -> Unit
) {
    val fileDialog = FileDialog(null as Frame?)
    fileDialog.mode = FileDialog.LOAD
    fileDialog.isVisible = true

    fileDialog.files.firstOrNull()?.absolutePath?.toString()?.let {
        onImagePathSelected(it)
    }
}
