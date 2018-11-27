package pl.training.goodweather.model.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*


class DatabaseHelper(context: Context) : ManagedSQLiteOpenHelper(context, dbName, null, dbVersion) {
    companion object {
        const val dbName = "forecast.db"
        const val dbVersion = 2
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.let {
            it.createTable(CityTable.name,true,
                CityTable.Columns.id to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                CityTable.Columns.providerID to INTEGER,
                CityTable.Columns.city to TEXT,
                CityTable.Columns.country to TEXT)
            it.createTable(ForecastTable.name, true,
                ForecastTable.Columns.id to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                ForecastTable.Columns.date to INTEGER,
                ForecastTable.Columns.description to TEXT,
                ForecastTable.Columns.low to INTEGER,
                ForecastTable.Columns.high to INTEGER,
                ForecastTable.Columns.iconUrl to TEXT,
                ForecastTable.Columns.cityId to INTEGER)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.let {
            it.dropTable(CityTable.name, true)
            it.dropTable(ForecastTable.name, true)
            onCreate(db)
        }
    }
}