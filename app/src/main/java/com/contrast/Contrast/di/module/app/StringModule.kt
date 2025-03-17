package com.contrast.Contrast.di.module.app

import com.contrast.Contrast.utils.StringProvider
import com.contrast.Contrast.utils.StringProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StringModule {
    @Binds
    @Singleton
    abstract fun bindStringProvider(
        impl: StringProviderImpl
    ): StringProvider
}
