package com.atlys.base


import com.atlys.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response

abstract class BaseRepository {


    protected fun <T> getRequest(call: suspend () -> Response<T>): Flow<Resource<T>> = flow {
        emit(Resource.Loading) // Emit loading state

        val response = call()
        if (response.isSuccessful && response.code() == 200) {
            response.body()?.let {
                emit(Resource.Success(it))
            } ?: emit(Resource.Error("Empty data"))
        } else {
            emit(Resource.Error("API Error: ${response.message()}"))
        }
    }.catch { emit(Resource.Error("Empty data")) }
}
