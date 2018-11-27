package pl.training.goodweather.model.database

import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import pl.training.goodweather.*
import pl.training.goodweather.model.City
import java.util.HashMap

class ForecastRepository(private val dbHelper: ManagedSQLiteOpenHelper) {
    fun save(city: City) = dbHelper.use {
        clean(CityTable.name, "${CityTable.Columns.providerID} = ${city.providerID}")
        clean(ForecastTable.name, "${ForecastTable.Columns.cityId} = ${city.providerID}")

        with(city.toDbModel()){
            insert(CityTable.name, *this.map.toPairArray() )

            forecastList.forEach {
                insert(
                    ForecastTable.name, *it.map.toPairArray()
                )
            }
        }
    }

    fun getForecastByCityName(cityName: String) : City? = dbHelper.use {
        val cityDb = select(CityTable.name)
            .whereSimple("${CityTable.Columns.city} = ?", cityName)
            .parseOptional {
                CityDb(HashMap(it)) }
        cityDb?.let {
            it.forecastList=getForecastListForCity(cityDb.providerID)
            it.toDomainModel()
        }
    }

    private fun getForecastListForCity(cityId: Long) = dbHelper.use {
        select(ForecastTable.name)
            .whereSimple("${ForecastTable.Columns.cityId} = ?", cityId.toString())
            .parseList{
                ForecastDb(HashMap(it))
            }
    }

    fun getLast() : City? = dbHelper.use {
        val cityName = select(CityTable.name, "MAX(${CityTable.Columns.id}) as maxID", "${CityTable.Columns.city}").exec {
            moveToNext()
            return@exec getString(getColumnIndex("${CityTable.Columns.city}"))
        }
        cityName?.let { getForecastByCityName(it) }
    }
}