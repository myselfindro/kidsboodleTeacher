package com.kidsboodle.teacher.data.model.homefragment.studentsattendance

import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import java.util.ArrayList

data class UpdateAttendanceRequest(

    @field:SerializedName("attendence_list")
    val attendanceList: ArrayList<JSONObject>? = null
)


//{"student_id": 9, "teacher_remarks": "Individual marking", "routine": "28", "present_status": "", "attendence_type": "multiple", "cls_section": "28", "scl_class": "1", "date": "2022-02-16"},
