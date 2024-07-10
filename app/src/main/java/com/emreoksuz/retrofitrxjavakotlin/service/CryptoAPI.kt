package com.emreoksuz.retrofitrxjavakotlin.service

import com.emreoksuz.retrofitrxjavakotlin.model.CryptoModel
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {
    
    @GET("ENTER EXTEND")
    fun getData():Call<List<CryptoModel>>

}