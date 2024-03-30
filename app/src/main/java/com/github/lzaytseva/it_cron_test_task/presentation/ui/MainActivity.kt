package com.github.lzaytseva.it_cron_test_task.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.lzaytseva.it_cron_test_task.R
import com.github.lzaytseva.it_cron_test_task.data.network.api.GithubApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val apiService = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create<GithubApiService>()

        val disposable = apiService.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    val headers = response.headers()
                    val link = headers["link"]
                    Log.d("RESPONSE", link.toString())
                    Log.d("RESPONSE", link?.contains("next").toString())
                    link?.substringAfter('<')?.let { Log.d("RESPONSE", it.substringBefore('>')) }
                    val body = response.body()
                    Log.d("RESPONSE", body.toString())
                    val code = response.code()
                    Log.d("RESPONSE", code.toString())
                },
                { it ->
                    Log.d("RESPONSE", it.message.toString())
                }
            )
    }
}