package fr.epita.hellogames

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Webservice {
    @GET("game/list")
    fun listToDos():Call<List<Game>>

    @GET("game/details")
    fun details(@Query("game_id") game_id: Int): Call<Gamedetails>
}