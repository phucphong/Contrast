package com.contrast.Contrast.di.module.app

import com.contrast.Contrast.di.qualifier.DefaultDispatcher
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.di.qualifier.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CoroutineDispatcherModule {
    @Singleton
    @DefaultDispatcher
    @Provides
    fun   provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
    @Singleton
    @IoDispatcher
    @Provides
    fun   provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
    @Singleton
    @MainDispatcher
    @Provides
    fun   provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main


}
