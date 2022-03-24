package com.kidsboodle.teacher.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.ui.activity.MainActivity
import com.kidsboodle.teacher.ui.activity.landing.LandingActivity
import com.kidsboodle.teacher.ui.fragment.home.HomeFragment
import com.kidsboodle.teacher.utility.PreferenceManager
import com.kidsboodle.teacher.utility.goToActivity
import kotlinx.android.synthetic.main.dialog_logout.*


class LogoutDialog : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.dialog_logout,
            container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_yes.setOnClickListener(View.OnClickListener {
            PreferenceManager.saveLoginStatus(false, "", requireContext())
            requireActivity().goToActivity(LandingActivity::class.java, true)

        })
        btn_no.setOnClickListener(View.OnClickListener {
            val fragment=HomeFragment()
            //childFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_main, fragment).commit()
            requireActivity().goToActivity(MainActivity::class.java, true)
        })
    }


}