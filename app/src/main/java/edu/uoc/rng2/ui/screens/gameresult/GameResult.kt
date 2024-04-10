package edu.uoc.rng2.ui.screens.gameresult

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.uoc.rng2.Constants.GAME_PROFIT
import edu.uoc.rng2.models.GameResult
import edu.uoc.rng2.ui.formattedDate
import edu.uoc.rng2.ui.icon
import edu.uoc.rng2.ui.iconColor
import edu.uoc.rng2.ui.title

// Función componible para mostrar la pantalla de resultado del juego.
//
// @param viewModel El ViewModel para la pantalla de resultado del juego.
// @param onBack Función de callback para navegar de vuelta desde la pantalla.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameResult(
    viewModel: GameResultViewModel,
    onBack: () -> Unit,
) {
    // Suscripción al estado del resultado del juego usando RxJava3.
    val gameResult: GameResult? = viewModel.gameResult.subscribeAsState(null).value

    // Scaffold es un componente predefinido de la estructura de pantalla proporcionado por Jetpack Compose.
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            // TopAppBar es un componente de Material Design para mostrar una barra de aplicación superior.
            TopAppBar(
                title = {
                    Text("Resultado partida") // Mostrar el título de la pantalla.
                },
                navigationIcon = {
                    // IconButton es un componente de Material Design para iconos clicables.
                    IconButton(onClick = onBack) {
                        // Icon es un componente de Material Design para mostrar iconos.
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack, // Icono de retroceso.
                            contentDescription = null,
                        )
                    }
                },
            )
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (gameResult != null) {
                // Mostrar la información del resultado del juego si no es nulo.

                // Calcular el beneficio en función de si el usuario ganó o perdió.
                val profit = when(gameResult.userWon) {
                    true -> "+$GAME_PROFIT"
                    false -> "-$GAME_PROFIT"
                }
                Box(Modifier.weight(1f, true)) // Cuadro de espacio para el diseño.
                Icon(
                    painterResource(id = gameResult.icon), // Mostrar el icono del resultado del juego.
                    contentDescription = null,
                    tint = gameResult.iconColor,
                    modifier = Modifier.size(64.dp)
                )

                Text(
                    gameResult.title, // Mostrar el título del resultado del juego.
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
                )

                Text(
                    profit, // Mostrar el beneficio.
                    fontSize = 36.sp,
                    color = gameResult.iconColor,
                )

                Text(
                    "Monedas", // Mostrar el tipo de moneda.
                    fontSize = 24.sp,
                    color = gameResult.iconColor,
                )

                Box(Modifier.weight(1f, true)) // Cuadro de espacio para el diseño.

                Text(
                    gameResult.formattedDate, // Mostrar la fecha formateada.
                    modifier = Modifier.padding(24.dp)
                )
            }

        }
    }
}