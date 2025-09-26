package com.paw.key.core.util

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun PermissionRequestEffect(
    permissions: Array<String>,
    onResult: (isGranted: Boolean) -> Unit
) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissionsMap ->
            val allPermissionsGranted = permissionsMap.values.all { it }
            onResult(allPermissionsGranted)
        }
    )

    LaunchedEffect(Unit) {
        permissionLauncher.launch(permissions)
    }
}