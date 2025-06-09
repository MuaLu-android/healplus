
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun TextSkipPage(
    @StringRes title: Int,
    onPressed: () -> Unit,
    icon: ImageVector? = null,
) {
    Row(
        modifier = Modifier
            .clickable { onPressed() }
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(title))
        if (icon != null) {
            Icon(
                imageVector = icon ?: return@Row,
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

    }
}
