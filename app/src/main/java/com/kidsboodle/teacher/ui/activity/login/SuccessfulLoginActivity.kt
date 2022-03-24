package com.kidsboodle.teacher.ui.activity.login

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.kidsboodle.teacher.databinding.ActivitySuccessfullLoginBinding
import com.kidsboodle.teacher.ui.activity.MainActivity
import com.kidsboodle.teacher.utility.goToActivity

class SuccessfulLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuccessfullLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessfullLoginBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.btnExplore.setOnClickListener {
            this.goToActivity(MainActivity::class.java, true)
        }
    }
}