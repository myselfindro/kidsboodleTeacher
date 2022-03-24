package com.kidsboodle.teacher.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.data.model.homefragment.DashBoardNoticeItem
import com.kidsboodle.teacher.data.model.homefragment.announcement.AnnouncementHistoryItem
import com.kidsboodle.teacher.data.model.homefragment.profile.ExamsItem
import com.kidsboodle.teacher.data.model.homefragment.profile.HobbiesItem
import com.kidsboodle.teacher.data.model.homefragment.students.CheckStudentItem
import com.kidsboodle.teacher.databinding.*

class HobbiesRecyclerAdapter(val context: Context, val data: List<HobbiesItem>, val navController: NavController) : RecyclerView.Adapter<HobbiesRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRowHobbiesProfileBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: ItemRowHobbiesProfileBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int){
            val item=data[position]

            binding.tvHobbyName.text=item.hobbyName

            val serialNumber=(position + 1).toString()+"."
            binding.tvSerialNumber.text = serialNumber

//            if(position == data.size - 1 ){
//                binding.itemView.visibility= View.INVISIBLE
//            }

        }
    }

}