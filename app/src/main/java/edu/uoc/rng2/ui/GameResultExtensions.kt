package edu.uoc.rng2.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import edu.uoc.rng2.R
import edu.uoc.rng2.models.GameResult
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val GameResult.icon: Int
    get() = when (this.userWon) {
        true -> R.drawable.ic_cup
        false -> R.drawable.ic_thumb_down
    }

val GameResult.iconColor: Color
    @Composable
    get() = when (this.userWon) {
        true -> MaterialTheme.colorScheme.primary
        false -> MaterialTheme.colorScheme.error
    }

val GameResult.title: String
    @Composable
    get() = when (this.userWon) {
        true -> "Victoria en ${this.turns} turnos"
        false -> "Derrota en ${this.turns} turnos"
    }

val GameResult.formattedDate: String
    get() {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yy - HH:mm:ss", Locale.ENGLISH)
        val date = Date(date)

        return simpleDateFormat.format(date)
    }