package com.learning.androidroadmap.mvvmPractice.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.learning.androidroadmap.R

class DataHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {
    val titleText : TextView = containerView.findViewById(R.id.titleTextView)
    val bodyText : TextView = containerView.findViewById(R.id.bodyTextView)
}