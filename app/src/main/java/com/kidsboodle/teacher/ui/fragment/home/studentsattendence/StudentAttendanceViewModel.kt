package com.kidsboodle.teacher.ui.fragment.home.studentsattendence

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kidsboodle.teacher.data.Resource
import com.kidsboodle.teacher.data.model.homefragment.studentsattendance.UpdateAttendanceRequest
import com.kidsboodle.teacher.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers


class StudentAttendanceViewModel(private val mainRepository: MainRepository) : ViewModel() {

   /* fun select(item1: String,item2: String,item3: String) {
        selectedClass.value = item1
        selectedSection.value = item2
        selectedSubject.value = item3
    }*/
    fun getTeacherClassSectionList(token:String,Class:String,section:String, page:Int, pageSize:Int) = liveData(Dispatchers.IO) {
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
    fun getSubjectOfClassSection(token:String,Class:String,section:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.getSubjectClassSec(token,Class,section)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }
    fun CheckAttendenceSubmission(token:String,session:String,Class:String,section:String,attendanceType:String,routine:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.getAttendanceCheck(token,session,Class,section,attendanceType,routine)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun getTeacherStudentDetails(token:String,Class:String,section:String,session:String,startDate:String,endDate:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.getTeacherStudentDetails(token,Class,section,session,startDate ,endDate ,"0")))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }
    fun getTeacherStudentDetailsUpdate(token:String,Class:String,section:String,session:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.getAttendanceUpdate(token,Class,section,session,"0")))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }
    fun setAttendanceUpdate(token:String,requestBody : UpdateAttendanceRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.setAttendanceUpdate(token,requestBody )))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

}