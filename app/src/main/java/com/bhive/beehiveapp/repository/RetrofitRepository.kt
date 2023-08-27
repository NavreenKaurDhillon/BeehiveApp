package com.bhive.beehiveapp.repository

interface RetrofitRepository {
    fun onSuccess(message: String)
    fun onFailure(message: String)
    fun printMessage(message: String)
}