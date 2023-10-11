package com.dicoding.submiss2.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.submiss2.data.local.database.FavoriteUser
import com.dicoding.submiss2.data.local.database.FavoriteUserDao
import com.dicoding.submiss2.data.local.database.UserDatabase
import com.dicoding.submiss2.data.retrofit.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import com.dicoding.submiss2.utils.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    val resultDetailUser = MutableLiveData<Result>()
    val resultFollowersUser = MutableLiveData<Result>()
    val resultFollowingUser = MutableLiveData<Result>()

    private var userDao: FavoriteUserDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao= userDb?.favoriteUserDao()
    }

    fun getDetailUser(username : String) {
        viewModelScope.launch {
            flow {
                val response = ApiConfig
                    .apiService
                    .getDetailUserGithub(username)
                emit(response)
            }.onStart {
                resultDetailUser.value = Result.Loading(true)
            }.onCompletion {
                resultDetailUser.value =Result.Loading(false)
            }.catch {
                it.printStackTrace()
                resultDetailUser.value = Result.Error(it)
            }.collect {
                resultDetailUser.value = Result.Success(it)
            }
        }
    }


    fun getFollowers(username : String){
        viewModelScope.launch {
            flow {
                val response = ApiConfig
                    .apiService
                    .getFollowersUserGithub(username)
                emit(response)
            }.onStart {
                resultFollowersUser.value = Result.Loading(true)
            }.onCompletion {
                resultFollowersUser.value =Result.Loading(false)
            }.catch {
                it.printStackTrace()
                resultFollowersUser.value =Result.Error(it)
            }.collect {
                resultFollowersUser.value =Result.Success(it)
            }
        }
    }

    fun getFollowing(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiConfig
                    .apiService
                    .getFollowingUserGithub(username)

                emit(response)
            }.onStart {
                resultFollowingUser.value = Result.Loading(true)
            }.onCompletion {
                resultFollowingUser.value = Result.Loading(false)
            }.catch {
                it.printStackTrace()
                resultFollowingUser.value = Result.Error(it)
            }.collect {
                resultFollowingUser.value = Result.Success(it)
            }
        }
    }

    fun addToFavorite(username: String, id:Int, avatarUrl:String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                username,
                id,
                avatarUrl
            )
            userDao?.addToFavorite(user)
        }
    }
    suspend fun checkUser(id: Int)= userDao?.checkUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}