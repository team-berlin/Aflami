package com.berlin.aflami.di

import org.koin.dsl.module
import usecase.SearchByCountryUseCase

val useCaseModule = module {
    single { SearchByCountryUseCase(get()) }
}