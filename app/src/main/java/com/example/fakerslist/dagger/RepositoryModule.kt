package com.example.fakerslist.dagger

import com.example.fakerslist.repository.PersonRepository
import com.example.fakerslist.repository.PersonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPersonRepository(personRepositoryImpl: PersonRepositoryImpl): PersonRepository
}