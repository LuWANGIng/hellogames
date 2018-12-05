package fr.epita.hellogames

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Webservice {
    @GET("game/list")
    fun listToDos(@Query("userId") userId :Int):Call<List<Game>>
}