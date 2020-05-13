package com.abstractchile.clase10

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abstractchile.clase10.configuration.API_KEY
import com.abstractchile.clase10.model.Cat
import com.abstractchile.clase10.networking.CatApi
import com.abstractchile.clase10.networking.CatService
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val request = CatService.buildService(CatApi::class.java)

        get_button.setOnClickListener {
            val call = request.getCats(API_KEY)
            call.enqueue(object : Callback<List<Cat>> {
                override fun onResponse(call: Call<List<Cat>>, response: Response<List<Cat>>) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            result_text_view.text = response.toString() + "\n" + response.body()!!
                                .map { " " + it.id + "\n" }
                        }
                    }
                }

                override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        get_image_button.setOnClickListener {
            val call = request.getImage(API_KEY, edit_text.text.toString())
            call.enqueue(object : Callback<JsonArray> {
                override fun onResponse(
                    call: Call<JsonArray>,
                    response: Response<JsonArray>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            result_text_view.text = response.body().toString()

                        }
                    }
                }

                override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })

            post_button.setOnClickListener {
                val vote = JsonObject()
                vote.addProperty("image_id", "test")
                vote.addProperty("sub_id", "test")
                vote.addProperty("value", 1)
                val call = request.postVote(vote.toString(), API_KEY)
                call.enqueue(object : Callback<JsonObject> {

                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>
                    ) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                result_text_view.text = response.body().toString()
                            }
                        }
                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })

            }

        }
    }
}

