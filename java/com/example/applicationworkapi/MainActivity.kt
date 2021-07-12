package com.example.applicationworkapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationworkapi.api.MyRetrofit
import com.example.applicationworkapi.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var recyclerProducts: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerProducts = findViewById(R.id.recycler_products)
        recyclerProducts.layoutManager = LinearLayoutManager(this)
        getData()
    }

    private fun getData(){

        val call: Call<List<Product>> =
            MyRetrofit.instace?.producttApi()?.getProductApi() as Call<List<Product>>
        call.enqueue(object : Callback<List<Product>>{
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                val adapter = response.body()?.let { ProductAdapter(this@MainActivity, it.toList()) }
                recyclerProducts.adapter = adapter
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}