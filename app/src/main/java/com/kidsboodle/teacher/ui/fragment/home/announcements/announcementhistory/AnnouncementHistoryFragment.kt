package com.kidsboodle.teacher.ui.fragment.home.announcements.announcementhistory


import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaopiz.kprogresshud.KProgressHUD
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.adapters.ViewPagerAdapter
import com.kidsboodle.teacher.data.api.ApiHelper
import com.kidsboodle.teacher.data.api.RetrofitBuilder
import com.kidsboodle.teacher.data.model.homefragment.announcement.AnnouncementHistoryItem
import com.kidsboodle.teacher.data.model.homefragment.announcement.ResultItems
import com.kidsboodle.teacher.databinding.FragmentAnnouncementsBinding
import com.kidsboodle.teacher.databinding.FragmentAnnouncementsHistoryBinding
import com.kidsboodle.teacher.databinding.FragmentCreateAnnouncementBinding
import com.kidsboodle.teacher.databinding.FragmentHomeBinding
import com.kidsboodle.teacher.ui.fragment.home.announcements.createannouncement.CreateAnnouncementViewModel
import com.kidsboodle.teacher.ui.fragment.home.announcements.createannouncement.CreateAnnouncementViewModelFactory
import com.kidsboodle.teacher.ui.fragment.home.announcements.createannouncement.CreateAnnouncementsFragment
import com.kidsboodle.teacher.utility.*
import java.util.ArrayList


class AnnouncementHistoryFragment() : Fragment(),
    Parcelable {

    private var _binding: FragmentAnnouncementsHistoryBinding? = null

    private lateinit var announcementViewModel: AnnouncementHistoryViewModel
    private var historyList:AnnouncementHistoryItem? = null

    private val binding get() = _binding!!

    constructor(parcel: Parcel) : this() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnnouncementsHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vm: AnnouncementHistoryViewModel by viewModels {
            AnnouncementHistoryViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        }
        announcementViewModel=vm
        makeHistory(PreferenceManager.getUserToken(requireContext()),1, 10)
        //setUpAnnouncementHistoryRecyclerAdapter()
    }

    private fun makeHistory(auth: String, page:Int, pageCount:Int) {
        var loader: KProgressHUD? = null
        announcementViewModel.getAnnouncementList(auth, page, pageCount)
            .observe(this, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)


                            if (resource.data?.requestStatus?.equals(1) == true) {
                                historyList=resource.data
                                setUpAnnouncementHistoryRecyclerAdapter()
                            } else binding.root.showSnackBar(resource.data?.msg)
                        }
                        Status.ERROR -> {
                            hideLoader(loader)
                            binding.root.showSnackBar(it.message)
                        }

                        Status.LOADING -> {
                            loader = requireContext().showLoader()
                        }
                    }
                }
            })


    }

    private fun setUpAnnouncementHistoryRecyclerAdapter() {
        val adapter= com.kidsboodle.teacher.adapters.AnnouncementHistoryRecyclerAdapter(requireContext(),
            historyList?.result as List<ResultItems>,findNavController())
        binding.rvAnnouncementHistory.adapter=adapter
        binding.rvAnnouncementHistory.layoutManager=LinearLayoutManager(requireContext())
    }

    /*override fun onItemClicks(pos: Int) {
        val announcementDetail=AnnouncementDetailsFragment()
        val bundle= Bundle()
        bundle.putString("createdby", historyList?.result?.get(pos)?.school_user)
        bundle.putString("date", historyList?.result?.get(pos)?.date)
        bundle.putString("title", historyList?.result?.get(pos)?.title)
        bundle.putString("desc", historyList?.result?.get(pos)?.description)
        announcementDetail.arguments=bundle

        val transaction=childFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_view_tag,  announcementDetail, null)
        transaction.commit()

    }
*/
    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AnnouncementHistoryFragment> {
        override fun createFromParcel(parcel: Parcel): AnnouncementHistoryFragment {
            return AnnouncementHistoryFragment(parcel)
        }

        override fun newArray(size: Int): Array<AnnouncementHistoryFragment?> {
            return arrayOfNulls(size)
        }
    }


}