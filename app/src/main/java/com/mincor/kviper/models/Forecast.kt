package com.mincor.kviper.models

data class Forecast (val dt:Long? = null, val main:Temperature? = null,
                     val weather:List<Weather>? = null, val clouds:Clouds? = null,
                     val wind:Wind? = null, val sys:Sys? = null,val dt_txt:String? = null)

/*
{
        "dt":1406106000,
        "main":{
            "temp":298.77,
            "temp_min":298.77,
            "temp_max":298.774,
            "pressure":1005.93,
            "sea_level":1018.18,
            "grnd_level":1005.93,
            "humidity":87,
            "temp_kf":0.26},
        "weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}],
        "clouds":{"all":88},
        "wind":{"speed":5.71,"deg":229.501},
        "sys":{"pod":"d"},
        "dt_txt":"2014-07-23 09:00:00"}
        ]}
 */