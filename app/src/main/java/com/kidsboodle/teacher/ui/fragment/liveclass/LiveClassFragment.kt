package com.kidsboodle.teacher.ui.fragment.liveclass

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.ui.activity.MainActivity

class LiveClassFragment : Fragment() {

    companion object {
        fun newInstance() = LiveClassFragment()
    }

    private lateinit var viewModel: LiveClassViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (context as MainActivity).changeToolbarTitle("Little Star")
        (context as MainActivity).displayHomeUpORHamburger(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.live_class_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LiveClassViewModel::class.java)
        // TODO: Use the ViewModel
    }

}