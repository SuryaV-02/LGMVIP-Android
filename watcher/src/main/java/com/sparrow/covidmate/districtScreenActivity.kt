package com.sparrow.covidmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sparrow.covidmate.MainActivity.Companion.districtDetailsList
import org.json.JSONException
import org.json.JSONObject

class districtScreenActivity : AppCompatActivity() {
    private var mQueue: RequestQueue? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_district_screen)
        setStateList()
    }

    private fun setupDistrictRecyclerView() {
        val district_details_list = districtDetailsList
        val rv_districtsName = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_districtsName)
        val districtAdapter = districtAdapter(this,district_details_list!!)
        rv_districtsName.adapter = districtAdapter
    }

    override fun onBackPressed() {
        districtDetailsList?.clear()
        super.onBackPressed()
    }

    private fun setStateList() {
        mQueue = Volley.newRequestQueue(this)
        val state_name = intent.getStringExtra("state_name")
        MainActivity.stateNameList = ArrayList()
        Log.i("SKHST_4894" , "inside1")
        val url = "https://data.covid19india.org/state_district_wise.json" //"https://data.covid19india.org/v4/min/data.min.json" //  "https://api.myjson.com/bins/kp9wz"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                Log.i("SKHST_4894" , "inside2")
                try {
                    val jsonObj = JSONObject(response.toString().trim())
                    val result_state_data = jsonObj.getJSONObject(state_name)
                    val jsonObj_districts = result_state_data.getJSONObject("districtData")
                    val Keys_district_name : Iterator<String> = jsonObj_districts.keys()
                    Log.i("Skhst_23456","state $Keys_district_name")
                    Log.i("Skhst_23456","dist $jsonObj_districts")
                    while(Keys_district_name.hasNext()){
                        val district_name = Keys_district_name.next()
                        val result_district_data = jsonObj_districts.getJSONObject(district_name)
                        Log.i("SKHST_78961320","$district_name")
                        Log.i("SKHST_78961320","$result_district_data")
                        val temp = covidData(
                            district_name,
                            result_district_data.getString("confirmed").toLong(),
                            result_district_data.getString("active").toLong(),
                            result_district_data.getString("recovered").toLong(),
                            result_district_data.getString("deceased").toLong()
                        )
                        districtDetailsList?.add(temp)
                    }
                    setupDistrictRecyclerView()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }) { error -> error.printStackTrace() }
        mQueue!!.add(request)
    }
}