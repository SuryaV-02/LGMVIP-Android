package com.sparrow.covidmate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class districtAdapter(val context : Context, val items : ArrayList<covidData>) : RecyclerView.Adapter<districtAdapter.ViewHolder>() {
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val tv_districtName = view.findViewById<TextView>(R.id.tv_districtName)
        val tv_dist_confirmed = view.findViewById<TextView>(R.id.tv_dist_confirmed)
        val tv_dist_active = view.findViewById<TextView>(R.id.tv_dist_active)
        val tv_recovered_active = view.findViewById<TextView>(R.id.tv_dist_recovered)
        val tv_deceased_active = view.findViewById<TextView>(R.id.tv_dist_deceased)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.row_district_name,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_districtName.text = items[position].districtName
        holder.tv_dist_active.text = items[position].active.toString()
        holder.tv_dist_confirmed.text = items[position].confirmed.toString()
        holder.tv_recovered_active.text = items[position].recovered.toString()
        holder.tv_deceased_active.text = items[position].recovered.toString()
    }
}