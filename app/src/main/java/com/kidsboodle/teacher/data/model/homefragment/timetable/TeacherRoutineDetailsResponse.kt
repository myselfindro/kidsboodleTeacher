package com.kidsboodle.teacher.data.model.homefragment.timetable

import com.google.gson.annotations.SerializedName

data class TeacherRoutineDetailsResponse(

	@field:SerializedName("next")
	val next: Any? = null,

	@field:SerializedName("request_status")
	val requestStatus: Int? = null,

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("previous")
	val previous: Any? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null
)

data class ResultsItem(

	@field:SerializedName("scl_class")
	val sclClass: Int? = null,

	@field:SerializedName("is_active")
	val isActive: Boolean? = null,

	@field:SerializedName("zoom_link")
	val zoomLink: String? = null,

	@field:SerializedName("subject")
	val subject: String? = null,

	@field:SerializedName("end_time")
	val endTime: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("owned_by")
	val ownedBy: Any? = null,

	@field:SerializedName("section")
	val section: String? = null,

	@field:SerializedName("created_by")
	val createdBy: Any? = null,

	@field:SerializedName("start_time")
	val startTime: String? = null,

	@field:SerializedName("is_deleted")
	val isDeleted: Boolean? = null,

	@field:SerializedName("day_name")
	val dayName: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("school")
	val school: Int? = null,

	@field:SerializedName("teacher")
	val schoolUser: Int? = null,

	@field:SerializedName("is_live")
	val isLive: Boolean? = null,

	@field:SerializedName("updated_by")
	val updatedBy: Any? = null,

	@field:SerializedName("cls_section")
	val clsSection: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("class_name")
	val className: String? = null,

	@field:SerializedName("zoom_pass")
	val zoomPass: String? = null
)
