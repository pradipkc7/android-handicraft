package com.example.blogapp.view.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Light Theme Colors
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF8B4513), // Saddle Brown
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDEB887), // Burlywood
    onPrimaryContainer = Color.Black,
    secondary = Color(0xFFD2B48C), // Tan
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFF5F5DC), // Beige
    onSecondaryContainer = Color.Black,
    tertiary = Color(0xFFCD853F), // Peru
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFF4A460), // Sandy Brown
    onTertiaryContainer = Color.Black,
    error = Color(0xFFB91C1C), // Red
    onError = Color.White,
    errorContainer = Color(0xFFFEE2E2),
    onErrorContainer = Color(0xFF7F1D1D),
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFF5F5F5),
    onSurfaceVariant = Color(0xFF525252),
    outline = Color(0xFFD1D5DB),
    outlineVariant = Color(0xFFE5E7EB),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFF171717),
    inverseOnSurface = Color(0xFFF3F4F6),
    inversePrimary = Color(0xFFDEB887),
    surfaceDim = Color(0xFFF9FAFB),
    surfaceBright = Color(0xFFFFFFFF),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF3F4F6),
    surfaceContainer = Color(0xFFE5E7EB),
    surfaceContainerHigh = Color(0xFFD1D5DB),
    surfaceContainerHighest = Color(0xFF9CA3AF)
)

// Dark Theme Colors
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFDEB887), // Burlywood
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF8B4513), // Saddle Brown
    onPrimaryContainer = Color.White,
    secondary = Color(0xFFF5F5DC), // Beige
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFD2B48C), // Tan
    onSecondaryContainer = Color.Black,
    tertiary = Color(0xFFF4A460), // Sandy Brown
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFFCD853F), // Peru
    onTertiaryContainer = Color.White,
    error = Color(0xFFFCA5A5),
    onError = Color.Black,
    errorContainer = Color(0xFF7F1D1D),
    onErrorContainer = Color(0xFFFEE2E2),
    background = Color(0xFF0F0F0F),
    onBackground = Color.White,
    surface = Color(0xFF171717),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF262626),
    onSurfaceVariant = Color(0xFFD1D5DB),
    outline = Color(0xFF525252),
    outlineVariant = Color(0xFF404040),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFFF3F4F6),
    inverseOnSurface = Color(0xFF171717),
    inversePrimary = Color(0xFF8B4513),
    surfaceDim = Color(0xFF0F0F0F),
    surfaceBright = Color(0xFF262626),
    surfaceContainerLowest = Color(0xFF0A0A0A),
    surfaceContainerLow = Color(0xFF171717),
    surfaceContainer = Color(0xFF1F1F1F),
    surfaceContainerHigh = Color(0xFF262626),
    surfaceContainerHighest = Color(0xFF404040)
)

// Custom Color Palette
object CraftedColors {
    val Primary = Color(0xFF8B4513) // Saddle Brown
    val PrimaryLight = Color(0xFFDEB887) // Burlywood
    val Secondary = Color(0xFFD2B48C) // Tan
    val Accent = Color(0xFFF5F5DC) // Beige
    val Success = Color(0xFF22C55E) // Green
    val Warning = Color(0xFFF59E0B) // Amber
    val Error = Color(0xFFEF4444) // Red
    val Info = Color(0xFF3B82F6) // Blue
    
    // Text Colors
    val TextPrimary = Color(0xFF1F2937)
    val TextSecondary = Color(0xFF6B7280)
    val TextTertiary = Color(0xFF9CA3AF)
    
    // Background Colors
    val BackgroundPrimary = Color.White
    val BackgroundSecondary = Color(0xFFF9FAFB)
    val BackgroundTertiary = Color(0xFFF3F4F6)
    
    // Border Colors
    val BorderLight = Color(0xFFE5E7EB)
    val BorderMedium = Color(0xFFD1D5DB)
    val BorderDark = Color(0xFF9CA3AF)
    
    // Status Colors
    val StatusOnline = Color(0xFF22C55E)
    val StatusOffline = Color(0xFF6B7280)
    val StatusBusy = Color(0xFFEF4444)
    
    // Rating Colors
    val RatingStar = Color(0xFFFFD700)
    val RatingStarEmpty = Color(0xFFD1D5DB)
    
    // Discount Colors
    val DiscountBadge = Color(0xFFEF4444)
    val DiscountText = Color.White
    
    // Shadow Colors
    val ShadowLight = Color(0xFF000000).copy(alpha = 0.05f)
    val ShadowMedium = Color(0xFF000000).copy(alpha = 0.1f)
    val ShadowDark = Color(0xFF000000).copy(alpha = 0.2f)
}

// Typography Scale
object CraftedTypography {
    val DisplayLarge = androidx.compose.ui.text.TextStyle(
        fontSize = 57.sp,
        lineHeight = 64.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Normal
    )
    
    val DisplayMedium = androidx.compose.ui.text.TextStyle(
        fontSize =45.sp,
        lineHeight = 52.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Normal
    )
    
    val DisplaySmall = androidx.compose.ui.text.TextStyle(
        fontSize =36.sp,
        lineHeight = 44.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Normal
    )
    
    val HeadlineLarge = androidx.compose.ui.text.TextStyle(
        fontSize = 32.sp,
        lineHeight = 40.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
    )
    
    val HeadlineMedium = androidx.compose.ui.text.TextStyle(
        fontSize = 28.sp,
        lineHeight = 36.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
    )
    
    val HeadlineSmall = androidx.compose.ui.text.TextStyle(
        fontSize =24.sp,
        lineHeight = 32.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
    )
    
    val TitleLarge = androidx.compose.ui.text.TextStyle(
        fontSize = 22.sp,
        lineHeight = 28.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
    )
    
    val TitleMedium = androidx.compose.ui.text.TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
    )
    
    val TitleSmall = androidx.compose.ui.text.TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
    )
    
    val BodyLarge = androidx.compose.ui.text.TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Normal
    )
    
    val BodyMedium = androidx.compose.ui.text.TextStyle(
        fontSize =14.sp,
        lineHeight =20.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Normal
    )
    
    val BodySmall = androidx.compose.ui.text.TextStyle(
        fontSize = 12.sp,
        lineHeight =16.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Normal
    )
    
    val LabelLarge = androidx.compose.ui.text.TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
    )
    
    val LabelMedium = androidx.compose.ui.text.TextStyle(
        fontSize =12.sp,
        lineHeight =16.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
    )
    
    val LabelSmall = androidx.compose.ui.text.TextStyle(
        fontSize =11.sp,
        lineHeight = 16.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
    )
}

// Shape Definitions
object CraftedShapes {
    val ExtraSmall = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
    val Small = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
    val Medium = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
    val Large = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
    val ExtraLarge = androidx.compose.foundation.shape.RoundedCornerShape(24.dp)
    val Circular = androidx.compose.foundation.shape.CircleShape
}

// Spacing Scale
object CraftedSpacing {
    val ExtraSmall = 4.dp
    val Small = 8.dp
    val Medium = 16.dp
    val Large = 24.dp
    val ExtraLarge = 32.dp
    val Huge = 48.dp
}

@Composable
fun CraftedTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
} 