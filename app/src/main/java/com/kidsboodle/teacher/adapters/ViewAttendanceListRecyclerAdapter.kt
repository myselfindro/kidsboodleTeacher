package com.kidsboodle.teacher.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.data.model.homefragment.DashBoardNoticeItem
import com.kidsboodle.teacher.data.model.homefragment.announcement.AnnouncementHistoryItem
import com.kidsboodle.teacher.data.model.homefragment.students.CheckStudentItem
import com.kidsboodle.teacher.data.model.homefragment.studentsattendance.ViewStudentsAttendanceListItem
import com.kidsboodle.teacher.databinding.ItemRowAnnouncementHistoryBinding
import com.kidsboodle.teacher.databinding.ItemRowCheckStudentBinding
import com.kidsboodle.teacher.databinding.ItemRowNoticeBinding
import com.kidsboodle.teacher.databinding.ItemRowViewAttendanceListBinding
import com.kidsboodle.teacher.ui.activity.MainActivity
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.updateattendance.updatelist.remark.RemarkBottomSheet
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.viewattendance.attendancelist.remarks.TeacherStudentCommonBottomSheet

class ViewAttendanceListRecyclerAdapter(val context: Context, val data: List<ViewStudentsAttendanceListItem>, val navController: NavController) : RecyclerView.Adapter<ViewAttendanceListRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRowViewAttendanceListBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: ItemRowViewAttendanceListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int){
            val item=data[position]

            if(position%2 == 0){
                binding.LLItem.setBackgroundColor(context.resources.getColor(R.color.light_light_violet))
            }
            else{
                binding.LLItem.setBackgroundColor(context.resources.getColor(R.color.transparent))
            }

            binding.tvName.text = item.name
            binding.tvRollNumber.text = item.roll
            binding.tvAttendance.text = item.attendance

//            val date="Date : ${item.date}"
//            binding.tvDate.text = date
//
//            val presentStatus="Present Status : ${item.presentStatus}"
//            binding.tvPresentStatus.text = presentStatus
//
//
//            binding.btnTeacherRemarks.setOnClickListener {
//                TeacherStudentCommonBottomSheet(item.teacherRemarks).show(
//                    (context as MainActivity).supportFragmentManager, ""
//                )
//            }
//
//            binding.btnStudentRemarks.setOnClickListener {
//                TeacherStudentCommonBottomSheet(item.studentRemarks).show(
//                    (context as MainActivity).supportFragmentManager, ""
//                )
//            }



        }
    }

}