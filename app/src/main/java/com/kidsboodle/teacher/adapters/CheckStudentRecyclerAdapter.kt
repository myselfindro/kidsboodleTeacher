package com.kidsboodle.teacher.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.data.model.homefragment.DashBoardNoticeItem
import com.kidsboodle.teacher.data.model.homefragment.announcement.AnnouncementHistoryItem
import com.kidsboodle.teacher.data.model.homefragment.students.CheckStudentItem
import com.kidsboodle.teacher.databinding.ItemRowAnnouncementHistoryBinding
import com.kidsboodle.teacher.databinding.ItemRowCheckStudentBinding
import com.kidsboodle.teacher.databinding.ItemRowNoticeBinding
import com.kidsboodle.teacher.ui.fragment.home.students.StudentCheckFragmentDirections

class CheckStudentRecyclerAdapter(val context: Context, val data: List<CheckStudentItem>, val navController: NavController) : RecyclerView.Adapter<CheckStudentRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRowCheckStudentBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: ItemRowCheckStudentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int){
            val item=data[position]

            if(position%2 != 0){
                binding.CLCheckStudent.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.light_light_violet)
            }
            else{
                binding.CLCheckStudent.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.white)
            }

            binding.tvName.text = item.name
            binding.tvRoll.text = item.roll

            binding.btnViewDetails.setOnClickListener {
                val lists=item.details
                val action=StudentCheckFragmentDirections.actionStudentCheckFragmentToProfileFragment(lists)
                navController.navigate(action)
            }

        }
    }

}