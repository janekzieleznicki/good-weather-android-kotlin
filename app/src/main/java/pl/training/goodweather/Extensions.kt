package pl.training.goodweather

import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.SelectQueryBuilder
import pl.training.goodweather.model.City
import pl.training.goodweather.model.Forecast
import pl.training.goodweather.model.api.ForecastResultTo
import pl.training.goodweather.model.api.ForecastTo
import pl.training.goodweather.model.database.CityDb
import pl.training.goodweather.model.database.ForecastDb
import java.text.DateFormat

fun Long.formatDate(dateFormat: Int = DateFormat.MEDIUM): String {
    return DateFormat.getDateInstance(dateFormat).format(this)
}

fun SQLiteDatabase.clean(table: String, where: String) {
    execSQL("delete from $table where $where")
}

fun <K, V> MutableMap<K, V?>.toPairArray() : Array<Pair<K, V>> = map { Pair(it.key, it.value!!) }.toTypedArray()

fun <T: Any> SelectQueryBuilder.parseList(parser: (Map<String, Any?>) -> T): List<T> =
    parseList(object : MapRowParser<T> {
        override fun parseRow(columns: Map<String, Any?>): T = parser(columns)
    })

fun <T : Any> SelectQueryBuilder.parseOptional(parser: (Map<String, Any?>) -> T): T? =
    parseOpt(object : MapRowParser<T> {
        override fun parseRow(columns: Map<String, Any?> ): T = parser(columns)
    })

fun ForecastResultTo.toDomainModel() = City(city.id, city.name, city.country, list.map { it.toDomainModel() })

fun ForecastTo.toDomainModel()= Forecast(-1, dt * 1000, weather[0].description, temp.max.toInt(), temp.min.toInt(),
    "https://openweathermap.org/img/w/${weather[0].icon}.png")

fun City.toDbModel() = CityDb(providerID, name, country, forecastList.map { it.toDbModel(providerID) })

fun Forecast.toDbModel(cityId: Long) = ForecastDb(date, description, high, low, iconUrl, cityId)

fun CityDb.toDomainModel() = City(providerID, city, country, forecastList.map { it.toDomainModel() })

fun ForecastDb.toDomainModel() = Forecast(_id, date, description, high, low, iconUrl)