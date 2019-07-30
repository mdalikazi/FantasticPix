package com.alikazi.codetest.weatherzone.utils

import com.alikazi.codetest.weatherzone.database.AppDatabase
import com.alikazi.codetest.weatherzone.repository.AppRepository
import com.alikazi.codetest.weatherzone.viewmodel.ViewModelFactory

object Injector {

    fun provideViewModelFactory(databaseInstance: AppDatabase): ViewModelFactory {
        return ViewModelFactory(provideRepository(databaseInstance))
    }

    private fun provideRepository(databaseInstance: AppDatabase): AppRepository {
        return AppRepository(databaseInstance)
    }
}