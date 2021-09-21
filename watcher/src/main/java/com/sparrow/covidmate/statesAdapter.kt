package com.sparrow.covidmate

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class statesAdapter(val context : Context,val items : ArrayList<String>) : RecyclerView.Adapter<statesAdapter.ViewHolder>() {
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val tv_stateName = view.findViewById<TextView>(R.id.tv_stateName)
        val ll_row_state_name = view.findViewById<LinearLayout>(R.id.ll_row_state_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.row_state_name,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_stateName.text = items[position]
        holder.ll_row_state_name.setOnClickListener {
            val intent = Intent(context,districtScreenActivity::class.java)
            intent.putExtra("state_name",items[position])
            context.startActivity(intent)
        }
    }

}