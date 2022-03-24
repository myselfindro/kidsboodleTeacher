package com.kidsboodle.teacher.ui.fragment.home.timetable.days

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaopiz.kprogresshud.KProgressHUD
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.adapters.TimeTableRecyclerAdapter
import com.kidsboodle.teacher.adapters.ViewPagerAdapter
import com.kidsboodle.teacher.data.api.ApiHelper
import com.kidsboodle.teacher.data.api.RetrofitBuilder
import com.kidsboodle.teacher.data.model.homefragment.timetable.ResultsItem
import com.kidsboodle.teacher.data.model.homefragment.timetable.TimeTableItem
import com.kidsboodle.teacher.databinding.FragmentMondayBinding
import com.kidsboodle.teacher.databinding.FragmentStudentsBinding
import com.kidsboodle.teacher.ui.fragment.home.HomeViewModel
import com.kidsboodle.teacher.ui.fragment.home.HomeViewModelFactory

import com.kidsboodle.teacher.ui.fragment.home.announcements.announcementhistory.AnnouncementHistoryFragment
import com.kidsboodle.teacher.ui.fragment.home.announcements.createannouncement.CreateAnnouncementsFragment
import com.kidsboodle.teacher.ui.fragment.home.timetable.TimetableViewModel
import com.kidsboodle.teacher.ui.fragment.home.timetable.TimetableViewModelFactory
import com.kidsboodle.teacher.utility.*


class ThursdayFragment : Fragment() {

    private var _binding: FragmentMondayBinding? = null

    private val binding get() = _binding!!

    private val list:ArrayList<TimeTableItem> = ArrayList()

    private lateinit var timetableViewModel: TimetableViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//
        val vm: TimetableViewModel by viewModels {
            TimetableViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        }
        timetableViewModel = vm

        _binding = FragmentMondayBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getTimetable("Thu")
    }

//    private fun observeChanges() {
////        TimetableFragment.isQueryChanged().observe(viewLifecycleOwner,{
////            println(it)
////                if(it!=null){
////                    getTimetable(it)
////                }
////        })
//    }

    private fun makeTimeTable() {
        val item1=TimeTableItem("Mathematics","V-A","9.30 AM\n to \n11.00 AM")
        val item2=TimeTableItem("Bengali","VI-A","8.30AM\n to \n9.30AM")
        val item3=TimeTableItem("Geography","XII-A","11.30AM\n to \n12.30PM")

        list.addAll(listOf(item1,item2,item3))
    }

    private fun setUpTimetable(list:List<ResultsItem?>) {
        val adapter=TimeTableRecyclerAdapter(requireContext(),list,findNavController())

        binding.rvTimetableMonday.adapter=adapter
        binding.rvTimetableMonday.layoutManager=LinearLayoutManager(requireContext())
    }

    private fun getTimetable(dayName:String) {
        println("called $dayName")
        var loader: KProgressHUD? = null
        timetableViewModel.getTeacherRoutineDetails(PreferenceManager.getUserToken(requireContext()),10,dayName)
            .observe(this, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideLoader(loader)
                            if (resource.data?.requestStatus?.equals(1) == true) {
                                if(resource.data.count!! != 0){
                                    binding.ivNodataFound.visibility=View.GONE
                                    binding.rvTimetableMonday.visibility=View.VISIBLE
                                    setUpTimetable(resource.data.results!!)
                                }
                                else{
                                    binding.rvTimetableMonday.visibility=View.GONE
                                    binding.ivNodataFound.visibility=View.VISIBLE
                                }
                            } else binding.root.showSnackBar(resource.data?.msg)

//                            TimetableFragment._isQueryChanged.value =null
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


}