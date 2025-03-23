package com.example.healplus.login
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.core.viewmodel.authviewmodel.AuthSate
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.acitivity.AdminActivity
import com.example.healplus.acitivity.MainActivity
import com.example.healplus.R

@Composable
fun CreateAccountScreen(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var localImageUrl by remember { mutableStateOf<String?>("") }
    val role by remember { mutableStateOf<String?>("user") }
    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        localImageUrl = uri?.toString()?:"" // Lưu đường dẫn ảnh cục bộ
    }
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
        val (imgTop, imgEnd, tvColunm)  = createRefs()
        Image(
            painter = painterResource(R.drawable.bubble_02),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .constrainAs(imgTop){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )
        val horizontalGuidelineImg = createGuidelineFromTop(0.1f)
        val horizontalGuideline = createGuidelineFromTop(0.14f)
        Image(
            painter = painterResource(R.drawable.bubble_01),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.3f)
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
                text = stringResource(id = R.string.creat_account),
                style = TextStyle(
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32),
                    lineHeight = 54.sp
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = if (localImageUrl != "") rememberAsyncImagePainter(localImageUrl)
                else painterResource(R.drawable.icon_camera),
                contentDescription = "Chọn ảnh",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .clickable { imagePickerLauncher.launch("image/*") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(stringResource(id = R.string.email)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text(stringResource(id = R.string.fullname)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Password Input Field
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
            Spacer(modifier = Modifier.height(8.dp))
            // Phone Number Input Field
            ConstraintLayout (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(24.dp))
            ) {
                val(tvImg, tvIcon, tvSpacer, tvTxt) = createRefs()
                Image(
                    painter = painterResource(id = R.drawable.flag), // Thay bằng cờ quốc gia
                    contentDescription = "Country Flag",
                    modifier = Modifier
                        .size(24.dp)
                        .constrainAs(tvImg) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start, margin = 16.dp)
                            bottom.linkTo(parent.bottom)
                        }
                )
                IconButton(onClick = { /* Mở danh sách quốc gia */ },
                    modifier = Modifier
                        .constrainAs(tvIcon){
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(tvImg.end, margin = 2.dp)
                        }
                ) {
                    Icon(Icons.Default.ArrowDropDown,
                        contentDescription = "Select Country")
                }
                Spacer(
                    modifier = Modifier
                        .height(30.dp)
                        .width(1.dp)
                        .background(Color.Black)
                        .constrainAs(tvSpacer) {
                            top.linkTo(tvIcon.top)
                            bottom.linkTo(tvIcon.bottom)
                            start.linkTo(tvIcon.end, margin = 2.dp)
                        }
                )

                BasicTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    modifier = Modifier
                        .padding(start = 62.dp)
                        .fillMaxWidth()
                        .constrainAs(tvTxt) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(tvSpacer.end, margin = 2.dp)
                        },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Start // Căn
                    )
                )
            }

            // Nút Done
            Button(
                onClick = {
                    authViewModel.signupAuthState(email, password,fullName, phoneNumber, localImageUrl!!, role.toString())
                },
                enabled = authSate.value != AuthSate.Loading,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .height(61.dp)
                ,
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = stringResource(id = R.string.done),
                    color = Color.White,
                    fontSize = 22.sp)
            }
            TextButton(onClick = { navController.popBackStack() }) {
                Text(text = stringResource(id = R.string.cancel), color = Color.Gray)
            }
        }
    }
}