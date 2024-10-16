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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
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
                        GyroscopeImage(R.drawable.wolverine,"Wolverine", nickName = "Logan, James, Jimmy","Healing Factor, Claws\nFerral Senses","Mutant", color = Color.Yellow)
//                        GyroscopeImage()
                    }
                }
            }
        }
    }
}
@Composable
fun GyroscopeImage(img: Int, heroName: String, nickName: String, powers: String, team: String, color: Color) {
    val context = LocalContext.current
    var rotationX by remember { mutableStateOf(0f) }
    var rotationY by remember { mutableStateOf(0f) }
    var translationX by remember { mutableStateOf(0f) }
    var translationY by remember { mutableStateOf(0f) }

    // Define rotation and translation limits and smoothing factors
    val maxRotation = 5f // Maximum rotation in degrees
    val maxTranslation = 100f // Maximum translation in pixels (adjust as needed)
    val smoothingFactor = 0.1f // Adjust this value for smoothing rotation and translation

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

                    // Smooth out the translation values
                    translationX = (translationX + deltaX * smoothingFactor * 10).coerceIn(-maxTranslation, maxTranslation)
                    translationY = (translationY + deltaY * smoothingFactor * 10).coerceIn(-maxTranslation, maxTranslation)
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
        sensorManager.registerListener(listener, gyroscopeSensor, SensorManager.SENSOR_DELAY_GAME)
    }

    Box(modifier = Modifier.fillMaxSize().padding(40.dp)) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f)
                .offset(x = 50.dp)
                .graphicsLayer(
                    rotationX = rotationY,
                    rotationY = rotationX
                )
        ) {
            Text(
                text = "$heroName",
                fontSize = 52.sp,
                fontFamily = AvengerFont,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.info),
                    tint = color,
                    modifier = Modifier.size(40.dp).padding(5.dp),
                    contentDescription = null
                )
                Text(text = "$nickName", Modifier.padding(start = 10.dp))
            }
            Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.power),
                    tint = color,
                    modifier = Modifier.size(40.dp).padding(5.dp),
                    contentDescription = null
                )
                Text(text = "$powers", Modifier.padding(start = 10.dp))
            }
            Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.team),
                    tint = color,
                    modifier = Modifier.size(40.dp).padding(5.dp),
                    contentDescription = null
                )
                Text(text = "$team", Modifier.padding(start = 10.dp))
            }

            Spacer(Modifier.height(60.dp))
            Image(
                painter = painterResource(id = img),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(300.dp)
                    .offset(-100.dp, 30.dp)
                    .graphicsLayer(
                        rotationX = rotationX,
                        rotationY = rotationY
                    )
            )
        }

        Box(
            modifier = Modifier
                .size(250.dp) // Set a fixed size for the circle
                .offset(x = translationX.dp, y = translationY.dp)
                .offset(-100.dp, 350.dp) // Adjust to display part of the circle
                .clip(CircleShape) // Use CircleShape to get a perfect circle
                .background(color)
                .zIndex(1f)
        )


        IconButton(
            onClick = {},
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .size(60.dp)
                .align(Alignment.BottomEnd),
            colors = IconButtonColors(
                containerColor = color,
                contentColor = Color.Black,
                disabledContainerColor = Color.White,
                disabledContentColor = Color.White
            )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}
