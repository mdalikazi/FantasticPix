package com.alikazi.codetest.weatherzone.utils

import com.alikazi.codetest.weatherzone.main.AppRepository
import com.alikazi.codetest.weatherzone.viewmodel.ViewModelFactory

object Injector {

    fun provideViewModelFactory(): ViewModelFactory {
        return ViewModelFactory(provideRepository())
    }

    private fun provideRepository(): AppRepository {
        return AppRepository()
    }
}