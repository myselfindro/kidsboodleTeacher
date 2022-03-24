package com.kidsboodle.teacher.ui.fragment.timetable

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.ui.activity.MainActivity

class TimeTableFragment : Fragment() {

    companion object {
        fun newInstance() = TimeTableFragment()
    }

    private lateinit var viewModel: TimeTableViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (context as MainActivity).changeToolbarTitle("Little Star")
        (context as MainActivity).displayHomeUpORHamburger(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.time_table_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TimeTableViewModel::class.java)
        // TODO: Use the ViewModel
    }

}