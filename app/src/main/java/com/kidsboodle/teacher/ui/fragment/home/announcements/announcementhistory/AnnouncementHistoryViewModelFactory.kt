package com.kidsboodle.teacher.ui.fragment.home.announcements.announcementhistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kidsboodle.teacher.data.api.ApiHelper
import com.kidsboodle.teacher.data.repository.MainRepository

class AnnouncementHistoryViewModelFactory(private val apiHelper: ApiHelper) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        AnnouncementHistoryViewModel(MainRepository(apiHelper)) as T
}