package com.atlys.utils

sealed class Resource<out T> {
    data object Initial : Resource<Nothing>()
    data object Loading : Resource<Nothing>()

    data class Success<out R>(
        val data: R ? ,            // Generic data
        val message:String? = null,
        val error:Boolean? = false
    ) : Resource<R>()

    data class Error(val message: String?) : Resource<Nothing>()
}
