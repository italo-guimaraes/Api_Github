package com.example.api_github.di

import com.example.api_github.data.api.GithubApi
import com.example.api_github.data.repository.GithubRepositoryImp
import com.example.api_github.domain.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.github.com/"

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideService() : GithubApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }

    @Singleton
    @Provides
    fun provideProductRepository(api: GithubApi) : GithubRepository {
        return GithubRepositoryImp(api)
    }

}