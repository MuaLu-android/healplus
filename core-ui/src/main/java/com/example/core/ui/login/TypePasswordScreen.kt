package com.example.core.ui.login
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.core.ui.R

@Composable
fun PassWordScreen() {
    var password by remember {
        mutableStateOf("")
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
                .fillMaxWidth(0.9f) // Co giãn theo chiều rộng màn hình
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

        val horizontalGuideline = createGuidelineFromTop(0.5f)
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
            // Ảnh đại diện
            Image(
                painter = painterResource(id = R.drawable.logo_app), // Thay ảnh avatar
                contentDescription = "Profile Avatar",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(4.dp, Color.White, CircleShape)
                    .offset(y = (-40).dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Chào người dùng
            Text(
                text = "Hello, Romina!!",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Hướng dẫn nhập mật khẩu
            Text(
                text = "Type your password",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Ô nhập mật khẩu
            Row(
                modifier = Modifier.padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(4) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Nút "Not you?"
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.clickable { /* Xử lý khi bấm */ }
            ) {
                Text(
                    text = "Not you?",
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Next",
                    tint = Color(0xFF0057FF)
                )
            }
        }
    }
}