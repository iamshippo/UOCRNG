package edu.uoc.rng2.usecases

import edu.uoc.rng2.Constants.GAME_PROFIT
import edu.uoc.rng2.models.GameResult
import edu.uoc.rng2.repositories.GameResultRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.schedulers.Schedulers

class SaveGameResultUseCase {
    private val gameResultRepository = GameResultRepository()

    operator fun invoke(personTurns: Int, date: Long, userWon: Boolean): Maybe<Long> {
        val profit = if (userWon) {
            GAME_PROFIT
        } else {
            -GAME_PROFIT
        }

        return gameResultRepository
            .currentBalance()
            .firstElement()
            .map { balance ->
                val gameResult = GameResult(
                    turns = personTurns,
                    date = date,
                    userWon = userWon,
                    profit = profit,
                    balance = balance + profit,
                )

                gameResultRepository.insertGameResult(gameResult)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}