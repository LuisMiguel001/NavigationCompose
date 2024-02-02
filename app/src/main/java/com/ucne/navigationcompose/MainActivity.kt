package com.ucne.navigationcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.ucne.navigationcompose.ui.theme.NavigationComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationComposeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                        ){
                            Text(text = "Welcome")
                            Button(
                                onClick = {navController.navigate("register")},
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .padding(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Blue,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = "Crear Cuenta")
                            }
                            Button(
                                onClick = {navController.navigate("forgot_password")},
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .padding(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.LightGray,
                                    contentColor = Color.Red
                                )
                            ) {
                                Text(text = "Forgot password")
                            }
                        }
                    }
                    navigation(
                        startDestination = "login",
                        route = "auth"
                    ) {
                        composable("login") {
                            val viewModel = it.sharedViewModel<SampleViewModel>(navController)

                            Button(onClick = {
                                navController.navigate("calendar") {
                                    popUpTo("auth") {
                                        inclusive = true
                                    }
                                }
                            }) {
                                Text(text = "Calendar")
                            }
                        }
                        composable("Tools") {
                            Text(text = "How Can I help you?")
                        }
                        composable("register") {
                            val viewModel = it.sharedViewModel<SampleViewModel>(navController)
                            Column{
                                OutlinedTextField(
                                    value = "",
                                    onValueChange = {},
                                    placeholder = {
                                        Text(text = "Email")
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(18.dp)
                                )

                                OutlinedTextField(
                                    value = "",
                                    onValueChange = {},
                                    placeholder = {
                                        Text(text = "Password")
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                )
                            }
                        }
                        composable("forgot_password") {
                            val viewModel = it.sharedViewModel<SampleViewModel>(navController)
                            Text(text = "How Can I help you?")
                        }
                    }
                    navigation(
                        startDestination = "calendar_overviellw",
                        route = "calendar",
                    ) {
                        composable("calendar_overview") {
                            Column {
                                Text(text = "Fecha Del 1Parcial")
                            }
                        }
                        composable("calendar_entry") {
                            Column {
                                Text(text = "14-02-2024")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parenEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parenEntry)
}
