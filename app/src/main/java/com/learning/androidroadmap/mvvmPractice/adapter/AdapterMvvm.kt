package com.learning.androidroadmap.mvvmPractice.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learning.androidroadmap.R
import com.learning.androidroadmap.mvvmPractice.data_class.PostsData

class AdapterMvvm : RecyclerView.Adapter<DataHolder>()  {
    private var dataToDisplay = listOf<PostsData>()

    fun collectData(data : List<PostsData>) {
        dataToDisplay = data
        Log.d("showData","$dataToDisplay")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val containerView = LayoutInflater.from(parent.context).inflate(R.layout.content_row_layout,
            parent, false)
        return DataHolder(containerView)
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        val contentData = dataToDisplay[position]
        val title = contentData.title
        val body = contentData.body
        holder.titleText.text = title
        holder.bodyText.text = body
    }

    override fun getItemCount(): Int {
        return dataToDisplay.size
    }
}