package com.kidsboodle.teacher.ui.fragment.home.students

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kidsboodle.teacher.data.Resource
import com.kidsboodle.teacher.data.model.login.LoginRequest
import com.kidsboodle.teacher.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers

class StudentsViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getTeacherClassSectionList(token:String,Class:String,section:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.getTeacherClassSectionList(token,Class,section)))
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