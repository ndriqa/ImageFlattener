package ui.app

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import util.Pixelator

@Composable fun typography() = MaterialTheme.typography
@Composable fun defaultFontFamily() = FontFamily(Font(resource = "raw/quicksand.ttf"))

@Composable fun PixelatorColors() = MaterialTheme.colors
@Composable fun PixelatorShapes() = MaterialTheme.shapes
@Composable fun PixelatorTypography() = MaterialTheme.typography.copy(
    h1 = typography().h1.copy(fontFamily = defaultFontFamily()),
    h2 = typography().h2.copy(fontFamily = defaultFontFamily()),
    h3 = typography().h3.copy(fontFamily = defaultFontFamily()),
    h4 = typography().h4.copy(fontFamily = defaultFontFamily()),
    h5 = typography().h5.copy(fontFamily = defaultFontFamily()),
    h6 = typography().h6.copy(fontFamily = defaultFontFamily()),
    subtitle1 = typography().subtitle1.copy(fontFamily = defaultFontFamily()),
    subtitle2 = typography().subtitle2.copy(fontFamily = defaultFontFamily()),
    body1 = typography().body1.copy(fontFamily = defaultFontFamily()),
    body2 = typography().body2.copy(fontFamily = defaultFontFamily()),
    caption = typography().caption.copy(fontFamily = defaultFontFamily()),
    overline = typography().overline.copy(fontFamily = defaultFontFamily()),
    button = typography().button.copy(fontFamily = defaultFontFamily()),
)

@Composable
fun PixelatorTheme(
    colors: Colors = PixelatorColors(),
    typography: Typography = PixelatorTypography(),
    shapes: Shapes = PixelatorShapes(),
    content: @Composable () -> Unit
) {
    MaterialTheme(colors, typography, shapes, content)
}