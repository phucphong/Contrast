package com.contrast.Contrast.di.module



import com.itechpro.data.api.CategoryAPI
import com.itechpro.data.repository.CategoryRepositoryImpl
import com.itechpro.domain.repository.CategoryRepository
import com.itechpro.domain.repository.RegisterAccountRepository
import com.itechpro.domain.usecase.category.CategoryUseCase

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryModule {

    @Binds
    @Singleton
    abstract fun bindRegisterAccountRepository(
        impl: CategoryRepositoryImpl
    ): CategoryRepository
}

@Module
@InstallIn(SingletonComponent::class)
object CategoryNetworkModule {

    @Provides
    @Singleton
    fun provideCategoryAPI(@Named("CustomDomain") retrofit: Retrofit): CategoryAPI {
        return retrofit.create(CategoryAPI::class.java)
    }

    // thêm các UseCase
    @Provides
    @Singleton
    fun provideCategoryUseCase(repository: CategoryRepository): CategoryUseCase {
        return CategoryUseCase(repository)
    }
  

}
