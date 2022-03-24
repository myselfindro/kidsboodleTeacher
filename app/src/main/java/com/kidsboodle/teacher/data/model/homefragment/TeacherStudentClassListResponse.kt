package com.kidsboodle.teacher.data.model.homefragment

import com.google.gson.annotations.SerializedName

data class TeacherStudentClassListResponse(

	@field:SerializedName("result")
	val result: List<ResultsItem?>? = null,

	@field:SerializedName("request_status")
	val requestStatus: Int? = null,

	@field:SerializedName("msg")
	val msg: String? = null
)

data class SessionItem(

	@field:SerializedName("end_date")
	val endDate: String? = null,

	@field:SerializedName("session")
	val session: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("start_date")
	val startDate: String? = null
)

data class ResultsItem(

	@field:SerializedName("is_deleted")
	val isDeleted: Boolean? = null,

	@field:SerializedName("school")
	val school: Int? = null,

	@field:SerializedName("session")
	val session: List<SessionItem?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("class_name")
	val className: String? = null
)
