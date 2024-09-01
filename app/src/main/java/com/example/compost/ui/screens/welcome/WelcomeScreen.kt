package com.example.compost.ui.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.compost.R
import com.example.compost.Screen
import com.example.compost.ui.theme.Green40
import com.example.compost.ui.theme.GreenGrey40
import com.example.compost.ui.theme.White12

@Composable
fun WelcomeScreen(navController: NavController){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenGrey40)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.logo_sementara),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(bottom = 20.dp)
                .height(200.dp),
            contentDescription = "Logo Compost"
        )
        Column(Modifier.fillMaxWidth()) {
            Text(
                text = "Selamat Datang di aplikasi\nDicompos",
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    fontSize = 30.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = Color(0xFF016734),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(start = 93.dp, end = 93.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            Image(
                painter = painterResource(R.drawable.ilustrasi2),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .height(200.dp),
                contentDescription = "ilustrasi"
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        OutlinedButton(
            onClick = {
                navController.navigate(Screen.LoginScreen.route)},
            shape = RoundedCornerShape(19.dp),
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Green40),
            modifier = Modifier
                .width(288.dp)
                .height(50.dp),


        ) {
            Text(
                text = "Masuk",
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = Color(White12.value)
                )
            )
        }


    }
}
