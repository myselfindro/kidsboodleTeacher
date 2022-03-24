package com.kidsboodle.teacher.ui.activity.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.databinding.ActivitySplashBinding
import com.kidsboodle.teacher.ui.activity.MainActivity
import com.kidsboodle.teacher.ui.activity.landing.LandingActivity
import com.kidsboodle.teacher.utility.PreferenceManager
import com.kidsboodle.teacher.utility.animateFadeIn
import com.kidsboodle.teacher.utility.goToActivity
import com.kidsboodle.teacher.utility.show
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        lifecycleScope.launch {
            delay(800)
            binding.ivKidsCircle.animateFadeIn(1500)
            delay(1000)
            animateZoomIn()
        }

    }

    private fun animateZoomIn() {
        val animZoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in)
        binding.tvSloganText.show()
        binding.tvSloganText.startAnimation(animZoomIn)
        lifecycleScope.launch {
            delay(1000)
            if(PreferenceManager.getLoginStatus(this@SplashActivity)){
                this@SplashActivity.goToActivity(MainActivity::class.java, true)
            }
            else{
                this@SplashActivity.goToActivity(LandingActivity::class.java, true)
            }

        }
    }
}