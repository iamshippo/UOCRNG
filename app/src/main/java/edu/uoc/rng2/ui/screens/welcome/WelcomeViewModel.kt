package edu.uoc.rng2.ui.screens.welcome

// Importaciones necesarias
import androidx.lifecycle.ViewModel
import edu.uoc.rng2.repositories.GameResultRepository

// ViewModel para la pantalla de bienvenida
class WelcomeViewModel : ViewModel() {
    // Repositorio para acceder a los datos del resultado del juego
    private val gameResultRepository = GameResultRepository()

    // LiveData que contiene el saldo actual del jugador
    val currentBalance = gameResultRepository.currentBalance() // Accede al saldo actual desde el repositorio
}