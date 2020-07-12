package com.kehao.myapplication.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pixelforce.pixelforce_widget_demo.model.Link
import com.pixelforce.pixelforce_widget_demo.model.Multimedia

@Database(entities = [Movie::class, Link::class, Multimedia::class], exportSchema = false, version = 4)
abstract class AppDataBase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao

    companion object {
        @Volatile
        private var instance: AppDataBase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            "widgetDemoDatabase"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}