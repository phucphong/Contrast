package com.contrast.Contrast.di.module



import com.itechpro.data.api.CategoryAffiliateAPI
import com.itechpro.data.repository.CategoryAffiliateRepositoryImpl
import com.itechpro.domain.repository.CategoryAffiliateRepository
import com.itechpro.domain.usecase.category.CategoryAffiliateUseCase


import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryAffiliateModule {

    @Binds
    @Singleton
    abstract fun bindCategoryAffiliateRepository(
        impl: CategoryAffiliateRepositoryImpl
    ): CategoryAffiliateRepository
}

@Module
@InstallIn(SingletonComponent::class)
object CategoryAffiliateNetworkModule {

    @Provides
    @Singleton
    fun provideCategoryAffiliateAPI( retrofit: Retrofit): CategoryAffiliateAPI {
        return retrofit.create(CategoryAffiliateAPI::class.java)
    }

    @Provides
    fun provideCategoryAffiliateUseCase(repository: CategoryAffiliateRepository): CategoryAffiliateUseCase {
        return CategoryAffiliateUseCase(repository)
    }





}
