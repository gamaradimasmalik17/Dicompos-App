package com.example.komposapp.bottombar

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.compost.ui.theme.Green40
import com.example.compost.viewModel.BottomBarViewModel
import com.example.komposapp.navigation.NavigationScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(navController: NavController,context: Context,viewModel: BottomBarViewModel) {
    // Access SharedPreferences
    val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    var isPowerIcon by rememberSaveable {
        mutableStateOf(sharedPreferences.getBoolean("power_state", true))
    }

    fun togglePowerState() {
        viewModel.togglePowerState(isPowerIcon) { success ->
            if (success) {
                isPowerIcon = !isPowerIcon
                with(sharedPreferences.edit()) {
                    putBoolean("power_state", isPowerIcon)
                    apply()
                }
                // Show success toast
                Toast.makeText(context, "Power state updated successfully", Toast.LENGTH_SHORT).show()
            } else {
                // Show failure toast
                Toast.makeText(context, "Failed to update power state", Toast.LENGTH_SHORT).show()
            }
        }
    }
    val navBarItemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.Black, // Warna teks saat item dipilih
        unselectedIconColor = Color.White, // Warna teks saat item tidak dipilih
        indicatorColor = Color(Green40.value),
        selectedTextColor = Color.Black, // Warna teks saat item dipilih
        unselectedTextColor = Color.White
    )

    val items = listOf(
        NavigationScreen.HomeScreen,
        NavigationScreen.ProfileScreen
    )

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }


    BottomNavigation(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .height(67.dp)
    ) {
        NavigationBar(containerColor = Color(Green40.value)) {

            NavigationBarItem(
                selected = selectedItemIndex == 0,
                onClick = {
                    selectedItemIndex = 0
                    navController.navigate(items[0].route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                label = { Text(text = items[0].title) },
                alwaysShowLabel = true,
                colors = navBarItemColors,
                icon = {
                    Box {
                        Icon(
                            imageVector = if (selectedItemIndex == 0) items[0].selectedIcon else items[0].unSelectedIcon,
                            contentDescription = items[0].title
                        )
                    }
                }
            )

            NavigationBarItem(
                selected = false,
                onClick = { togglePowerState() },
                label = { /* No label for power button */ },
                alwaysShowLabel = false,
                icon = {
                    Box(
                        modifier = Modifier
                            .size(64.dp)  // Adjust the size of the outer box
                            .border(2.dp, Color.Transparent, CircleShape)
                            .clip(CircleShape)
                            .background(if (isPowerIcon) Color.White
                            else Color.Red), // Color based on the state
                        contentAlignment = Alignment.Center // Center align the icon
                    ) {
                        Icon(
                            imageVector = if (isPowerIcon) Icons.Filled.PowerSettingsNew else Icons.Filled.Stop,
                            contentDescription = if (isPowerIcon) "Power" else "Stop",
                            tint = Color(Green40.value),
                            modifier = Modifier.size(32.dp)  // Adjust the size of the icon
                        )
                    }
                }
            )

            NavigationBarItem(
                selected = selectedItemIndex == 1,
                onClick = {
                    selectedItemIndex = 1
                    navController.navigate(items[1].route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                label = { Text(text = items[1].title) },
                alwaysShowLabel = true,
                colors = navBarItemColors,
                icon = {
                    Box {
                        Icon(
                            imageVector = if (selectedItemIndex == 1) items[1].selectedIcon else items[1].unSelectedIcon,
                            contentDescription = items[1].title
                        )
                    }
                }
            )
        }
    }
}
