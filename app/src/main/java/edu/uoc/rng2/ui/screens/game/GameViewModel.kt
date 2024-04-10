package edu.uoc.rng2.ui.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.uoc.rng2.models.GameResult
import edu.uoc.rng2.repositories.GameResultRepository
import edu.uoc.rng2.usecases.SaveGameResultUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

// Número inicial del juego.
private const val INITIAL_GAME_NUMBER = 100
// Número mínimo del juego.
private const val MIN_GAME_NUMBER = 1
// Retraso para la jugada de la CPU en milisegundos.
private const val CPU_PLAY_DELAY = 1000L

// ViewModel para la pantalla del juego.
class GameViewModel : ViewModel() {
    // Manejador de suscripciones.
    private val compositeDisposable = CompositeDisposable()
    // Caso de uso para guardar el resultado del juego.
    private val saveGameResultUseCase = SaveGameResultUseCase()

    // Fecha actual en milisegundos desde el inicio de la época.
    private val date = System.currentTimeMillis()

    // Estado del jugador actual (persona o CPU).
    val currentPlayer = MutableStateFlow(Player.PERSON)
    // Número actual en el juego.
    val currentNumber = MutableStateFlow(INITIAL_GAME_NUMBER)

    // Flujo compartido para indicar que la partida ha terminado.
    val gameEnded = MutableSharedFlow<Long>()

    // Número de turnos realizados por la persona.
    private var personTurns = 0

    // Limpia las suscripciones cuando se destruye el ViewModel.
    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }

    // Genera un nuevo número en el juego.
    fun generateNewNumber() {
        personTurns++

        val newNumber = newRandomNumber()

        if (isGameFinished(newNumber)) {
            persistGameResult()
        } else {
            currentNumber.value = newNumber
            currentPlayer.value = Player.CPU

            cpuTurn()
        }
    }

    // Jugada de la CPU.
    private fun cpuTurn() = viewModelScope.launch {
        delay(CPU_PLAY_DELAY)

        val newNumber = newRandomNumber()

        if (isGameFinished(newNumber)) {
            persistGameResult()
        } else {
            currentNumber.value = newNumber
            currentPlayer.value = Player.PERSON
        }
    }

    // Guarda el resultado del juego.
    private fun persistGameResult() {
        val userWon = currentPlayer.value != Player.PERSON

        val disposable = saveGameResultUseCase(
            personTurns,
            date,
            userWon,
        ).subscribe {
            viewModelScope.launch {
                gameEnded.emit(it)
            }
        }

        compositeDisposable.add(disposable)
    }

    // Comprueba si el juego ha terminado.
    private fun isGameFinished(newNumber: Int): Boolean {
        return newNumber == MIN_GAME_NUMBER
    }

    // Genera un nuevo número aleatorio.
    private fun newRandomNumber(): Int {
        val number = currentNumber.value

        return Random.nextInt(MIN_GAME_NUMBER, number)
    }
}

// Enumeración para representar el tipo de jugador.
enum class Player {
    PERSON,
    CPU
}