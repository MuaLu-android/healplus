package com.example.healplus.Login
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.healplus.R
import com.example.healplus.ViewModel.AuthSate
import com.example.healplus.ViewModel.AuthViewModel

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val authSate = authViewModel.authSate.observeAsState()
    LaunchedEffect(authSate.value) {
        when(authSate.value){
            is AuthSate.Authenticated -> navController.navigate("home")
            is AuthSate.Error -> Toast.makeText(context,
                (authSate.value as AuthSate.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
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
                            imageVector = if (isPasswordVisible) Icons.Filled.Face else Icons.Filled.Person,
                            contentDescription = "Toggle Password Visibility"
                        )
                    }
                }
            )
            Spacer(modifier = Modifier
                .height(37.dp))
            // Nút Done
            Button(
                onClick = {
                    authViewModel.loginAuthState(email, password)
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