package com.example.healplus.cart

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healplus.ui.theme.backgroundDark

@Composable
fun PayScreen(selectedMethod: Int, onMethodSelected: (Int) -> Unit){
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Phương thức thanh toán", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        val paymentMethods = listOf(
            "Thanh toán tiền mặt khi nhận hàng",
            "Thanh toán bằng chuyển khoản (QR Code)",
            "Thanh toán bằng thẻ ATM nội địa và tài khoản ngân hàng",
            "Thanh toán bằng thẻ quốc tế Visa, Master, JCB, AMEX (GooglePay, ApplePay)",
            "Thanh toán bằng ví ZaloPay",
            "Thanh toán bằng ví MoMo",
            "Thanh toán bằng VNPay"
        )

        paymentMethods.forEachIndexed { index, method ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onMethodSelected(index) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedMethod == index,
                    onClick = { onMethodSelected(index) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(method)
            }
        }
        Button(
            onClick = { /* TODO: Handle confirmation */ },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text("Xác nhận", color = Color.White, fontSize = 16.sp)
        }
    }
}