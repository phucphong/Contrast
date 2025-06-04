package com.contrast.Contrast.di.module.contact







import com.itechpro.data.api.contact.ContactListAPI
import com.itechpro.data.repository.contact.ContactListRepositoryImpl
import com.itechpro.domain.repository.contact.ContactListRepository
import com.itechpro.domain.usecase.contact.ContactListUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ContactListModule {

    @Binds
    @Singleton
    abstract fun bindContactListRepository(
        impl: ContactListRepositoryImpl
    ): ContactListRepository



}

@Module
@InstallIn(SingletonComponent::class)
object ContactListNetworkModule {

    @Provides
    @Singleton
    fun provideContactListAPI(retrofit: Retrofit): ContactListAPI {
        return retrofit.create(ContactListAPI::class.java)
    }


    @Provides
    fun provideContactListUseCase(repository: ContactListRepository): ContactListUseCase {
        return ContactListUseCase(repository)
    }


}
