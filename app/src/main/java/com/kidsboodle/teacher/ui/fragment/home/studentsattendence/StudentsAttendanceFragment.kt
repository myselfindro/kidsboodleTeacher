package com.kidsboodle.teacher.ui.fragment.home.studentsattendence

import android.graphics.Typeface.BOLD
import android.graphics.Typeface.NORMAL
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.kaopiz.kprogresshud.KProgressHUD
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.adapters.ViewPagerAdapter
import com.kidsboodle.teacher.data.api.ApiHelper
import com.kidsboodle.teacher.data.api.RetrofitBuilder
import com.kidsboodle.teacher.databinding.FragmentStudentsAttendanceBinding
import com.kidsboodle.teacher.ui.activity.MainActivity
import com.kidsboodle.teacher.ui.fragment.home.announcements.announcementhistory.AnnouncementHistoryFragment
import com.kidsboodle.teacher.ui.fragment.home.announcements.createannouncement.CreateAnnouncementsFragment
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.updateattendance.UpdateAttendanceFragment
import com.kidsboodle.teacher.ui.fragment.home.studentsattendence.viewattendance.ViewAttendanceFragment
import com.kidsboodle.teacher.utility.*


class StudentsAttendanceFragment : Fragment() {

    private var _binding: FragmentStudentsAttendanceBinding? = null

    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        (context as MainActivity).changeBackButton()
//        (context as MainActivity).setToolbarLeftIcon(R.drawable.ic_left_arrow)
//        (context as MainActivity).makeScreenTitleToLeft(true)
//        (context as MainActivity).setToolbarLeftIcon(R.drawable.ic_left_arrow)
//        (context as MainActivity).changeToolbarTitle("Little Star")
//        (context as MainActivity).setToolbarLeftIcon(R.drawable.ic_left_arrow)

//        (context as MainActivity).displayHomeUpORHamburger(true)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        (context as MainActivity).changeBackButton()
        (context as MainActivity).displayHomeUpORHamburger(true)

        (context as MainActivity).changeToolbarTitle("Little Star")


//        (context as MainActivity).displayHomeUpORHamburger(true)
//        (context as MainActivity).makeScreenTitleToLeft(true)
        _binding = FragmentStudentsAttendanceBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewPager()
        ReusableFunctions.setUpTabLayoutCustomisations(requireContext(),binding.tablayoutStudentAttendence,binding.viewPagerStudentAttendence)

    }

//    private fun setUpTabLayoutCustomisations() {
//        for (i in 0..binding.tablayoutStudentAttendence.tabCount){
//            val tab: TabLayout.Tab? = binding.tablayoutStudentAttendence.getTabAt(i)
//            if (tab != null){
//                val tabTextView: TextView = TextView(requireContext())
//                tab.customView = tabTextView
//
//                tabTextView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
//                tabTextView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
//
//                tabTextView.text = tab.text
//
//                if (i == 0){
//                    // This set the font style of the first tab
//                    tabTextView.setTypeface(null,BOLD)
//                }
//                if (i == 1){
//                    // This set the font style of the first tab
//                    tabTextView.setTypeface(null,NORMAL)
//                }
//            }
//        }
//        binding.tablayoutStudentAttendence.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                binding.viewPagerStudentAttendence.currentItem = tab!!.position
//                val text:TextView = tab.customView as TextView
//                text.setTypeface(null,BOLD)
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                val text:TextView = tab?.customView as TextView
//                text.setTypeface(null,NORMAL)
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//            }
//        })
//    }

    private fun setUpViewPager() {
        val tabTitles = intArrayOf(R.string.update_attendance, R.string.view_attendance)
        val listOfFragment = listOf<Fragment>(UpdateAttendanceFragment(), ViewAttendanceFragment())
        val tabPagerAdapter = ViewPagerAdapter(requireContext(), childFragmentManager, tabTitles, listOfFragment)

        binding.viewPagerStudentAttendence.adapter = tabPagerAdapter
        binding.tablayoutStudentAttendence.setupWithViewPager(binding.viewPagerStudentAttendence)

    }

}