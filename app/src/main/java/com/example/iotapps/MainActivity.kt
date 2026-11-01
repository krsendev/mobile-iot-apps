package com.example.iotapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.iotapps.model.Screen
import com.example.iotapps.model.SensorCatalog
import com.example.iotapps.ui.screens.DashboardScreen
import com.example.iotapps.ui.screens.ListSensorScreen
import com.example.iotapps.ui.screens.LoginScreen
import com.example.iotapps.ui.theme.IoTAppsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IoTAppsTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    var currentScreen by rememberSaveable { mutableStateOf(Screen.LOGIN) }
    val activeSensorIds = rememberSaveable {
        mutableStateListOf(SensorCatalog.availableSensors[0].id)
    }
    val activeSensors = SensorCatalog.availableSensors.filter { it.id in activeSensorIds }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        when (currentScreen) {
            Screen.LOGIN -> {
                LoginScreen(
                    onLoginSuccess = {
                        currentScreen = Screen.DASHBOARD
                    }
                )
            }Screen.DASHBOARD -> {
            DashboardScreen(
                activeSensors = activeSensors,
                onAddSensorClick = {
                    currentScreen = Screen.LIST_SENSOR
                },
                onDeleteSensor = { sensorToDelete ->
                    // Hapus ID dari list saveable
                    activeSensorIds.remove(sensorToDelete.id)
                }
            )
        }

            Screen.LIST_SENSOR -> {
                ListSensorScreen(
                    activeSensors = activeSensors,
                    onSensorSelect = { selectedSensor ->
                        if (!activeSensorIds.contains(selectedSensor.id)) {
                            activeSensorIds.add(selectedSensor.id)
                        }
                        currentScreen = Screen.DASHBOARD
                    },
                    onBack = {
                        currentScreen = Screen.DASHBOARD
                    }
                )
            }
        }
    }
}