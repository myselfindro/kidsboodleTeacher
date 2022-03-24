package com.kidsboodle.teacher.ui.fragment.home.studentsattendence.viewattendance.attendancelist.remarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.databinding.DialogBottomSheetRemarkBinding
import com.kidsboodle.teacher.databinding.DialogBottomSheetTeacherStudentRemarksBinding
import com.kidsboodle.teacher.databinding.FragmentHomeBinding
import com.kidsboodle.teacher.ui.fragment.home.HomeViewModel

class TeacherStudentCommonBottomSheet(val remark:String):
    BottomSheetDialogFragment() {

    private var _binding: DialogBottomSheetTeacherStudentRemarksBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogBottomSheetTeacherStudentRemarksBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tvRemark.text = remark

        binding.ivCancelBottomSheet.setOnClickListener {
            dismiss()
        }
    }

    override fun dismiss() {
        super.dismiss()
    }
}