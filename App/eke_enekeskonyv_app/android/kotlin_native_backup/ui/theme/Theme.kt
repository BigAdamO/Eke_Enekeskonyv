package com.nagyadam.eke_enekeskonyv_app.ui.theme

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.nagyadam.eke_enekeskonyv_app.darkMode

//import com.example.eke_enekeskonyv_app.darkMode

private val DarkColorScheme = darkColorScheme(

    secondary = BlueGrey80,
    tertiary = app_light_blue,
    background = Gary3,
    surface = app_gray,
    onPrimary = app_dark,

    //link
    primary = app_blue,

    //buttons
    secondaryContainer = app_blue,
    onTertiary = Color.White,

    //top bar
    primaryContainer = app_dark_blue,
    onSecondary = Color.White,
)

private val LightColorScheme = lightColorScheme(


    secondary = app_dark_blue,
    tertiary = app_dark_blue,

    background = Color.White,
    surface = app_light_blue,
    onPrimary = app_dark,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),

    //link
    primary = app_blue,

    //Button
    secondaryContainer = app_blue,
    onTertiary = Color.White,


    //top bar
    onSecondary = Color.White,
    primaryContainer = app_dark_blue,


)

@Composable
fun EkeEnekeskonyvTheme(
    //darkTheme: Boolean = isSystemInDarkTheme(),
    darkTheme: Boolean = darkMode,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            setUpEdgeToEdge(view, darkTheme)
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
//        shapes = Shapes,
        typography = Typography,
        content = content
    )
}

/**
 * Sets up edge-to-edge for the window of this [view]. The system icon colors are set to either
 * light or dark depending on whether the [darkTheme] is enabled or not.
 */
private fun setUpEdgeToEdge(view: View, darkTheme: Boolean) {
    val window = (view.context as Activity).window
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.statusBarColor = Color.Transparent.toArgb()
    val navigationBarColor = when {
        Build.VERSION.SDK_INT >= 29 -> Color.Transparent.toArgb()
        // Min sdk version for this app is 24, this block is for SDK versions 24 and 25
        else -> Color(0xFF, 0xFF, 0xFF, 0x63).toArgb()
    }
    window.navigationBarColor = navigationBarColor
    val controller = WindowCompat.getInsetsController(window, view)
    controller.isAppearanceLightStatusBars = !darkTheme
    controller.isAppearanceLightNavigationBars = !darkTheme


}