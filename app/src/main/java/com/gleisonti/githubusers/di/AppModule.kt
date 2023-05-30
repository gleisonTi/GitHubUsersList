package com.gleisonti.githubusers.di

import com.gleisonti.githubusers.model.apis.ApiGitHubService
import com.gleisonti.githubusers.helper.Constants
import com.gleisonti.githubusers.model.repository.GitHubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofitInstance(BASE_URL: String): ApiGitHubService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiGitHubService::class.java)

    @Provides
    @Singleton
    fun provideRepGitRepositoy( apiGitHubService: ApiGitHubService) : GitHubRepository =
        GitHubRepository(apiGitHubService)
}