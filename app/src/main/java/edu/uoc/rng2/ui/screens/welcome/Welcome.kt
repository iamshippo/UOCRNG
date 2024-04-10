package edu.uoc.rng2.ui.screens.welcome

// Importaciones necesarias
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.uoc.rng2.Constants
import edu.uoc.rng2.R

// Anotación para indicar que se está utilizando una característica experimental de Material3
@OptIn(ExperimentalMaterial3Api::class)
@Composable
// Función para mostrar la pantalla de bienvenida
fun Welcome(
    viewModel: WelcomeViewModel, // ViewModel que proporciona datos necesarios
    goToHistory: () -> Unit, // Función para ir al historial de resultados
    goToGame: () -> Unit, // Función para iniciar el juego
    exitApp: () -> Unit, // Función para salir de la aplicación
) {
    // Obtener el saldo actual como un estado que puede cambiar
    val currentBalance by viewModel.currentBalance.subscribeAsState(Constants.INITIAL_BALANCE)

    // Estructura de la pantalla utilizando Scaffold de Material3
    Scaffold(
        modifier = Modifier.fillMaxSize(), // Ocupa toda la pantalla
        topBar = {
            // Barra superior de la pantalla
            TopAppBar(
                title = {
                    Text("RNG") // Título de la barra superior
                },
                actions = {
                    var showMenu by remember {
                        mutableStateOf(false)
                    }

                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = null,
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            onClick = goToGame,
                            text = { Text("Jugar") }
                        )
                        DropdownMenuItem(
                            onClick = goToHistory,
                            text = { Text("Histórico") }
                        )
                        DropdownMenuItem(
                            onClick = exitApp,
                            text = { Text("Salir") }
                        )
                    }
                }
            )
        },
    ) {
        // Columna principal que organiza los elementos verticalmente
        Column(
            modifier = Modifier
                .padding(it) // Agregar relleno alrededor del contenido
                .fillMaxSize(), // Ocupa to do el espacio disponible
            horizontalAlignment = Alignment.CenterHorizontally, // Alineación horizontal al centro
        ) {
            // Espacio en blanco en la parte superior
            Box(modifier = Modifier.weight(1f))

            // Botón para iniciar el juego
            Button(
                onClick = goToGame,
                modifier = Modifier.width(120.dp),
            ) {
                Text("Jugar")
            }

            // Botón para ir al historial de resultados
            Button(
                onClick = goToHistory,
                modifier = Modifier.width(120.dp),
            ) {
                Text("Histórico")
            }

            // Botón para salir de la aplicación
            Button(
                onClick = {
                    exitApp()
                },
                modifier = Modifier.width(120.dp),
            ) {
                Text("Salir")
            }

            Box(modifier = Modifier.weight(1f))

            // Texto "Monedas" para indicar el saldo
            Text(
                "Monedas",
                modifier = Modifier.padding(bottom = 8.dp),
            )

            // Texto que muestra el saldo actual
            Text(
                currentBalance.toString(),
                modifier = Modifier.padding(bottom = 24.dp),
                fontSize = 24.sp,
            )
        }
    }
}