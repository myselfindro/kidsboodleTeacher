package com.kidsboodle.teacher.ui.fragment.home.announcements.announcementhistory

import com.kidsboodle.teacher.adapters.AnnouncementHistoryRecyclerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kidsboodle.teacher.data.model.homefragment.announcement.AnnouncementHistoryItem
import com.kidsboodle.teacher.data.model.homefragment.announcement.ResultItems
import com.kidsboodle.teacher.databinding.*
import com.kidsboodle.teacher.utility.*
import java.util.ArrayList

class AnnouncementDetailsFragment : Fragment() {

    private var _binding: FragmentAnnouncementDetailsBinding? = null

    private var createdBy:String? = null
    private var date:String?=null
    private var title:String?=null
    private var desc:String?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnnouncementDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        createdBy= arguments?.getString("createdby")
        date= arguments?.getString("date")
        title= arguments?.getString("title")
        desc= arguments?.getString("desc")



        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAnnouncementHistoryRecyclerAdapter()
    }

    private fun setUpAnnouncementHistoryRecyclerAdapter() {
        binding.txtAnnouncementTitle.text=title
        binding.txtAnnouncementDescription.text=desc
        binding.txtCreatedBy.text="Created by: "+createdBy
        binding.txtDate.text="Created on: "+date
    }



}