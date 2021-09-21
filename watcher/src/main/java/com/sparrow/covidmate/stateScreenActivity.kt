package com.sparrow.covidmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sparrow.covidmate.MainActivity.Companion.stateNameList
import org.json.JSONException
import org.json.JSONObject

class stateScreenActivity : AppCompatActivity() {
    private var mQueue: RequestQueue? = null
    var response_final  : JSONObject?  = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_screen)
        setupStateRecyclerView()
    }

    private fun setupStateRecyclerView() {
        val stateList = stateNameList
        val rv_statesName = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_statesName)
        val stateAdapter = statesAdapter(this,stateList!!)
        rv_statesName.adapter = stateAdapter
    }
}