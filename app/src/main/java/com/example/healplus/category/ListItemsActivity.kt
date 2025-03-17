package com.example.healplus.category
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel


@Composable
fun ListItemScreen(
    title: String,
    viewModel: ApiCallViewModel,
    idc: String,
    navController: NavController
){
    val items by viewModel.recommended.observeAsState(emptyList())
    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(idc) {
        viewModel.loadProductByIngredient(idc)
    }
    Column (modifier = Modifier
        .fillMaxSize()){
        ConstraintLayout(
            modifier = Modifier
                .padding(top = 36.dp, start = 16.dp, end = 16.dp)
        ) {
            val(backBtn, cartTxt) = createRefs()
            IconButton(
                onClick = { navController.popBackStack()},
                modifier = Modifier

                    .constrainAs(backBtn) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
            ){
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,

                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(cartTxt)
                    {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(backBtn.end, margin = 4.dp)
                    }
                ,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                text = title
            )

        }
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }else {
            ListItemsFullSize(items, navController)
        }

    }
    LaunchedEffect(items) {
        isLoading = items.isEmpty()
    }
}