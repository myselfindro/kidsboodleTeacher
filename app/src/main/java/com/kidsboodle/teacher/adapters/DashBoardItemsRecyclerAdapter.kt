package com.kidsboodle.teacher.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.data.model.homefragment.DashboardItems
import com.kidsboodle.teacher.databinding.ItemRowDashboardRoundedItemsBinding

class DashBoardItemsRecyclerAdapter(val context: Context, val data: List<DashboardItems>, val navController: NavController) : RecyclerView.Adapter<DashBoardItemsRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRowDashboardRoundedItemsBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: ItemRowDashboardRoundedItemsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int){
            val item=data[position]

            binding.RLCircle.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(item.backgroundColor))
//            binding.ivIcon.setImageDrawable(context.resources.getDrawable(item.iconDrawable))
            binding.ivIcon.setImageDrawable(context.resources.getDrawable(item.iconDrawable))
            binding.tvNameOfItem.text = item.optionName

            if(item.notificationCount!=null){
                binding.textCount.visibility= View.VISIBLE
                binding.textCount.text =item.notificationCount.toString()
            }

            if (item.optionName == "Announcements"){
                binding.LLCircle.setOnClickListener {
                    navController.navigate(R.id.action_navigation_home_to_announcementsFragment)
                }
            }

            if (item.optionName == "Student"){
                binding.LLCircle.setOnClickListener {
                    navController.navigate(R.id.action_navigation_home_to_studentsFragment)
                }
            }

            if (item.optionName == "Time Table"){
                binding.LLCircle.setOnClickListener {
                    navController.navigate(R.id.action_navigation_home_to_timetableFragment)
                }
            }

            if(item.optionName == "Grade Card"){
                binding.LLCircle.setOnClickListener {
                    navController.navigate(R.id.action_navigation_home_to_gradeCardFragment)
                }
            }

            if(item.optionName == "Student Attendance"){
                binding.LLCircle.setOnClickListener {
                    navController.navigate(R.id.action_navigation_home_to_studentsAttendanceFragment)
                }
            }

            if(item.optionName == "Profile"){
                binding.LLCircle.setOnClickListener {
                    navController.navigate(R.id.action_navigation_home_to_navigationAccount)
                }
            }



        }
    }

}