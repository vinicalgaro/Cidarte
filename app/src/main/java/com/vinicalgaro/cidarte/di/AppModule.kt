package com.vinicalgaro.cidarte.di

import com.vinicalgaro.cidarte.BuildConfig
import com.vinicalgaro.cidarte.data.local.dao.MovieDao
import com.vinicalgaro.cidarte.data.remote.api.TmdbApi
import com.vinicalgaro.cidarte.data.repository.MovieRepositoryImpl
import com.vinicalgaro.cidarte.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ProxySelector
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val API_KEY = BuildConfig.TMDB_API_KEY
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY_STRING = "api_key"

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val apiKeyInterceptor = Interceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter(API_KEY_STRING, API_KEY)
                .addQueryParameter("language", "pt-BR")
                .build()

            val requestBuilder = original.newBuilder().url(url)
            val request = requestBuilder.build()
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .proxySelector(ProxySelector.getDefault())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideTmdbApi(retrofit: Retrofit): TmdbApi = retrofit.create(TmdbApi::class.java)

    @Provides
    @Singleton
    fun provideMovieRepository(api: TmdbApi, dao: MovieDao): MovieRepository =
        MovieRepositoryImpl(api, dao)
}