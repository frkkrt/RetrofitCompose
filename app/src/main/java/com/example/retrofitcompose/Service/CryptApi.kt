package com.example.retrofitcompose.Service

import android.telecom.Call
import com.example.retrofitcompose.Model.CryptoModel
import retrofit2.http.GET

interface CryptApi {
    @GET("prices?key=292247011d37d421fed405ba83972d16")
    fun getData() : retrofit2.Call<List<CryptoModel>>
}