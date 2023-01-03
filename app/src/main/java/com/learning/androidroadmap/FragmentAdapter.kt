package com.learning.androidroadmap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FragmentAdapter(val nameList: List<Employee>, val callBack : (Employee, position : Int)-> Unit) : RecyclerView.Adapter<FragmentAdapter.NameHolder>() {
    class NameHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {
            val titleText : TextView = containerView.findViewById(R.id.NameEt)
            val subtitleText : TextView = containerView.findViewById(R.id.UidEt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameHolder {
        val containerView = LayoutInflater.from(parent.context).inflate(R.layout.item_row_updated, parent, false)
        return NameHolder(containerView)
    }

    override fun onBindViewHolder(holder: NameHolder, position: Int) {
        val comboData = nameList[position]


        val name = comboData.fullName
        val uid = comboData.uid
        holder.titleText.text = name
        holder.subtitleText.text = "UID - $uid"

        val cardview = holder.itemView

        cardview.setOnClickListener {
            callBack(comboData, position)
        }
    }

    override fun getItemCount(): Int {
        return nameList.size
    }
}