package fr.epita.hellogames

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SecondActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val data = arrayListOf<Game>()
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()
        val service: Webservice = retrofit.create(Webservice::class.java)
        val callback = object : Callback<Gamedetails> {
            override fun onFailure(call: Call<Gamedetails>?, t: Throwable?) {
                Log.d("TAG", "WebService call failed")
            }
            override fun onResponse(call: Call<Gamedetails>?,
                                    response: Response<Gamedetails>?) {
                if (response != null) {
                    if (response.code() == 200) {
                        val responseData = response.body()
                        if (responseData != null) {

                            Log.d("TAG", "WebService success : " + data.size)
                        }
                    }
                }
            }
        }
        val originIntent = intent // extract data from the intent
        val message = originIntent.getIntExtra("GAMEID", 0)
        service.details(message).enqueue(callback)
    }
}