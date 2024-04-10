package edu.uoc.rng2.ui.screens.history

import androidx.lifecycle.ViewModel
import edu.uoc.rng2.repositories.GameResultRepository

class HistoryViewModel : ViewModel() {
    private val gameResultRepository = GameResultRepository()

    val history = gameResultRepository.history
}