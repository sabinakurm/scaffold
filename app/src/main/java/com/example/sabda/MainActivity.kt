package com.example.sabda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sabda.ui.theme.SabdaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SabdaTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        NavigationHost(navController, modifier = Modifier.padding(it))
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf("Экран 1", "Экран 3")
    BottomNavigation {
        items.forEach { screen ->
            val icon = when (screen) {
                "Home" -> Icons.Filled.Home
                "Call" -> Icons.Filled.Call
                else -> Icons.Filled.Info
            }
            BottomNavigationItem(
                icon = { Icon(icon, contentDescription = null) },
                label = { Text(screen) },
                selected = false,
                onClick = {
                    when (screen) {
                        "Экран 1" -> navController.navigate("screen1") { popUpTo("screen1") }
                        "Экран 3" -> navController.navigate("screen3") { popUpTo("screen1") }
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier) {
    NavHost(
        modifier = modifier,
        navController = navController, startDestination = "screen1") {
        composable("screen1") { Screen1(navController) }
        composable("screen2/{item}") { backStackEntry ->
            val item = backStackEntry.arguments?.getString("item")
            Screen2(item = item, navController = navController)
        }
        composable("screen3") { Screen3(navController) }
    }
}

@Composable
fun Screen1(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Экран 1") }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            List(5) { index ->
                Text(
                    text = "Элемент $index",
                    style = MaterialTheme.typography.body1.copy(lineHeight = 24.sp),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            navController.navigate("screen2/$index")
                        }
                )
            }
        }
    }
}

@Composable
fun Screen2(item: String?, navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Экран 2") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Детальная информация: $item",
                style = MaterialTheme.typography.body1.copy(lineHeight = 24.sp),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun Screen3(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Экран 3") }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Статический текст на экране 3",
                    style = MaterialTheme.typography.body1.copy(lineHeight = 24.sp),
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { navController.navigate("screen1") }) {
                    Icon(Icons.Filled.Home, contentDescription = "Домой")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Вернуться на Экран 1")
                }
            }
        }
    }
}
