package com.example.elacosmetologyandroid.repository.network.di

import android.content.Context
import com.example.elacosmetologyandroid.BuildConfig
import com.example.elacosmetologyandroid.repository.local.preferences.manager.PreferencesManager
import com.example.elacosmetologyandroid.repository.local.preferences.utils.PREFERENCE_TOKEN
import com.example.elacosmetologyandroid.repository.network.ServiceApi
import com.example.elacosmetologyandroid.repository.network.api.AppNetwork
import com.example.elacosmetologyandroid.repository.network.api.AuthNetwork
import com.example.elacosmetologyandroid.repository.network.api.ProductNetwork
import com.example.elacosmetologyandroid.repository.network.utils.*
import com.example.elacosmetologyandroid.usecases.repository.IAppRepositoryNetwork
import com.example.elacosmetologyandroid.usecases.repository.IAuthRepositoryNetwork
import com.example.elacosmetologyandroid.usecases.repository.IProductRepositoryNetwork
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {
    single { providerHttpLoggingInterceptor() }
    single { providerCache(get()) }
    single { ApiInterceptor(get(),get()) }
    single { providerOkHttpClient(get(), get(),get()) }
    single { providerRetrofit(getProperty(NAME_BASE_URL), get()) }
    single { providerApi(get()) }
    single<IAuthRepositoryNetwork> { AuthNetwork() }
    single<IAppRepositoryNetwork> { AppNetwork() }
    single<IProductRepositoryNetwork> { ProductNetwork() }

}

fun providerApi(retrofit: Retrofit): ServiceApi {
    return retrofit.create(ServiceApi::class.java)
}

fun providerRetrofit(baseUrl: String, client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .baseUrl(baseUrl)
        .build()
}

fun providerOkHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    apiInterceptor: ApiInterceptor,context: Context
): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(ChuckInterceptor(context))
        .addInterceptor(apiInterceptor)
        .build()
}

fun providerCache(context: Context): Cache {
    val cacheSize: Long = 10485760
    return Cache(context.cacheDir, cacheSize)
}

fun providerHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    return logging
}

class ApiInterceptor(private val context: Context,private val preferencesManager: PreferencesManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val builder = request.newBuilder()
            .addHeader("Content-Type", CONTENT_TYPE)
            //.header("x-os", PLATFORM)
            .addHeader("x-density", getDensity(context).toString())
            .addHeader("x-width", getWidth(context).toString())
            .addHeader("x-height", getHeight(context).toString())
            if (preferencesManager.getString(PREFERENCE_TOKEN).isNotEmpty()) {
               builder.addHeader(AUTHORIZATION, preferencesManager.getString(PREFERENCE_TOKEN))
             }
        request = builder.build()
        return chain.proceed(request)
    }
}

