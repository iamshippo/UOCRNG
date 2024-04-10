package edu.uoc.rng2.ui.screens.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.collectLatest

// Pantalla principal del juego.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Game(
    viewModel: GameViewModel, // ViewModel que contiene la lógica del juego.
    goToGameResult: (Long) -> Unit, // Función de navegación para ir al resultado del juego.
    onBack: () -> Unit, // Función de navegación para regresar a la pantalla anterior.
) {
    // Observa el estado del jugador actual y la cifra actual del ViewModel.
    val currentPlayer by viewModel.currentPlayer.collectAsState()
    val currentNumber by viewModel.currentNumber.collectAsState()

    // Se ejecuta cuando la partida termina para navegar al resultado del juego.
    LaunchedEffect(viewModel) {
        viewModel.gameEnded.collectLatest {
            goToGameResult(it)
        }
    }

    // Diseño de la pantalla.
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            // Barra de aplicaciones superior con título y botón de retroceso.
            TopAppBar(
                title = {
                    Text("Partida")
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
                }
            )
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Indicador de progreso si es el turno de la CPU.
            if (currentPlayer == Player.CPU) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            // Texto que indica el turno del jugador.
            Text("Turno de")

            // Texto que muestra el tipo de jugador actual (persona o CPU).
            val playerText = when(currentPlayer) {
                Player.PERSON -> "Jugador"
                Player.CPU -> "CPU"
            }

            Text(
                playerText,
                fontSize = 28.sp,
            )

            Box(modifier = Modifier.weight(1f, true))

            // Texto que muestra la cifra actual.
            Text("Cifra actual")
            Text(
                currentNumber.toString(),
                fontSize = 40.sp,
            )

            Box(modifier = Modifier.weight(1f, true))

            // Botón para generar un nuevo número si es el turno del jugador.
            Button(
                onClick = { viewModel.generateNewNumber() },
                enabled = currentPlayer == Player.PERSON,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Text("Generar número")
            }
        }
    }
}