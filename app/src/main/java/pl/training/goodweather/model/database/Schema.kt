package pl.training.goodweather.model.database

object CityTable {
    val name = "City"
    object Columns {
        val id = "_id"
        val providerID = "providerID"
        val city = "city"
        val country = "country"
    }
}

object ForecastTable {
    val name = "Forecast"
    object Columns {
        val id = "_id"
        val date = "date"
        val description = "description"
        val low = "low"
        val high = "high"
        val iconUrl = "iconUrl"
        val cityId = "cityId"
    }
}