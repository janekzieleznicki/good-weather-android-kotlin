package pl.training.goodweather.di

import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.training.goodweather.model.Forecast
import pl.training.goodweather.model.api.WeatherProvider
import pl.training.goodweather.model.WeatherService
import pl.training.goodweather.model.database.DatabaseHelper
import pl.training.goodweather.model.database.ForecastRepository
import pl.training.goodweather.model.events.EventBus
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ModelModule(private val context: Context) {

    @Singleton
    @Provides
    fun httpClient() : OkHttpClient{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient().newBuilder()
            .addInterceptor(logging)
            .build()
    }

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

    @Singleton
    @Provides
    fun weatherService(weatherProvider: WeatherProvider, forecastRepository: ForecastRepository) = WeatherService(weatherProvider,forecastRepository)

    @Singleton
    @Provides
    fun databaseHelper() = DatabaseHelper(context)

    @Singleton
    @Provides
    fun forecastRepository(databaseHelper: DatabaseHelper) = ForecastRepository(databaseHelper)

    @Singleton
    @Provides
    fun eventBus() = EventBus<Forecast>()
}