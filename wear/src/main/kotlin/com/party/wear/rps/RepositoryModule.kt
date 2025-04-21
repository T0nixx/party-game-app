package com.party.wear.rps

import com.party.shared.rps.repository.RpsRepository
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