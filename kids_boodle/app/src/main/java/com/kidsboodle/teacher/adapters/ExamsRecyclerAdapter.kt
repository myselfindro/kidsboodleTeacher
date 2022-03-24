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
import com.kidsboodle.teacher.data.model.homefragment.students.CheckStudentItem
import com.kidsboodle.teacher.databinding.ItemRowAnnouncementHistoryBinding
import com.kidsboodle.teacher.databinding.ItemRowCheckStudentBinding
import com.kidsboodle.teacher.databinding.ItemRowExamsProfileBinding
import com.kidsboodle.teacher.databinding.ItemRowNoticeBinding

class ExamsRecyclerAdapter(val context: Context, val data: List<ExamsItem>, val navController: NavController) : RecyclerView.Adapter<ExamsRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRowExamsProfileBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: ItemRowExamsProfileBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int){
            val item=data[position]

            binding.tvExamName.text=item.testName
            val marks="${item.marksObtained}%"
            binding.tvMarks.text=marks

            val serialNumber=(position + 1).toString()+"."
            binding.tvSerialNumber.text = serialNumber

//            if(position == data.size - 1 ){
//                binding.itemView.visibility= View.INVISIBLE
//            }

        }
    }

}