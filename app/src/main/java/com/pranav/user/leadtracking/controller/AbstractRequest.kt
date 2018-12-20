package com.pranav.user.leadtracking.controller

import android.content.Context
import android.util.Log
import com.pranav.user.leadtracking.controller.responce.ErrorMessageResponce
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

abstract class AbstractRequest<T>(protected var context: Context, private val responcehandler: ProcessResponcceInterphase<T>?) : Callback<T> {
    protected var leadTrackingInterphase: LeadTrackingInterphase
    private var base_url = "http://138.197.32.34/lead_tracking/public/"
    val token_ = "Bearer " + context.getSharedPreferences("Token_", Context.MODE_PRIVATE).getString("TOKEN", "")
    val json_ = "application/json"
    lateinit var respons: Response<ErrorMessageResponce>

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val retrofit = Retrofit.Builder()
                .baseUrl(base_url)
                .client(OkHttpClient.Builder().addNetworkInterceptor(loggingInterceptor)
                        .connectTimeout(300, TimeUnit.SECONDS)
                        .readTimeout(300, TimeUnit.SECONDS)
                        .build())
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
        leadTrackingInterphase = retrofit.create(LeadTrackingInterphase::class.java)
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            Log.e("response", "  ok")
            processResponse(response.body())
        } else {
            Log.e("response", "  ok alla")
            responcehandler!!.processResponceError(response.body())
//            responceHandler?.processResponceError(response.body())
        }
        Log.e("response", response.body().toString())
    }

    override fun onFailure(call: Call<T>, t: Throwable?) {
        Log.e("response ", t!!.message + "  poe")
        processResponse(null)
    }

    private fun processResponse(response: T?) {
        if (response != null) {
            responcehandler?.processResponce(response)
        }
    }
}