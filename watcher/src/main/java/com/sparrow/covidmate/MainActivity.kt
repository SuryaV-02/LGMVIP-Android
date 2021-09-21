package com.sparrow.covidmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request

import org.json.JSONException

import org.json.JSONObject

import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import org.json.JSONTokener


class MainActivity : AppCompatActivity() {
    companion object{
        var countryDataList : ArrayList<String>? = null
        var stateNameList : ArrayList<String>? = null
        var districtDetailsList : ArrayList<covidData>? = null
    }
    private var mQueue: RequestQueue? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        districtDetailsList = ArrayList()
//        Handler().postDelayed(
//            {
//                val intent = Intent(this,HomeScreenActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(intent)
//            },2000
//        )
        setDistrictInfo()

    }

    private fun setDistrictInfo() {
        countryDataList = ArrayList()
        mQueue = Volley.newRequestQueue(this)
        val url = "https://corona.lmao.ninja/v2/countries/India?yesterday=true&strict=true&query"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,url,null,
            {response->
                try {
                    val rawJasonObject = JSONTokener(response.toString()).nextValue() as JSONObject
                    val totalCases = rawJasonObject.getString("cases")
                    val todayCases = rawJasonObject.getString("todayCases")
                    val totalDeaths = rawJasonObject.getString("deaths")
                    val todayDeaths = rawJasonObject.getString("todayDeaths")
                    val recovered = rawJasonObject.getString("recovered")
                    val todayRecovered = rawJasonObject.getString("todayRecovered")
                    val active = rawJasonObject.getString("active")

                    countryDataList!!.add(totalCases)
                    countryDataList!!.add(totalDeaths)
                    countryDataList!!.add(recovered)
                    countryDataList!!.add(active)

                    setStateList()
                }catch (e : Exception){
                    e.printStackTrace()
                }
            },
            { error ->

            })
        mQueue!!.add(jsonObjectRequest)
    }

    private fun setStateList() {
        stateNameList = ArrayList()
        districtDetailsList = ArrayList()
        Log.i("SKHST_4894" , "inside1")
        val url = "https://data.covid19india.org/state_district_wise.json" //"https://data.covid19india.org/v4/min/data.min.json" //  "https://api.myjson.com/bins/kp9wz"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                Log.i("SKHST_4894" , "inside2")
                try {
                    val jsonObj = JSONObject(response.toString().trim())
                    val Keys_state_name : Iterator<String> = jsonObj.keys()
                    while (Keys_state_name.hasNext()){
                        val state_name = Keys_state_name.next()
//                        val result_state_data = jsonObj.getJSONObject(state_name)
//                        val jsonObj_districts = result_state_data.getJSONObject("districtData")
//                        val Keys_district_name : Iterator<String> = jsonObj_districts.keys()
//                        Log.i("Skhst_23456","state $Keys_district_name")
//                        Log.i("Skhst_23456","dist $jsonObj_districts")
//                        while(Keys_district_name.hasNext()){
//                            val district_name = Keys_district_name.next()
//                            val result_district_data = jsonObj_districts.getJSONObject(district_name)
//                            Log.i("SKHST_78961320","$district_name")
//                            Log.i("SKHST_78961320","$result_district_data")
//                            val temp = covidData(
//                                district_name,
//                                result_district_data.getString("confirmed").toLong(),
//                                result_district_data.getString("active").toLong(),
//                                result_district_data.getString("recovered").toLong(),
//                                result_district_data.getString("deceased").toLong()
//                            )
//                            districtDetailsList?.add(temp)
//                        }
                        Log.i("SKHST_12569","$state_name")
                        stateNameList!!.add(state_name)
                    }
                    for(each in districtDetailsList!!){
                        Log.i("SKHST_12479","${each.districtName}")
                    }
                    Log.i("SKHST_12479","${districtDetailsList!!.size}")
//                    Toast.makeText(this, "${districtDetailsList!!.size}", Toast.LENGTH_SHORT).show()
                    
                    val intent = Intent(this,HomeScreenActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }) { error -> error.printStackTrace() }
        mQueue!!.add(request)
    }
}