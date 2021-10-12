package com.example.postrequest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val userInfo: ArrayList<ArrayList<String>>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.user_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.tv_pk.text = "ID: ${userInfo[position][0]}"
        holder.tv_name.text = "Name: ${userInfo[position][1]}"
        holder.tv_location.text = "Location: ${userInfo[position][2]}"
    }

    override fun getItemCount() = userInfo.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tv_pk: TextView
        var tv_name: TextView
        var tv_location: TextView

        init {
            tv_pk = itemView.findViewById(R.id.tv_pk)
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_location = itemView.findViewById(R.id.tv_location)
        }
    }


}