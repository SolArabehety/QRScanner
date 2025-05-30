package com.solara.qrscanner.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.solara.qrscanner.R
import com.solara.qrscanner.ui.navigation.MainPaths


@Composable
internal fun HomeScreen(navController: NavHostController) {
    var menuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            Box {
                FloatingActionButton(onClick = { menuExpanded = !menuExpanded }) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = stringResource(R.string.menu_description)
                    )
                }
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.menu_scan_qr)) },
                        onClick = {
                            menuExpanded = false
                            navController.navigate(MainPaths.SCAN_QR)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.menu_create_qr)) },
                        onClick = {
                            menuExpanded = false
                            navController.navigate(MainPaths.CREATE_QR)
                        }
                    )
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text("Main Screen", style = MaterialTheme.typography.headlineMedium)
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}