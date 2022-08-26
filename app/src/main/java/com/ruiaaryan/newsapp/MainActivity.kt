package com.ruiaaryan.newsapp

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var items = mutableListOf<Article?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        fetchData("")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val search = findViewById<EditText>(R.id.search)
        search.setOnEditorActionListener{
                _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                if(search.text.toString() != "") {
                    fetchData(search.text.toString())
                }
                else {
                    search.hint = "Enter search query"
                    search.setHintTextColor(Color.RED)
                }
            }
            false
        }
    }

    private fun insertData(it: MutableList<Article?>) {
        if(it.size != 0) {
        items = it
        val adapter = NewsAdapter(items, this)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                baseContext,
                LinearLayoutManager(this).orientation
            )
        )
        println("Loaded in Recycler View")
    }
        else {
            Toast.makeText(this@MainActivity, "Sorry!! No Results", Toast.LENGTH_LONG).show()
        }
    }

    private fun fetchData(q:String) {
        val apikey = "dc9e197b010149908560f8bbf94f2746"
        q.replace(' ', '+', false)
        val country = "in"
        println(q)
        val client = APIService.APIObject.retrofitService.getTopHeadlines(country,q,apikey)
        client.enqueue(object : Callback<APIResponse> {
            override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
                if (response.isSuccessful) {
                    println("RESPONSE SUCCESSFUL")
                    insertData(response.body()!!.articles!!)
                } else {
                    Toast.makeText(this@MainActivity, "Sorry!!", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Please check your internet connection",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun onClicked(pos: Int) {
        val link = items[pos]?.url
        Toast.makeText(this@MainActivity, "OPENING : $link", Toast.LENGTH_LONG).show()
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(link))
    }

    fun share(pos: Int) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Hey Checkout this ${items[pos]?.url}")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}