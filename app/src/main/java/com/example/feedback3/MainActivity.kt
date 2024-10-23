package com.example.feedback3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NovelaApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NovelaApp() {
    val navController = rememberNavController()
    val viewModel: NovelaViewModel = viewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Gestión de Novelas") }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavigationHost(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun NavigationHost(
    navController: NavHostController,
    viewModel: NovelaViewModel
) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            PantallaPrincipal(
                novelas = viewModel.novelas,
                onAgregarClick = { navController.navigate("agregar") },
                onEliminarClick = { novela -> viewModel.eliminarNovela(novela) },
                onVerDetallesClick = { novela ->
                    navController.navigate("detalles/${novela.titulo}")
                },
                isLoading = false // Aquí puedes controlar el estado de carga desde PantallaPrincipal
            )
        }
        composable("agregar") {
            AgregarNovela { novela ->
                viewModel.agregarNovela(novela)
                navController.popBackStack()
            }
        }
        composable("detalles/{titulo}") { backStackEntry ->
            val titulo = backStackEntry.arguments?.getString("titulo")
            val novela = viewModel.novelas.firstOrNull { it.titulo == titulo }
            novela?.let {
                DetallesNovela(
                    novela = it,
                    onMarcarFavorita = { novela ->
                        viewModel.marcarFavorita(novela)
                    },
                    onVolver = { navController.popBackStack() }
                )
            }
        }
        composable("resenas") {
            PantallaResenas(novelas = viewModel.novelas) { novela ->
                navController.navigate("agregar_resena/${novela.titulo}")
            }
        }
        composable("agregar_resena/{titulo}") { backStackEntry ->
            val titulo = backStackEntry.arguments?.getString("titulo")
            val novela = viewModel.novelas.firstOrNull { it.titulo == titulo }
            novela?.let {
                AgregarResena(novela = it) { resena ->
                    viewModel.agregarResena(novela, resena)
                    navController.popBackStack() // Regresar a la pantalla de reseñas
                }
            }
        }
    }
}
