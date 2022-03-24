package com.kidsboodle.teacher.ui.fragment.home.announcements.createannouncement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kidsboodle.teacher.data.Resource
import com.kidsboodle.teacher.data.model.forgotpwd.GetGradeRequest
import com.kidsboodle.teacher.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CreateAnnouncementViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getTeacherClassSectionList(token: String, Class: String, section: String, pageCount:Int, pageSize:Int) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(
                    Resource.success(
                        data = mainRepository.getTeacherClassSectionList(
                            token,
                            Class,
                            section,
                            pageCount,
                            pageSize
                        )
                    )
                )
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }

    fun getTeacherStudentClassList(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.getTeacherStudentClassList(token)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }
    fun getTeacherStudentList(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.getTeacherStudentClassList(token)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun getExamTypeList(token: String, pageSize:Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.getExamTypeList(token, pageSize)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun postAnnouncement(
        token: String, announcementName: RequestBody,
        announcementDesc: RequestBody, date: RequestBody,
        toSchool: RequestBody, className: RequestBody, section: RequestBody, student: RequestBody,
        image1: MultipartBody.Part,
        image2: MultipartBody.Part,
        image3: MultipartBody.Part,
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(
                Resource.success(
                    data = mainRepository.postAnnouncement(
                        token,
                        announcementName,
                        announcementDesc,
                        date,
                        toSchool,
                        className,
                        section,
                        student,
                        image1,
                        image2,
                        image3
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun getGrade(
        token: String, pageSize: Int, exam:String,
        scl_class: String,
        cls_section: String,
        student_id:String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(
                Resource.success(
                    data = mainRepository.getGrade(token, pageSize, exam, scl_class, cls_section, student_id)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

}