package com.example.fakerslist.dagger

import com.example.fakerslist.repository.PersonRepository
import com.example.fakerslist.repository.PersonRepositoryImpl
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    abstract fun bindPersonRepository(personRepositoryImpl: PersonRepositoryImpl): PersonRepository
}