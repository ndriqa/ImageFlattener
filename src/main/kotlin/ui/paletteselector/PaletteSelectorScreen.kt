package ui.paletteselector

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import models.Screen
import util.hueToColor
import util.luminanceToColor
import util.rgbText

@Composable
fun PaletteSelectorScreen(
    selectedPalette: List<Color>,
    onPaletteSelected: (List<Color>) -> Unit,
    onScreenChange: (Screen) -> Unit
) {
    var currentColors by remember { mutableStateOf(selectedPalette) }

    fun addNewColor(newColor: Color) {
        currentColors = currentColors + listOf(newColor)
    }

    fun removeColor(color: Color) {
        val filteredColors = currentColors.filterNot { it == color }
        currentColors = filteredColors
    }

    Scaffold(
        bottomBar = {
            ActionButtons(
                selectedPalette = currentColors,
                onPaletteSelected = onPaletteSelected,
                onScreenChange = onScreenChange,
            )
        }
    ) { contentPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.padding(contentPadding)
        ) {
            var isAddingNewColor by remember { mutableStateOf(value = false) }
            var hue by remember { mutableStateOf(value = 0.5F) }
            var luminance by remember { mutableStateOf(value = 0.5F) }

            fun onHueChanged(newHue: Float) {
                hue = newHue
            }

            fun onLuminanceChanged(newLuminance: Float) {
                luminance = newLuminance
            }

            fun onColorAdd() {
                val newColor = Color.hsl(
                    hue = hue * 360,
                    saturation = 1F,
                    lightness = luminance
                )

                if (currentColors.contains(newColor)) return
                else {
                    isAddingNewColor = !isAddingNewColor
                    addNewColor(newColor = newColor)
                }

            }

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(space = 5.dp),
                contentPadding = PaddingValues(100.dp)
            ) {
                itemsIndexed(
                    items = currentColors,
                    key = { _, c -> c.rgbText }
                ) { _, color ->
                    ColorBox(
                        color = color,
                        onColorRemove = ::removeColor
                    )
                }

                item {
                    AnimatedVisibility(!isAddingNewColor) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth(),
                            border = BorderStroke(
                                width = 1.dp,
                                color = Color.Black),
                            shape = RoundedCornerShape(percent = 50),
                            onClick = { isAddingNewColor = !isAddingNewColor }
                        ) {
                            Text(text = "Add New Color")
                        }
                    }
                }

                item {
                    AnimatedVisibility(isAddingNewColor) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            HueGradient(
                                hueValue = hue,
                                onHueChanged = ::onHueChanged,
                            )

                            LuminanceGradient(
                                luminanceValue = luminance,
                                onLuminanceChanged = ::onLuminanceChanged
                            )

                            Column(
                                modifier = Modifier.fillMaxHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1F)
                                        .aspectRatio(ratio = 1F)
                                        .background(
                                            color = Color.hsl(
                                                hue = hue * 360,
                                                saturation = 1F,
                                                lightness = luminance),
                                            shape = RoundedCornerShape(size = 20.dp))
                                        .border(
                                            width = 1.dp,
                                            color = Color.Black,
                                            shape = RoundedCornerShape(size = 20.dp))
                                        .clip(shape = RoundedCornerShape(size = 20.dp)),
                                )

                                Button(
                                    modifier = Modifier.wrapContentWidth(),
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = Color.Black),
                                    shape = RoundedCornerShape(percent = 50),
                                    onClick = ::onColorAdd
                                ) {
                                    Text(text = "Add Color")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ColorBox(
    color: Color,
    onColorRemove: (Color) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = color,
                shape = RoundedCornerShape(percent = 50))
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(percent = 50))
            .clip(shape = RoundedCornerShape(percent = 50))
    ) {
        Text(
            text = color.rgbText,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(resource = "raw/quicksand.ttf")),
            modifier = Modifier
                .weight(1F)
                .padding(vertical = 10.dp)
        )

        Button(
            onClick = { onColorRemove(color) },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White),
            contentPadding = PaddingValues(all = 0.dp),
            border = BorderStroke(
                width = 1.dp,
                color = Color.Black),
            shape = CircleShape,
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete icon",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun RowScope.HueGradient(
    hueValue: Float,
    onHueChanged: (Float) -> Unit,
) {
    val gradientColors = listOf(
        Color.Red,
        Color.Yellow,
        Color.Green,
        Color.Cyan,
        Color.Blue,
        Color.Magenta,
        Color.Red // Red again to complete the loop
    )

    val brush = Brush.horizontalGradient(colors = gradientColors)

    Column(
        modifier = Modifier
            .weight(1F)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(size = 20.dp))
            .clip(shape = RoundedCornerShape(size = 20.dp)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(all = 15.dp)
                .clip(shape = RoundedCornerShape(size = 20.dp))
                .background(brush),
            contentAlignment = Alignment.Center
        ) {  }

        Slider(
            value = hueValue,
            onValueChange = onHueChanged,
            colors = SliderDefaults.colors(
                thumbColor = hueValue.times(360).hueToColor()
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        )
    }
}

@Composable
fun RowScope.LuminanceGradient(
    luminanceValue: Float,
    onLuminanceChanged: (Float) -> Unit,
) {
    val gradientColors = listOf(Color.White, Color.Black,)
    val brush = Brush.verticalGradient(colors = gradientColors)

    Row(
        modifier = Modifier
            .width(110.dp)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(size = 20.dp))
            .clip(shape = RoundedCornerShape(size = 20.dp)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(60.dp)
                .padding(all = 15.dp)
                .clip(shape = RoundedCornerShape(size = 20.dp))
                .background(brush),
            contentAlignment = Alignment.Center
        ) {  }

        Slider(
            value = luminanceValue,
            onValueChange = onLuminanceChanged,
            colors = SliderDefaults.colors(
                thumbColor = luminanceValue.luminanceToColor()
            ),
            modifier = Modifier
                .graphicsLayer {
                    rotationZ = 270f
                    transformOrigin = TransformOrigin(0f, 0f)
                }
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(
                        Constraints(
                            minWidth = constraints.minHeight,
                            maxWidth = constraints.maxHeight,
                            minHeight = constraints.minWidth,
                            maxHeight = constraints.maxWidth,
                        )
                    )
                    layout(placeable.height, placeable.width) {
                        placeable.place(-placeable.width, 0)
                    }
                }
                .fillMaxHeight()
                .padding(horizontal = 15.dp)
        )
    }
}

@Composable
fun ActionButtons(
    selectedPalette: List<Color>,
    onPaletteSelected: (List<Color>) -> Unit,
    onScreenChange: (Screen) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        AnimatedVisibility(selectedPalette.isNotEmpty()) {
            Button(
                onClick = {
                    onPaletteSelected(selectedPalette)
                    onScreenChange(Screen.Pixelator)
                }
            ) {
                Text(
                    text = "Proceed"
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewPaletteSelectorScreen() {

    PaletteSelectorScreen(
        selectedPalette = listOf(Color.Red, Color.Blue),
        onPaletteSelected = { _ -> } ,
        onScreenChange = { _ -> }
    )
}
