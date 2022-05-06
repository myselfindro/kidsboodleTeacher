package com.kidsboodle.teacher.data.model.homefragment

import com.google.gson.annotations.SerializedName

data class TeacherDashboardDetailsResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("request_status")
	val requestStatus: Int? = null,

	@field:SerializedName("results")
	val results: Results? = null
)

data class TeacherDetails(

	@field:SerializedName("school")
	val school: String? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null
)

data class Results(

	@field:SerializedName("announcement_list")
	val announcementList: List<AnnouncementListItem?>? = null,

	@field:SerializedName("school_details")
	val schoolDetails: List<SchoolDetailsItem?>? = null,

	@field:SerializedName("teacher_details")
	val teacherDetails: TeacherDetails? = null,

	@field:SerializedName("announcement_count")
	val announcementCount: Int? = null
)

data class SchoolDetailsItem(

	@field:SerializedName("school_email")
	val schoolEmail: String? = null,

	@field:SerializedName("reg_no")
	val regNo: String? = null,

	@field:SerializedName("school_phone2")
	val schoolPhone2: String? = null,

	@field:SerializedName("school_name")
	val schoolName: String? = null,

	@field:SerializedName("school_phone1")
	val schoolPhone1: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class AnnouncementListItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("is_active")
	val isActive: Boolean? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null
)
