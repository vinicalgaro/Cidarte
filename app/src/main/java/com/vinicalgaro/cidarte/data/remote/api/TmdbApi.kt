package com.vinicalgaro.cidarte.data.remote.api

import com.vinicalgaro.cidarte.data.remote.dto.MovieDto
import com.vinicalgaro.cidarte.data.remote.dto.TmdbListResponse
import retrofit2.http.GET

interface TmdbApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(): TmdbListResponse<MovieDto>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): TmdbListResponse<MovieDto>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): TmdbListResponse<MovieDto>

    @GET("movie/upcoming")
    suspend fun getEmBreveMovies(): TmdbListResponse<MovieDto>
}