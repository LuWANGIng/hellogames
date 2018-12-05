package fr.epita.hellogames

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var selection = mutableListOf<Game>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = arrayListOf<Game>()
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
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
                            selection=selectRand(data)
                            button_0.text = selection[0].name
                        button_1.text = selection[1].name
                        button_2.text = selection[2].name
                        button_3.text = selection[3].name
                    }
                    }
                }
            }
        }
        service.listToDos().enqueue(callback)
        button_0.setOnClickListener(this@MainActivity)
        button_1.setOnClickListener(this@MainActivity)
        button_2.setOnClickListener(this@MainActivity)
        button_3.setOnClickListener(this@MainActivity)
    }

    override fun onClick(v: View?) {
        var gameid = 0
        if (v != null) {
            when (v.id){
                R.id.button_0 -> {
                    gameid=selection[0].id
                }
                R.id.button_1-> {
                    gameid=selection[1].id
                }
                R.id.button_2 -> {
                    gameid=selection[2].id
                }
                R.id.button_3 -> {
                    gameid=selection[3].id
                }
            }
        }
        val explicitIntent = Intent(this, SecondActivity::class.java) // Insert extra data in the intent
        explicitIntent.putExtra("GAMEID", gameid) // Start the other activity by sending the intent
        startActivity(explicitIntent)
    }

    private fun selectRand(data: ArrayList<Game>): MutableList<Game> {
        val list = arrayListOf<Game>()
        for (i in 0..3){
            val game = data.removeAt(Random.nextInt(0,data.size))
            list.add(game)
        }
        return list
    }
}
