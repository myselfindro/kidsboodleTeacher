package com.kidsboodle.teacher.ui.fragment.home.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kidsboodle.teacher.data.api.ApiHelper
import com.kidsboodle.teacher.data.repository.MainRepository

class TimetableViewModelFactory(private val apiHelper: ApiHelper) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        TimetableViewModel(MainRepository(apiHelper)) as T
}