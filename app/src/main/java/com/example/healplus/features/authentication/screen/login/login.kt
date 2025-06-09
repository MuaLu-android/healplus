package com.example.healplus.features.authentication.screen.login

import AuthCheck
import TextSkipPage
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.R
import com.example.healplus.common.styles.ButtonStyle
import tOutLindTextField

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authSate = authViewModel.authSate.observeAsState()
    //Check role
    AuthCheck(authSate)
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
    ) {
        val (imgTop, imgEnd, tvImgStart, tvImgBottom, tColunm) = createRefs()
        Image(
            painter = painterResource(R.drawable.bubble_04),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .constrainAs(imgTop) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )
        Image(
            painter = painterResource(R.drawable.bubble_03),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .constrainAs(tvImgStart) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )
        Image(
            painter = painterResource(R.drawable.bubble_06),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .constrainAs(tvImgBottom) {
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
                .fillMaxWidth(0.3f)
                .constrainAs(imgEnd) {
                    top.linkTo(horizontalGuidelineImg)
                    end.linkTo(parent.end)
                }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .constrainAs(tColunm) {
                    top.linkTo(horizontalGuideline)
                    start.linkTo(parent.start)
                },
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
                    lineHeight = 35.sp
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            //Body
            email = tOutLindTextField(text = R.string.email, title = email)
            Spacer(modifier = Modifier.height(8.dp))
            password = tOutLindTextField(text = R.string.password, title = password, tShowIcon = true)
            Spacer(modifier = Modifier.height(37.dp))
            // Footer
            ButtonStyle(
                title = R.string.next,
                authSate = authSate,
                onPressed = { authViewModel.loginAuthState(email, password) })
            TextSkipPage(title = R.string.cancel, onPressed = { navController.popBackStack() })
        }
    }
}