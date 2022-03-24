package com.kidsboodle.teacher.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.data.model.homefragment.studentsattendance.StudentsAttendanceItem
import com.kidsboodle.teacher.databinding.ItemRowAttendanceListBinding
import com.kidsboodle.teacher.ui.activity.MainActivity
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.updateattendance.updatelist.UpdateAttendanceListFragment.Companion.setAttendUpdateList
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.updateattendance.updatelist.remark.RemarkBottomSheet
import org.json.JSONObject


class UpdateStudentAttendanceListRecyclerAdapter(val context: Context, val data: List<StudentsAttendanceItem>, val navController: NavController) : RecyclerView.Adapter<UpdateStudentAttendanceListRecyclerAdapter.ViewHolder>() {

    private var isSelectedAll:Boolean =false

    fun selectAll(){
        isSelectedAll=true
        notifyDataSetChanged()
    }

     fun deselectAll(){
        isSelectedAll=false
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRowAttendanceListBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private var binding: ItemRowAttendanceListBinding) : RecyclerView.ViewHolder(binding.root) {
       var isPresent :String = ""

        fun bind(position: Int){
            val item=data[position]

            if(position%2 != 0){
                binding.CLAttendanceListItem.setBackgroundColor(
                    ContextCompat.getColor(context,
                        R.color.light_light_violet))
            }
            else{
                binding.CLAttendanceListItem.setBackgroundColor(ContextCompat.getColor(context,
                    R.color.transparent))
            }
            binding.LLCheckboxPA.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    binding.radioButtonAbsent.id -> {
                        isPresent = "absent"
                    }
                    binding.radioButtonPresent.id  -> {
                        isPresent = "present"
                    }

                }
            })


            if (!isSelectedAll){
                binding.checkBoxStudent.setChecked(false)
            }
            else{
                binding.checkBoxStudent.setChecked(true)
                RemarkBottomSheet().show(
                    (context as MainActivity).supportFragmentManager, ""
                )
            }

            val name= item.studentName
            val roll= item.studentRoll
            binding.tvStudentName.text= name
            binding.tvStudentRoll.text = roll

            binding.checkBoxStudent.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked)
                {
                    addRowUpdate(item,isPresent)
                }
            }

            binding.btnStudentRemark.setOnClickListener {
                RemarkBottomSheet().show(
                    (context as MainActivity).supportFragmentManager, ""
                )
            }

        }
    }
    fun addRowUpdate(item: StudentsAttendanceItem, isPresent: String)
    {
        var jsonObjData = JSONObject()
        jsonObjData.put(("student_id"), item.student_id)
        jsonObjData.put(("teacher_remarks"), item.teacher_remarks)
        jsonObjData.put(("routine"),item.routine)
        jsonObjData.put(("present_status"),isPresent)
        jsonObjData.put(("attendence_type"),item.attendanceType)
        jsonObjData.put(("cls_section"), item.cls_section)
        jsonObjData.put(("scl_class"),item.scl_class)
        jsonObjData.put(("date"),item.date)
        setAttendUpdateList.add(jsonObjData)
        /* setAttendUpdateList.add(
             teacherRemarks= item.teacher_remarks,
             attendanceType = item.attendanceType,
             cls_section = item.cls_section ,
             scl_class = item.scl_class,
             date = item.date,
             presentStatus = isPresent,
             routineID = item.routine,
             studentId = item.student_id
         )*/
    }

}