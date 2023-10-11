package com.dicoding.submiss2.data.retrofit


import com.dicoding.submiss2.BuildConfig
import com.dicoding.submiss2.data.response.ResponseDetail
import com.dicoding.submiss2.data.response.ResponseUser
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {

    @JvmSuppressWildcards
    @GET("users")
    suspend fun getUserGithub(
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<ResponseUser.Item>

    @JvmSuppressWildcards
    @GET("users/{username}")
    suspend fun getDetailUserGithub(
        @Path("username") username: String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): ResponseDetail

    @JvmSuppressWildcards
    @GET("/users/{username}/followers")
    suspend fun getFollowersUserGithub(
        @Path("username") username: String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<ResponseUser.Item>

    @JvmSuppressWildcards
    @GET("/users/{username}/following")
    suspend fun getFollowingUserGithub(
        @Path("username") username: String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<ResponseUser.Item>

    @JvmSuppressWildcards
    @GET("search/users")
    suspend fun searchUserGithub(
        @QueryMap params: Map<String, Any>,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): ResponseUser
}