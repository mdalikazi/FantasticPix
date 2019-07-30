package com.alikazi.codetest.weatherzone.repository

import androidx.paging.LivePagedListBuilder
import com.alikazi.codetest.weatherzone.database.AppDatabase
import com.alikazi.codetest.weatherzone.models.RequestResponseModels

class AppRepository(private val databaseInstance: AppDatabase) {

    fun loadPhotos(request: RequestResponseModels.ViewModelQueryRequest): RequestResponseModels.PhotosPagedListResponse {
        val dataSourceFactory = databaseInstance.photoDao().getAllPhotos()
        val boundaryCallback = PaginationBoundaryCallback(request, databaseInstance)
        val data = LivePagedListBuilder(dataSourceFactory, 5)
            .setBoundaryCallback(boundaryCallback)
            .build()
        return RequestResponseModels.PhotosPagedListResponse(data, boundaryCallback.networkErrors)
    }

}