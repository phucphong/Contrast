package com.contrast.Contrast.di.module




import com.itechpro.data.api.ProductViewSaveAPI
import com.itechpro.data.repository.ProductViewSaveRepositoryImpl
import com.itechpro.domain.repository.ProductViewSaveRepository
import com.itechpro.domain.usecase.product_view_save.ProductViewSaveUseCase



import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductViewSaveModule {

    @Binds
    @Singleton
    abstract fun bindProductViewSaveRepository(
        impl: ProductViewSaveRepositoryImpl
    ): ProductViewSaveRepository
}

@Module
@InstallIn(SingletonComponent::class)
object ProductViewSaveNetworkModule {

    @Provides
    @Singleton
    fun provideProductViewSaveAPI( retrofit: Retrofit): ProductViewSaveAPI {
        return retrofit.create(ProductViewSaveAPI::class.java)
    }

    @Provides
    fun provideProductViewSaveUseCase(repository: ProductViewSaveRepository): ProductViewSaveUseCase {
        return ProductViewSaveUseCase(repository)
    }




}
