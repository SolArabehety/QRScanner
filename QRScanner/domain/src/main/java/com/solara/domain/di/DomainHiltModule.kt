package com.solara.domain.di

import com.solara.domain.usecases.GenerateSeedUseCase
import com.solara.domain.usecases.GenerateSeedUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DomainHiltModule {
    @Provides
    @Singleton
    fun providesGenerateSeedUseCase(): GenerateSeedUseCase =
        GenerateSeedUseCaseImpl()
}
