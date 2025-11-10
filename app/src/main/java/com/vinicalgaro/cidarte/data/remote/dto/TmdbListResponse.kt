package com.vinicalgaro.cidarte.data.remote.dto

data class TmdbListResponse<T> (
    val results: List<T>
)