package com.party.wear.rps.di

import com.party.shared.rps.repository.RpsRepository
import com.party.wear.rps.data.repository.RpsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRpsRepository(
        impl: RpsRepositoryImpl
    ): RpsRepository
}