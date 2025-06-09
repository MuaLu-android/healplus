
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import com.example.core.viewmodel.authviewmodel.AuthSate
import com.example.healplus.acitivity.AdminActivity
import com.example.healplus.acitivity.MainActivity

@Composable
fun AuthCheck(
    authSate: State<AuthSate?>,
) {
    val context = LocalContext.current
    LaunchedEffect(authSate.value) {
        if (authSate.value is AuthSate.Admin) {
            val intent = Intent(context, AdminActivity::class.java)
            context.startActivity(intent)
        }
        if (authSate.value is AuthSate.User) {
            val intent1 = Intent(context, MainActivity::class.java)
            context.startActivity(intent1)
        }

    }
}