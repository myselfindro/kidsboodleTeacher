package com.kidsboodle.teacher.ui.fragment.home.announcements.announcementhistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.adapters.AnnouncementHistoryRecyclerAdapter
import com.kidsboodle.teacher.adapters.ViewPagerAdapter
import com.kidsboodle.teacher.data.model.homefragment.announcement.AnnouncementHistoryItem
import com.kidsboodle.teacher.databinding.FragmentAnnouncementsBinding
import com.kidsboodle.teacher.databinding.FragmentAnnouncementsHistoryBinding
import com.kidsboodle.teacher.databinding.FragmentCreateAnnouncementBinding
import com.kidsboodle.teacher.databinding.FragmentHomeBinding

class AnnouncementHistoryFragment : Fragment() {

    private var _binding: FragmentAnnouncementsHistoryBinding? = null

    private val historyList:ArrayList<AnnouncementHistoryItem> = ArrayList()

    private val binding get() = _binding!!

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

        makeHistory()
        setUpAnnouncementHistoryRecyclerAdapter()
    }

    private fun makeHistory() {
        val item1=AnnouncementHistoryItem("Class is cancelled today",
        "20.3.2020","Mr A.B Ghosh")

        val item2=AnnouncementHistoryItem("This saturday there will be an extra class",
            "27.3.2020","Mr A.B Mukherjee")

        val item3=AnnouncementHistoryItem("Happy Holidays!",
            "10.4.2021","Mr P.K Bannerjee")

        historyList.addAll(listOf(item1,item2,item3))

    }

    private fun setUpAnnouncementHistoryRecyclerAdapter() {
        val adapter= AnnouncementHistoryRecyclerAdapter(requireContext(),historyList,findNavController())
        binding.rvAnnouncementHistory.adapter=adapter
        binding.rvAnnouncementHistory.layoutManager=LinearLayoutManager(requireContext())
    }


}