package montanez.alexander.spotme.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.palette.graphics.Palette
import montanez.alexander.spotme.R
import kotlin.math.PI
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun getPaletteFromBitmap(
    bitmap: Bitmap = BitmapFactory
        .decodeResource(LocalContext.current.resources, R.drawable.new_sweney_cover)
) = Palette.from(bitmap).generate()

fun Palette.Swatch.rgbAsColor(): Color = Color(rgb)

fun Palette.Swatch.titleRGBAsColor(): Color = Color(titleTextColor)

fun Modifier.gradientBackground(colors: List<Color>, angle: Float) = this.then(
    Modifier.drawBehind {
        // Setting the angle in radians
        val angleRad = angle / 180f * PI

        // Fractional x
        val x = kotlin.math.cos(angleRad).toFloat()

        // Fractional y
        val y = kotlin.math.sin(angleRad).toFloat()

        // Set the Radius and offset as shown below
        val radius = sqrt(size.width.pow(2) + size.height.pow(2)) / 2f
        val offset = center + Offset(x * radius, y * radius)

        // Setting the exact offset
        val exactOffset = Offset(
            x = min(offset.x.coerceAtLeast(0f), size.width),
            y = size.height - min(offset.y.coerceAtLeast(0f), size.height)
        )

        // Draw a rectangle with the above values
        drawRect(
            brush = Brush.linearGradient(
                colors = colors,
                start = Offset(size.width, size.height) - exactOffset,
                end = exactOffset
            ),
            size = size
        )
    }
)
