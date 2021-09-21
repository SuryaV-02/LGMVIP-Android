package com.sparrow.covidmate

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sparrow.covidmate.MainActivity.Companion.countryDataList
import org.json.JSONObject
import org.json.JSONTokener

class HomeScreenActivity : AppCompatActivity() {
    private var mQueue: RequestQueue? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        val ll_state_wise_data = findViewById<LinearLayout>(R.id.ll_state_wise_data)
        ll_state_wise_data.setOnClickListener {
            val intent = Intent(this,stateScreenActivity::class.java)
            startActivity(intent)
        }
        val ll_who = findViewById<LinearLayout>(R.id.ll_who)
        ll_who.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.who.int/emergencies/diseases/novel-coronavirus-2019/advice-for-public"))
            startActivity(intent)
        }
        setCountryDetails()
    }

    fun setCountryDetails(){
        val countryData = countryDataList
        val tv_confirmed_cases = findViewById<TextView>(R.id.tv_confirmed_cases)
        val tv_active_cases = findViewById<TextView>(R.id.tv_active_cases)
        val tv_recovered_cases = findViewById<TextView>(R.id.tv_recovered_cases)
        val tv_deceased_cases = findViewById<TextView>(R.id.tv_deceased_cases)


        tv_confirmed_cases.text = countryData?.get(0)
        tv_active_cases.text = countryData?.get(1)
        tv_recovered_cases.text = countryData?.get(2)
        tv_deceased_cases.text = countryData?.get(3)
    }
}