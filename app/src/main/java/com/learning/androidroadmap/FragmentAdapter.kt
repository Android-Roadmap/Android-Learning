package com.learning.androidroadmap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FragmentAdapter(private val nameList: MutableList<Employee>, val callBack : (Employee, position : Int, delete : Boolean)-> Unit) : RecyclerView.Adapter<FragmentAdapter.NameHolder>() {
    class NameHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {
            val titleText : TextView = containerView.findViewById(R.id.NameEt)
            val subtitleText : TextView = containerView.findViewById(R.id.UidEt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameHolder {
        val containerView = LayoutInflater.from(parent.context).inflate(R.layout.item_row_updated, parent, false)
        return NameHolder(containerView)
    }

    override fun onBindViewHolder(holder: NameHolder, position: Int) {
        val employeeData = nameList[position]
        val name = employeeData.fullName
        val uid = employeeData.uid
        holder.titleText.text = name
        holder.subtitleText.text = "UID : $uid"
        val cardView = holder.itemView
        cardView.setOnClickListener {
            callBack(employeeData, position, false)
        }
        cardView.setOnLongClickListener {
            val popupMenu = PopupMenu(it.context, it)
            popupMenu.inflate(R.menu.menu_long_press)
            popupMenu.setOnMenuItemClickListener{
                println("Will remove $name")
                nameList.removeAt(position)
                notifyItemRemoved(position)
                callBack(employeeData, position, true)
                true
            }
            popupMenu.show()

            true
        }
    }

    override fun getItemCount(): Int {
        return nameList.size
    }
}