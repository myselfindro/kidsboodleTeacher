package com.kidsboodle.teacher.ui.fragment.home.timetable

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kidsboodle.teacher.data.Resource
import com.kidsboodle.teacher.data.model.login.LoginRequest
import com.kidsboodle.teacher.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers

class TimetableViewModel(private val mainRepository: MainRepository) : ViewModel() {

    var _isQueryChanged: MutableLiveData<String> = MutableLiveData()
    fun isQueryChanged(): LiveData<String> = _isQueryChanged

    fun getTeacherRoutineDetails(token:String,pageSize:Int,dayName:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getTeacherRoutineDetails(token,pageSize,dayName)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

}