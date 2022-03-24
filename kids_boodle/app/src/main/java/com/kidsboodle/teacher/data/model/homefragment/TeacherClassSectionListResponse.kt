package com.kidsboodle.teacher.data.model.homefragment

import com.google.gson.annotations.SerializedName

data class TeacherClassSectionListResponse(

	@field:SerializedName("result")
	val result: List<ResultItem?>? = null,

	@field:SerializedName("request_status")
	val requestStatus: Int? = null,

	@field:SerializedName("msg")
	val msg: String? = null
)

data class ResultItem(

	@field:SerializedName("scl_class")
	val sclClass: Int? = null,

	@field:SerializedName("is_deleted")
	val isDeleted: Boolean? = null,

	@field:SerializedName("school")
	val school: Int? = null,

	@field:SerializedName("section_name")
	val sectionName: String? = null,

	@field:SerializedName("session")
	val session: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
