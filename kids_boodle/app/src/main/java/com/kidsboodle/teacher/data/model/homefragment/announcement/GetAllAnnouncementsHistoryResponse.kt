package com.kidsboodle.teacher.data.model.homefragment.announcement

import com.google.gson.annotations.SerializedName

data class GetAllAnnouncementsHistoryResponse(

	@field:SerializedName("next")
	val next: String? = null,

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

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("scl_class")
	val sclClass: Int? = null,

	@field:SerializedName("own_announcement")
	val ownAnnouncement: Boolean? = null,

	@field:SerializedName("image3")
	val image3: String? = null,

	@field:SerializedName("is_active")
	val isActive: Boolean? = null,

	@field:SerializedName("doc_path")
	val docPath: String? = null,

	@field:SerializedName("student")
	val student: Any? = null,

	@field:SerializedName("is_image1")
	val isImage1: Boolean? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("to_school_or_class")
	val toSchoolOrClass: Boolean? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("image1")
	val image1: String? = null,

	@field:SerializedName("image2")
	val image2: String? = null,

	@field:SerializedName("is_image3")
	val isImage3: Boolean? = null,

	@field:SerializedName("is_image2")
	val isImage2: Boolean? = null,

	@field:SerializedName("is_deleted")
	val isDeleted: Boolean? = null,

	@field:SerializedName("teacher")
	val schoolUser: String? = null,

	@field:SerializedName("school")
	val school: Int? = null,

	@field:SerializedName("cls_section")
	val clsSection: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("announcement_type")
	val announcementType: String? = null
)
