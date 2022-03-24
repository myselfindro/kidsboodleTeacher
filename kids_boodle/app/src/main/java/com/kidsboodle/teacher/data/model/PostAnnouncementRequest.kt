package com.kidsboodle.teacher.data.model

import com.google.gson.annotations.SerializedName
import retrofit2.http.Multipart

data class PostAnnouncementRequest (
        @field:SerializedName("title")
        val title: String = "",

        @field:SerializedName("description")
        val description: String? = "",


        @field:SerializedName("date")
        val date: String? = "",


        @field:SerializedName("to_school_or_class ")
        val to_school_or_class : String? = "1",


        @field:SerializedName("scl_class")
        val scl_class: String? = "",


        @field:SerializedName("cls_section")
        val cls_section: String? = "",


        @field:SerializedName("student")
        val student: String? = "",



)