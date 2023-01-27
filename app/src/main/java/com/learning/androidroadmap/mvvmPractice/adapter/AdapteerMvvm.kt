package com.learning.androidroadmap.mvvmPractice.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.learning.androidroadmap.R
import com.learning.androidroadmap.mvvmPractice.data_class.UserData

class AdapteerMvvm(private val dataList: List<UserData>) : RecyclerView.Adapter<AdapteerMvvm.DataHolder>()  {
    class DataHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {
        val titleText : TextView = containerView.findViewById(R.id.titleTextView)
        val bodyText : TextView = containerView.findViewById(R.id.bodyTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val containerView = LayoutInflater.from(parent.context).inflate(R.layout.content_row_layout, parent, false)
        return DataHolder(containerView)
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        val contentData = dataList[position]
        val title = contentData.title
        val body = contentData.body
        holder.titleText.text = title
        holder.bodyText.text = body
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}