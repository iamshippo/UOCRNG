package edu.uoc.rng2.ui.screens.gameresult

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import edu.uoc.rng2.NavConstants
import edu.uoc.rng2.repositories.GameResultRepository

// Etiqueta de identificación para registros de información y depuración.
private const val TAG = "GameResultViewModel"

// ViewModel para la pantalla de resultado del juego.
class GameResultViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    // Identificador del resultado del juego recuperado del SavedStateHandle.
    private val gameResultId: Long = checkNotNull(savedStateHandle[NavConstants.GAME_RESULT_SCREEN_ARG_GAME_ID])
    // Repositorio para acceder a los resultados del juego.
    private val gameResultRepository = GameResultRepository()

    // LiveData para observar el resultado del juego.
    val gameResult = gameResultRepository.getGameResultById(gameResultId)

    // Inicialización del ViewModel.
    init {
        // Registro de información sobre el identificador del resultado del juego.
        Log.i(TAG, "Game result id: $gameResultId")
    }

}