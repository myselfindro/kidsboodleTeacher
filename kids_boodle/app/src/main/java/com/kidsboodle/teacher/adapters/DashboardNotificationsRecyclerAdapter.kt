package com.kidsboodle.teacher.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.kidsboodle.teacher.data.model.homefragment.DashBoardNotificationItem
import com.kidsboodle.teacher.databinding.ItemRowDashboardNotificationBinding


class DashboardNotificationsRecyclerAdapter(val context: Context, val data: List<DashBoardNotificationItem>, val navController: NavController) : RecyclerView.Adapter<DashboardNotificationsRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRowDashboardNotificationBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: ItemRowDashboardNotificationBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int){
            val item=data[position]

            binding.tvDateAndDay.text=item.dateAndDay
            binding.tvNotificationText.text=item.notificationMessage

        }
    }

}