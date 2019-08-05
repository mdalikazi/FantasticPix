package com.alikazi.codetest.weatherzone.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alikazi.codetest.weatherzone.models.Photo

@Database(entities = [Photo::class], version = 1, exportSchema = false)
@TypeConverters(WZTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

	abstract fun photoDao(): PhotoDao

	companion object {
		private var databaseName = "WeatherZone.db"
		private var INSTANCE: AppDatabase? = null

		fun getInstance(context: Context): AppDatabase =
			INSTANCE ?: synchronized(this) {
				INSTANCE ?:
				Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
					.fallbackToDestructiveMigration()
					.build()
			}.also { INSTANCE = it }
	}

}