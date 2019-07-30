package com.alikazi.codetest.weatherzone.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alikazi.codetest.weatherzone.models.Photo

@Dao
interface PhotoDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun savePhotos(photos: List<Photo>?)

	@Query("SELECT * FROM Photo")
	fun getAllPhotos(): DataSource.Factory<Int, Photo>

	@Query("SELECT * FROM photo where id = :photoId")
	fun getOnePhoto(photoId: Int): LiveData<Photo>

}