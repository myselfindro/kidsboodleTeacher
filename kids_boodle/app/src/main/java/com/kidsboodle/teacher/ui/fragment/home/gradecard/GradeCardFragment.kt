package com.kidsboodle.teacher.ui.fragment.home.gradecard

import android.os.Bundle
import android.view.*
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.adapters.ViewPagerAdapter
import com.kidsboodle.teacher.databinding.FragmentAnnouncementsBinding
import com.kidsboodle.teacher.databinding.FragmentGradecardBinding
import com.kidsboodle.teacher.databinding.FragmentStudentsBinding
import com.kidsboodle.teacher.ui.activity.MainActivity
import com.kidsboodle.teacher.ui.fragment.home.announcements.announcementhistory.AnnouncementHistoryFragment
import com.kidsboodle.teacher.ui.fragment.home.announcements.createannouncement.CreateAnnouncementsFragment


class GradeCardFragment : Fragment() {

    private var _binding: FragmentGradecardBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        (context as MainActivity).changeBackButton()
//        (context as MainActivity).setToolbarLeftIcon(null)
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

//        (context as MainActivity).changeBackButton()
//        (context as MainActivity).setToolbarLeftIcon(null)

        (context as MainActivity).displayHomeUpORHamburger(true)
        (context as MainActivity).changeToolbarTitle("Little Star")
//        (context as MainActivity).makeScreenTitleToLeft(true)

        _binding = FragmentGradecardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCheckGradeCard.setOnClickListener {

        }
    }



}