package com.kidsboodle.teacher.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.data.model.homefragment.DashBoardNoticeItem
import com.kidsboodle.teacher.data.model.homefragment.announcement.AnnouncementHistoryItem
import com.kidsboodle.teacher.databinding.ItemRowAnnouncementHistoryBinding
import com.kidsboodle.teacher.databinding.ItemRowNoticeBinding

class AnnouncementHistoryRecyclerAdapter(val context: Context, val data: List<AnnouncementHistoryItem>, val navController: NavController) : RecyclerView.Adapter<AnnouncementHistoryRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRowAnnouncementHistoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: ItemRowAnnouncementHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int){
            val item=data[position]

            if(position%2 != 0){
                binding.LLItemAnnouncementHistory.setBackgroundColor(context.resources.getColor(R.color.light_light_violet))
            }
            else{
                binding.LLItemAnnouncementHistory.setBackgroundColor(context.resources.getColor(R.color.transparent))
            }

            binding.tvAnnouncementText.text=item.announcementText
            binding.tvCreationDate.text=item.date
            binding.tvCreatorName.text=item.creator



        }
    }

}