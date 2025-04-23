package com.contrast.Contrast.di.module
import com.itechpro.data.api.ProductAPI
import com.itechpro.data.repository.ProductRepositoryImpl
import com.itechpro.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository
}

@Module
@InstallIn(SingletonComponent::class)
object ProductNetworkModule {

    @Provides
    @Singleton
    fun provideProductAPI( retrofit: Retrofit): ProductAPI {
        return retrofit.create(ProductAPI::class.java)
    }

    @Provides
    fun provideProductUseCase(repository: ProductRepository): ProductUseCase {
        return ProductUseCase(repository)
    }




}
