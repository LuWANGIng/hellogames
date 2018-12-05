package fr.epita.hellogames

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = arrayListOf<Game>()
        val baseURL = "http://jsonplaceholder.typicode.com/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()
        val service: Webservice = retrofit.create(Webservice::class.java)
        val callback = object : Callback<List<Game>> {
            override fun onFailure(call: Call<List<Game>>?, t: Throwable?) {
                Log.d("TAG", "WebService call failed")
            }
            override fun onResponse(call: Call<List<Game>>?,
                                    response: Response<List<Game>>?) {
                if (response != null) {
                    if (response.code() == 200) {
                        val responseData = response.body()
                        if (responseData != null) {
                            data.addAll(responseData)
                            Log.d("TAG", "WebService success : " + data.size)
                        }
                    }
                }
            }
        }
        //service.listToDos(2).enqueue(callback)


    }
}
