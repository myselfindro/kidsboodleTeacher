package com.kidsboodle.teacher.ui.fragment.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kidsboodle.teacher.data.Resource
import com.kidsboodle.teacher.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers


class AttendanceViewModel(private val mainRepository: MainRepository) : ViewModel() {
    fun getTeacherClassSectionList(token:String,Class:String,section:String, page : Int, pageSize:Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.getTeacherClassSectionList(token,Class,section, page, pageSize)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun getTeacherStudentClassList(token:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.getTeacherStudentClassList(token)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

}