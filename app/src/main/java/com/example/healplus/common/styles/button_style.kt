package com.example.healplus.common.styles

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.viewmodel.authviewmodel.AuthSate

@Composable
fun ButtonStyle(
    @StringRes title: Int,
    onPressed: () -> Unit,
    authSate: State<AuthSate?>? = null
) {
    Button(
        onClick = onPressed,
        enabled = authSate?.value != AuthSate.Loading,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = stringResource(title),
            fontSize = 22.sp
        )
    }
}