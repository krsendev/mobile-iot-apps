package com.example.iotapps.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.iotapps.model.SensorCatalog
import com.example.iotapps.model.SensorItem
import com.example.iotapps.ui.components.getSensorIcon
import com.example.iotapps.ui.theme.IoTAppsTheme
import kotlin.collections.List

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListSensorScreen(
    activeSensors: List<SensorItem>,
    onSensorSelect: (SensorItem) -> Unit,
    onBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pilihan Sensor IoT") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali ke Dashboard"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(SensorCatalog.availableSensors, key = { it.id }) { sensor ->
                val isAlreadyActive = activeSensors.any { it.id == sensor.id }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = !isAlreadyActive) {
                            onSensorSelect(sensor)
                            // Memunculkan pesan ketika sensor ditambahkan
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "${sensor.name} berhasil ditambahkan"
                                )
                            }
                        },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isAlreadyActive) {
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    )
                ) {
                    ListItem(
                        leadingContent = {
                            Icon(
                                imageVector = getSensorIcon(sensor.type),
                                contentDescription = sensor.name,
                                tint = if (isAlreadyActive) {
                                    MaterialTheme.colorScheme.outline
                                } else {
                                    MaterialTheme.colorScheme.primary
                                },
                                modifier = Modifier.size(28.dp)
                            )
                        },
                        headlineContent = {
                            Text(
                                text = sensor.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        supportingContent = {
                            Text(
                                text = "Modul: ${sensor.type} | Satuan: ${sensor.unit}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        trailingContent = {
                            if (isAlreadyActive) {
                                Text(
                                    text = "Sensor Aktif",
                                    color = MaterialTheme.colorScheme.outline,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Tambahkan",
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

// Preview untuk Mobile kondisi Potrait
@Preview(
    name = "Phone - Potrait",
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_7
)
// Preview untuk Tablet kondisi Landscape
@Preview(
    name = "Tablet - Landscape",
    showBackground = true,
    showSystemUi = true,
    device = Devices.TABLET
)
@Composable
fun ListSensorScreenPreview() {
    IoTAppsTheme {
        ListSensorScreen(
            activeSensors = SensorCatalog.availableSensors,
            onSensorSelect = {},
            onBack = {}
        )
    }
}