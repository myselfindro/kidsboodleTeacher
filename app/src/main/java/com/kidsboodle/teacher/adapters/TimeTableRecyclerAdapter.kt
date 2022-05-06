package com.kidsboodle.teacher.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.data.model.homefragment.timetable.ResultsItem
import com.kidsboodle.teacher.databinding.ItemRowTimetableBinding

class TimeTableRecyclerAdapter(
    val context: Context,
    val data: List<ResultsItem?>,
    val navController: NavController
) : RecyclerView.Adapter<TimeTableRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRowTimetableBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: ItemRowTimetableBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val item = data[position]

            if (position % 2 == 0) {
                binding.CLTimeTable.setBackgroundColor(context.resources.getColor(R.color.light_light_violet))
            } else {
                binding.CLTimeTable.setBackgroundColor(context.resources.getColor(R.color.transparent))
            }

            binding.tvSubjectName.text = item?.subject
            val classSection = "${item?.className} - ${item?.section}"
            binding.tvClass.text = classSection
            var startTime = 0
            var endTime = 0
            var startTimeHr = item?.startTime?.split(":")?.get(0)
            val startTimeMin = item?.startTime?.split(":")?.get(1)
            var textAM_PM = ""
            if (startTimeHr != null) {
                textAM_PM = if (startTimeHr.toInt() < 12)
                    "AM"
                else "PM"
            }

            if (startTimeHr != null) {
                if (startTimeHr.toInt() > 12)
                    startTime = startTimeHr.toInt() - 12
            }

            var endTimeHr = item?.endTime?.split(":")?.get(0)
            val endTimeMin = item?.endTime?.split(":")?.get(1)
            var textEndAM_PM = ""
            if (endTimeHr != null) {
                textEndAM_PM = if (endTimeHr.toInt() < 12)
                    "AM"
                else "PM"
            }
            if (endTimeHr != null) {
                if (endTimeHr.toInt() > 12)
                    endTime = endTimeHr.toInt() - 12
            }
            val timing =
                "${"$startTime:$startTimeMin $textAM_PM"}\n to \n${"$endTime:$endTimeMin $textEndAM_PM"}"
            binding.tvClassTiming.text = timing

            val zoomlink = item?.zoomLink
            binding.btnStartLiveClass.setOnClickListener {
                if (zoomlink == null) {
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://zoom.us/")
                    )
                    context.startActivity(browserIntent)
                } else {
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(zoomlink)
                    )
                    context.startActivity(browserIntent)
                }

            }

        }
    }

}