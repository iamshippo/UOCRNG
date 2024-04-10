// Paquete para manejar la base de datos y sus entidades
package edu.uoc.rng2.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.uoc.rng2.db.dao.GameResultDao
import edu.uoc.rng2.models.GameResult
import java.lang.IllegalStateException


// Clase de Room para gestionar los resultados del juego.
@Database(
    entities = [
        GameResult::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class RngDatabase : RoomDatabase() {
    // Método abstracto para obtener el GameResultDao.
    abstract fun gameResultDao(): GameResultDao
}

// Objeto singleton para acceder a la base de datos de Room.
object AppDatabase {
    private var db: RngDatabase? = null

    // Inicializa la base de datos de Room.
    // @param app Instancia de la aplicación.
    fun init(app: Application) {
        db = Room
            .databaseBuilder(app, RngDatabase::class.java, "app.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    // Obtiene la instancia de la base de datos de Room.
    // @return Instancia de RngDatabase.
    // @throws IllegalStateException si la base de datos no está inicializada.
    fun db(): RngDatabase {
        return db ?: throw IllegalStateException("Uninitialized Database")
    }
}