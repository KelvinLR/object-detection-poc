
package com.example.object_detection_poc.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF81C784),
    onPrimary = Color(0xFF003909),

    secondary = Color(0xFFA5D6A7),
    onSecondary = Color(0xFF19371D),

    tertiary = Color(0xFFB9CC8F),
    onTertiary = Color(0xFF293514),

    background = Color(0xFF101510),
    onBackground = Color(0xFFE0E5DC),

    surface = Color(0xFF101510),
    onSurface = Color(0xFFE0E5DC),

    surfaceVariant = Color(0xFF414941),
    onSurfaceVariant = Color(0xFFC1C9BD),

    outline = Color(0xFF899388)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2E7D32),
    onPrimary = Color.White,

    primaryContainer = Color(0xFFC8E6C9),
    onPrimaryContainer = Color(0xFF002106),

    secondary = Color(0xFF558B2F),
    onSecondary = Color.White,

    secondaryContainer = Color(0xFFDCECCB),
    onSecondaryContainer = Color(0xFF172D0A),

    tertiary = Color(0xFF558B6E),
    onTertiary = Color.White,

    background = Color(0xFFF7FAF5),
    onBackground = Color(0xFF191D18),

    surface = Color(0xFFF7FAF5),
    onSurface = Color(0xFF191D18),

    surfaceVariant = Color(0xFFE1E8DE),
    onSurfaceVariant = Color(0xFF424940),

    outline = Color(0xFF72796F)
)

@Composable
fun ObjectDetectionPocTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
