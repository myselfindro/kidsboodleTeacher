package com.kidsboodle.teacher.data.model.homefragment.studentsattendance

import com.google.gson.annotations.SerializedName

data class StudentsAttendanceItem(
    val studentName:String,
    val studentRoll:String,
    val isPresent:Boolean,
    val teacher_remarks:String,
    val student_id:String,
    val routine:String,
    val attendanceType:String,
    val cls_section:String,
    val scl_class:String,
    val date:String,

)