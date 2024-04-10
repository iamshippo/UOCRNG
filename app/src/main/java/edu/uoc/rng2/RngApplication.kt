package edu.uoc.rng2

import android.app.Application
import edu.uoc.rng2.db.AppDatabase

class RngApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AppDatabase.init(this)
    }
}