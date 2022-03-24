package com.kidsboodle.teacher.ui.fragment.home.timetable

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.adapters.ViewPagerAdapter
import com.kidsboodle.teacher.data.api.ApiHelper
import com.kidsboodle.teacher.data.api.RetrofitBuilder
import com.kidsboodle.teacher.databinding.FragmentDashboardTimetableBinding
import com.kidsboodle.teacher.ui.activity.MainActivity
import com.kidsboodle.teacher.ui.fragment.home.timetable.days.*


class TimetableFragment : Fragment() {

    private var _binding: FragmentDashboardTimetableBinding? = null

    private val binding get() = _binding!!

    private lateinit var timetableViewModel: TimetableViewModel

//    companion object{
//        var _isQueryChanged: MutableLiveData<String> = MutableLiveData()
//        fun isQueryChanged(): LiveData<String> = _isQueryChanged
//    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        (context as MainActivity).changeBackButton()
//        (context as MainActivity).setToolbarLeftIcon(null)
//        _isQueryChanged.value="Mon"
        (context as MainActivity).displayHomeUpORHamburger(true)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val vm: TimetableViewModel by viewModels {
            TimetableViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        }
        timetableViewModel = vm

//        (context as MainActivity).changeBackButton()
//        (context as MainActivity).setToolbarLeftIcon(null)
        (context as MainActivity).changeToolbarTitle(com.kidsboodle.teacher.utility.PreferenceManager.getUserSchoolName(requireContext())!!)

        _binding = FragmentDashboardTimetableBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        timetableViewModel._isQueryChanged.value="Mon"
        setUpViewPager()
    }

    fun goToFragment(){
        timetableViewModel.isQueryChanged().observe(viewLifecycleOwner,{
            if(it!=null){

            }
        })
    }

    private fun setUpViewPager() {
        val tabTitles = intArrayOf(R.string.monday, R.string.tuesday, R.string.wednesday, R.string.thursday, R.string.friday, R.string.saturday,R.string.sunday)
        val listOfFragment = listOf<Fragment>(
            MondayFragment(),
            TuesdayFragment(),
            WednesdayFragment(),
            ThursdayFragment(),
            FridayFragment(),
            SaturdayFragment(),
            SundayFragment()
        )
        val tabPagerAdapter = ViewPagerAdapter(requireContext(), activity?.supportFragmentManager!!, tabTitles, listOfFragment)

        binding.viewPagerTimetable.adapter = tabPagerAdapter
        binding.tablayoutTimeTable.setupWithViewPager(binding.viewPagerTimetable)

        binding.tablayoutTimeTable.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
//                Log.d("ImHere","Called ${tab?.text}")
//                timetableViewModel._isQueryChanged.value=tab?.text.toString()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

    }

}