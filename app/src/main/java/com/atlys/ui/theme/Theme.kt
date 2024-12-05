package com.atlys.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.atlyssahil.ui.theme.Pink40
import com.atlyssahil.ui.theme.Pink80
import com.atlyssahil.ui.theme.Purple80
import com.atlyssahil.ui.theme.PurpleGrey40
import com.atlyssahil.ui.theme.PurpleGrey80
import com.atlyssahil.ui.theme.Typography
import com.atlyssahil.ui.theme.WhiteForest
import com.atlyssahil.ui.theme.WhitePrimary

// dark colors - not using for now
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = WhitePrimary,
    onPrimary = WhiteForest,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun AtlysTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}