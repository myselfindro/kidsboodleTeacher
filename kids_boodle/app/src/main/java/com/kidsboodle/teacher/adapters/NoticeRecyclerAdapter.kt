package com.kidsboodle.teacher.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.kidsboodle.teacher.data.model.homefragment.AnnouncementListItem
import com.kidsboodle.teacher.data.model.homefragment.DashBoardNoticeItem
import com.kidsboodle.teacher.databinding.ItemRowNoticeBinding

class NoticeRecyclerAdapter(val context: Context, val data: List<AnnouncementListItem?>, val navController: NavController) : RecyclerView.Adapter<NoticeRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRowNoticeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: ItemRowNoticeBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int){
            val item=data[position]

            if(item?.title?.isNotEmpty() == true){
                val text="∙ ${item.title}"
                binding.tvNotice.text = text
            }

//            binding.tvNoticeDate.text =item.Date

        }
    }

}