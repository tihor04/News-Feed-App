package com.example.newsfeed
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity(), NewsItemClicked {
    private  lateinit var  madpater :newsAdapter
    val TAG = "logging"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recylcerview:RecyclerView=findViewById(R.id.recyclerview)
        recylcerview.layoutManager= LinearLayoutManager(this)
        Log.d(TAG,"fetch function called")
        fectchdata()
        madpater= newsAdapter(this )
        recylcerview.adapter=madpater
    }

    private fun fectchdata(){
        val url = "https://newsdata.io/api/1/news?apikey=pub_49035d43244d2fea2ee288504e37735c39d2&country=in&language=en&category=top "
        val JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                Log.d(TAG,"the api works fine")
             val newsJsonArray =it.getJSONArray("results")
                val newsarray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()){
                  val newsJsonObject= newsJsonArray.getJSONObject(i)
                    val news =News(
                        newsJsonObject.getString("title"),
                         newsJsonObject.getString("pubDate"),
                        newsJsonObject.getString("link"),
                        newsJsonObject.getString("image_url")

                    )
                    newsarray.add(news)
                }
                madpater.updatenews(newsarray)
            },
            {
                Log.d(TAG,"api Doesnt work")
            }

        )
        MySingleton.getInstance(this,).addToRequestQueue(JsonObjectRequest)
    }



    override fun onItemClicked(Item: News) {
      val url = Item.url
       val builder =CustomTabsIntent.Builder()
        val customTabsIntent=builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
    }
}