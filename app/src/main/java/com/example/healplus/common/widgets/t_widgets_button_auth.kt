import android.support.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WidgetButtonAuth(
    authValue: Boolean,
    @StringRes title: Int,
    onPress: () -> Unit
) {
    Button(
        onClick = {onPress()},
        enabled = authValue,
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .height(61.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = stringResource(id = title),
            color = Color.White,
            fontSize = 22.sp
        )
    }
}