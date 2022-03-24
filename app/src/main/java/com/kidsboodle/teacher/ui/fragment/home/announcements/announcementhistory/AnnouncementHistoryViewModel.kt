package com.kidsboodle.teacher.ui.fragment.home.announcements.announcementhistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kidsboodle.teacher.data.Resource
import com.kidsboodle.teacher.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AnnouncementHistoryViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getAnnouncementList(token: String, page:Int, pageSize:Int) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(
                    Resource.success(
                        data = mainRepository.getAnnouncementHistoryList(
                            token,
                            page,
                            pageSize
                        )
                    )
                )
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }


}