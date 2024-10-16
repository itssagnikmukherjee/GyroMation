package com.sagnikmukherjee.gyroscopecontrol

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sagnikmukherjee.gyroscopecontrol.ui.theme.AvengerFont
import com.sagnikmukherjee.gyroscopecontrol.ui.theme.GyroscopeControlTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GyroscopeControlTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        GyroscopeImage(R.drawable.wolverine,"Wolverine")
//                        GyroscopeImage()
                    }
                }
            }
        }
    }
}

@Composable
fun GyroscopeImage(img: Int, heroName:String) {
    val context = LocalContext.current
    var rotationX by remember { mutableStateOf(0f) }
    var rotationY by remember { mutableStateOf(0f) }

    // Define rotation limits and smoothing factor
    val maxRotation = 10f // Maximum rotation in degrees
    val smoothingFactor = 0.1f // Adjust this value for more or less smoothing

    LaunchedEffect(Unit) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event != null) {
                    val deltaX = event.values[0] // Rotation around x-axis
                    val deltaY = event.values[1] // Rotation around y-axis

                    // Smooth out the rotation values
                    rotationX = (rotationX + deltaX * 10 * smoothingFactor).coerceIn(-maxRotation, maxRotation)
                    rotationY = (rotationY + deltaY * 10 * smoothingFactor).coerceIn(-maxRotation, maxRotation)
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
        sensorManager.registerListener(listener, gyroscopeSensor, SensorManager.SENSOR_DELAY_GAME)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$heroName",
            fontSize = 52.sp,
            fontFamily = AvengerFont,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End
        )
        Spacer(Modifier.height(60.dp))
        Image(
            painter = painterResource(id = img),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(500.dp).offset(-100.dp, 30.dp)
                .graphicsLayer(
                    rotationX = rotationX,
                    rotationY = rotationY
                )
        )
    }
}
