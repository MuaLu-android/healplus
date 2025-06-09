import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class TSizes (
    // Padding and margin sizes
    val sm: Dp = 8.dp,
    val md: Dp = 16.dp,
    val xs: Dp = 4.dp,
    val lg: Dp = 24.dp,
    val xl: Dp = 32.dp,

    // Icons sizes
    val iconXs: Dp = 12.dp,
    val iconSm: Dp = 16.dp,
    val iconMd: Dp = 24.dp,
    val iconLg: Dp = 32.dp,

    // Font sizes
    val fontSm: Dp = 14.dp,
    val fontMd: Dp = 16.dp,
    val fontLg: Dp = 18.dp,

    // AppBar height
    val appBarHeight: Dp = 56.dp,

    // Image sizes
    val imageThumbSize: Dp = 80.dp,

    // Spacing between sections
    val defaultSpace: Dp = 24.dp,
    val spaceBetweenItems: Dp = 16.dp,
    val spaceBetweenSections: Dp = 32.dp,

    // Border radius
    val borderRadiusSm: Dp = 4.dp,
    val borderRadiusMd: Dp = 8.dp,
    val borderRadiusLg: Dp = 12.dp,

    // Product card
    val productImageSize: Dp = 120.dp,
    val productImageRadius: Dp = 16.dp,
    val productItemHeight: Dp = 160.dp,

    // Input field
    val inputFieldRadius: Dp = 12.dp,
    val spaceBetweenInputFields: Dp = 16.dp,

    // Card sizes
    val cardRadiusLg: Dp = 16.dp,
    val cardRadiusMd: Dp = 12.dp,
    val cardRadiusSm: Dp = 10.dp,
    val cardRadiusXs: Dp = 6.dp,
    val cardElevation: Dp = 2.dp,

    // Carousel and loading
    val imageCarouselHeight: Dp = 200.dp,
    val loadingIndicatorSize: Dp = 36.dp,

    // GridView
    val gridViewSpacing: Dp = 16.dp
)