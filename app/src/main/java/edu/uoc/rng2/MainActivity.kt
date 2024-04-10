package edu.uoc.rng2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.uoc.rng2.NavConstants.GAME_RESULT_SCREEN
import edu.uoc.rng2.NavConstants.GAME_RESULT_SCREEN_ARG_GAME_ID
import edu.uoc.rng2.NavConstants.GAME_SCREEN
import edu.uoc.rng2.NavConstants.HISTORY_SCREEN
import edu.uoc.rng2.NavConstants.WELCOME_SCREEN
import edu.uoc.rng2.ui.screens.welcome.Welcome
import edu.uoc.rng2.ui.screens.game.Game
import edu.uoc.rng2.ui.screens.game.GameViewModel
import edu.uoc.rng2.ui.screens.gameresult.GameResult
import edu.uoc.rng2.ui.screens.gameresult.GameResultViewModel
import edu.uoc.rng2.ui.screens.history.History
import edu.uoc.rng2.ui.screens.history.HistoryViewModel
import edu.uoc.rng2.ui.screens.welcome.WelcomeViewModel
import edu.uoc.rng2.ui.theme.RNG2Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RNG2Theme {
                MainView() // Establece la vista principal de la aplicación
            }
        }
    }

    @Composable
    fun MainView() {
        val navController = rememberNavController() // Crea un controlador de navegación

        NavHost(navController, startDestination = WELCOME_SCREEN) {
            composable(WELCOME_SCREEN) {
                val viewModel = viewModel<WelcomeViewModel>() // Obtiene el ViewModel para la pantalla de bienvenida

                Welcome(
                    viewModel = viewModel,
                    goToHistory = { navController.navigate(HISTORY_SCREEN) }, // Navega a la pantalla de historial
                    goToGame = { navController.navigate(GAME_SCREEN) }, // Navega a la pantalla de juego
                    exitApp = { finish() }, // Sale de la aplicación
                )
            }
            composable(HISTORY_SCREEN) {
                val viewModel = viewModel<HistoryViewModel>() // Obtiene el ViewModel para la pantalla de historial

                History(
                    onBack = { navController.popBackStack() }, // Regresa atrás en la pila de navegación
                    viewModel = viewModel,
                    onGameResultClick = { navigateToGameResult(navController, it, false) } // Navega a la pantalla de resultados del juego
                )
            }
            composable(GAME_SCREEN) {
                val viewModel = viewModel<GameViewModel>() // Obtiene el ViewModel para la pantalla de juego

                Game(
                    viewModel,
                    goToGameResult = { gameId ->
                        navigateToGameResult(
                            navController = navController,
                            gameResultId = gameId,
                            goBack = true
                        )
                    },
                    onBack = { navController.popBackStack() }, // Regresa atrás en la pila de navegación
                )
            }
            composable(
                GAME_RESULT_SCREEN,
                arguments = listOf(navArgument(GAME_RESULT_SCREEN_ARG_GAME_ID) {
                    type = NavType.LongType
                })
            ) {
                val viewModel = viewModel<GameResultViewModel>() // Obtiene el ViewModel para la pantalla de resultados del juego

                GameResult(
                    viewModel,
                    onBack = { navController.popBackStack() } // Regresa atrás en la pila de navegación
                )
            }
        }
    }

    private fun navigateToGameResult(
        navController: NavController,
        gameResultId: Long,
        goBack: Boolean
    ) {
        val path =
            GAME_RESULT_SCREEN.replace("{$GAME_RESULT_SCREEN_ARG_GAME_ID}", gameResultId.toString()) // Reemplaza el argumento en la ruta

        if (goBack) {
            navController.popBackStack() // Regresa atrás en la pila de navegación si es necesario
        }

        navController.navigate(path) // Navega a la pantalla de resultados del juego
    }
}
