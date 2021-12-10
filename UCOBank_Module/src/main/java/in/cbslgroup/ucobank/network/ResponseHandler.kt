package com.roysoft.bigstepmovieapptest.network

import android.content.res.TypedArray
import android.util.Log
import com.roysoft.documentmanagementsystem.network.Resource
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1)
}

open class ResponseHandler {

    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.Success(data)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        Log.e("handleException1", e.toString())
        return when (e) {
            is HttpException -> {
                Log.e("handleException2", e.toString() + e.response()!!.errorBody())
                Resource.Error(getErrorMessage(e.code()), e.response()?.errorBody().toString() as T )
            }
            is SocketTimeoutException -> Resource.Error(
                getErrorMessage(ErrorCodes.SocketTimeOut.code),null)
            else -> {
                Log.e("handleException", e.toString())
                Resource.Error(getErrorMessage(Int.MAX_VALUE), null)
            }
        }
    }

    private fun getErrorMessage(code: Int): String {
        Log.e("handleException3", code.toString())
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> "Timeout"
            400 -> "HTTP 400 Bad Request"
            401 -> "Unauthorised"
            404 -> "Not found"
            else -> "Something went wrong"
        }
    }
}