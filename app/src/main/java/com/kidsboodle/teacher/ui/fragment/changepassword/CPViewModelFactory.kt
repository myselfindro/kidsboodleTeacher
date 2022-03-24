package com.kidsboodle.teacher.ui.fragment.changepassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kidsboodle.teacher.data.api.ApiHelper
import com.kidsboodle.teacher.data.repository.MainRepository

class CPViewModelFactory(private val apiHelper: ApiHelper) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ChangePasswordViewModel(MainRepository(apiHelper)) as T
}