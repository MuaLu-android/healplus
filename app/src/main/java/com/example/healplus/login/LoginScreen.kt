package com.example.healplus.login
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.core.viewmodel.authviewmodel.AuthSate
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.acitivity.AdminActivity
import com.example.healplus.acitivity.MainActivity
import com.example.healplus.R

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val authSate = authViewModel.authSate.observeAsState()
    LaunchedEffect(authSate.value) {
        if (authSate.value is AuthSate.Admin) {
            // Chuyển sang MainActivity khi đã đăng nhập
            val intent = Intent(context, AdminActivity::class.java)
            context.startActivity(intent)
        }
        if (authSate.value is AuthSate.User) {
            val intent1 = Intent(context, MainActivity::class.java)
            context.startActivity(intent1)
        }

    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ){
        val (imgTop, imgEnd, tvImgStart, tvImgBottom, tvColunm)  = createRefs()
        Image(
            painter = painterResource(R.drawable.bubble_04),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .constrainAs(imgTop){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )
        Image(
                painter = painterResource(R.drawable.bubble_03),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth(0.7f) // Giữ tỷ lệ vuông
            .constrainAs(tvImgStart){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )
        Image(
            painter = painterResource(R.drawable.bubble_06),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.6f) // Co giãn theo chiều rộng màn hình
                .constrainAs(tvImgBottom){
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )
        val horizontalGuidelineImg = createGuidelineFromTop(0.2f)
        val horizontalGuideline = createGuidelineFromTop(0.4f)
        Image(
            painter = painterResource(R.drawable.bubblle_05),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.3f) // Co giãn theo chiều rộng màn hình
                .constrainAs(imgEnd){
                    top.linkTo(horizontalGuidelineImg)
                    end.linkTo(parent.end)
                }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .constrainAs(tvColunm) {
                    top.linkTo(horizontalGuideline)
                    start.linkTo(parent.start)
                }
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = TextStyle(
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32),
                    lineHeight = 54.sp
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier.align(Alignment.Start)
            )
            Text(
                text = stringResource(id = R.string.good_to_see),
                style = TextStyle(
                        fontSize = 19.sp,
                        lineHeight = 35.sp),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .align(Alignment.Start)
            )
            Spacer(modifier = Modifier
                .height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text (stringResource(id = R.string.email)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                singleLine = true
            )
            Spacer(modifier = Modifier
                .height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(id = R.string.password)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                singleLine = true,
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            painter = if (isPasswordVisible) painterResource(R.drawable.visibility_24px) else painterResource(R.drawable.visibility_off_24px),
                            contentDescription = "Toggle Password Visibility"
                        )
                    }
                }
            )
            Spacer(modifier = Modifier
                .height(37.dp))
            Button(
                onClick = {
                    authViewModel.loginAuthState(email, password)
                    Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                },
                enabled = authSate.value != AuthSate.Loading,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .height(61.dp)
                ,
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = stringResource(id = R.string.next),
                    color = Color.White,
                    fontSize = 22.sp)
            }
            TextButton(onClick = { navController.popBackStack() }) {
                Text(text = stringResource(id = R.string.cancel), color = Color.Gray)
            }
        }
    }
}