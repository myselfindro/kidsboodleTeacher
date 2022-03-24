package com.kidsboodle.teacher.ui.fragment.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kidsboodle.teacher.data.Resource
import com.kidsboodle.teacher.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers

class AccountViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getTeacherProfileDetails(token:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.getTeacherProfileDetails(token)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun updateProfileDetails(token:String,requestBody: Map<String,Any>) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.updateProfileDetails(token,requestBody)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }
}