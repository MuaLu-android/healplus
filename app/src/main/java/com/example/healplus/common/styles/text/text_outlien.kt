import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.healplus.R

@Composable
fun tOutLindTextField(@StringRes text: Int, title: String, tShowIcon: Boolean? = false): String {
    var tileText = title
    var tisPasswordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = tileText,
        onValueChange = { tileText = it },
        label = { Text(stringResource(id = text)) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        singleLine = true,
        visualTransformation = if (tisPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            if (tShowIcon == true) {
                IconButton(onClick = { tisPasswordVisible = !tisPasswordVisible }) {
                    Icon(
                        painter = if (tisPasswordVisible) painterResource(R.drawable.visibility_24px) else painterResource(
                            R.drawable.visibility_off_24px
                        ),
                        contentDescription = "Toggle Password Visibility"
                    )
                }
            }
        }
    )
    return tileText
}