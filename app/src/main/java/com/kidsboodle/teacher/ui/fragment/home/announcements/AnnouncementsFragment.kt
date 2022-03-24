package com.kidsboodle.teacher.ui.fragment.home.announcements

import android.os.Bundle
import android.view.*
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.adapters.ViewPagerAdapter
import com.kidsboodle.teacher.databinding.FragmentAnnouncementsBinding
import com.kidsboodle.teacher.ui.activity.MainActivity
import com.kidsboodle.teacher.ui.fragment.home.announcements.announcementhistory.AnnouncementHistoryFragment
import com.kidsboodle.teacher.ui.fragment.home.announcements.createannouncement.CreateAnnouncementsFragment
import com.kidsboodle.teacher.utility.ReusableFunctions


class AnnouncementsFragment : Fragment() {

    private var _binding: FragmentAnnouncementsBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        (context as MainActivity).setToolbarLeftIcon(null)
        (context as MainActivity).changeToolbarTitle("Little Star")
        setHasOptionsMenu(true)
//        (context as MainActivity).displayHomeUpORHamburger(false)
//        (context as MainActivity).changeBackButton()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (context as MainActivity).displayHomeUpORHamburger(true)

        _binding = FragmentAnnouncementsBinding.inflate(inflater, container, false)
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
        ReusableFunctions.setUpTabLayoutCustomisations(requireContext(),binding.tablayout,binding.viewPagerAnnouncements)
    }

    private fun setUpViewPager() {
        val tabTitles = intArrayOf(R.string.create_announcement, R.string.announcement_history)
        val listOfFragment = listOf<Fragment>(CreateAnnouncementsFragment(), AnnouncementHistoryFragment())
        val tabPagerAdapter = ViewPagerAdapter(requireContext(), activity?.supportFragmentManager!!, tabTitles, listOfFragment)

        binding.viewPagerAnnouncements.adapter = tabPagerAdapter
        binding.tablayout.setupWithViewPager(binding.viewPagerAnnouncements)

    }

}