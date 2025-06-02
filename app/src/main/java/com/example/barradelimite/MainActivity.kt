package com.example.barradelimite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.barradelimite.ui.theme.BarradeLimiteTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BarradeLimiteTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LimiteSlider()
                }
            }
        }
    }
}

@Composable
fun LimiteSlider() {
    val limite = remember { mutableStateOf(3000f) }
    val limiteMaximo = 5000f
    val limiteMinimo = 300f
    val progressoAnimado by animateFloatAsState(
        targetValue = (limite.value - limiteMinimo) / (limiteMaximo - limiteMinimo),
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Barra de Limite",
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive, // Escolhe um estilo de fonte
                color = Color.Green, // Define a cor verde
                modifier = Modifier.padding(bottom = 12.dp)
            )
            // Título
            Text(
                text = "Ajuste de Limite",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Valor do limite ajustado
            Text(
                text = "R$ ${limite.value.toInt()},00",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            // Texto informativo
            Text(
                text = "R$ ${limiteMaximo.toInt() - limite.value.toInt()},00 de limite disponível",
                fontSize = 16.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Slider interativo sem pontos
            Slider(
                value = limite.value,
                onValueChange = { limite.value = it },
                valueRange = limiteMinimo..limiteMaximo,
                colors = SliderDefaults.colors(
                    thumbColor = Color.Green,
                    activeTrackColor = Color.Green
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Barra de limite estilizada com bolinha dinâmica
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(Color.Green, Color(0xFF8A05BE))
                        )
                    )
            ) {
                // Bolinha indicadora do limite ajustado
                Box(
                    modifier = Modifier
                        .size(18.dp) // Tamanho da bolinha
                        .offset(x = (progressoAnimado * 280).dp) // Movendo dentro da barra
                        .clip(CircleShape)
                        .background(Color.White) // Cor da bolinha
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BarradeLimiteTheme {
        LimiteSlider()
    }
}