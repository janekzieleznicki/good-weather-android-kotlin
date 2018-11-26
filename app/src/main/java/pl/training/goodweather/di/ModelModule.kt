package pl.training.goodweather.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import pl.training.goodweather.model.WeatherProvider
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ModelModule {

    @Singleton
    @Provides
    fun htttpClient() = OkHttpClient()

    @Singleton
    @Provides
    fun retrofit(httpClient: OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Singleton
    @Provides
    fun weatherProvider(retrofit: Retrofit) : WeatherProvider = retrofit.create(WeatherProvider::class.java)

}
