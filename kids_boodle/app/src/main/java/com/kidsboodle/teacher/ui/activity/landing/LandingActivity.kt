package com.kidsboodle.teacher.ui.activity.landing

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.lifecycleScope
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.databinding.ActivityLandingBinding
import com.kidsboodle.teacher.ui.activity.login.LoginActivity
import com.kidsboodle.teacher.utility.animateFadeIn
import com.kidsboodle.teacher.utility.show
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LandingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        lifecycleScope.launch {
            val animZoomIn =
                AnimationUtils.loadAnimation(this@LandingActivity, R.anim.translation_x_anim)
            delay(500)
            binding.ivKid.show()
            binding.ivKid.startAnimation(animZoomIn)
            delay(500)
            binding.ivAppLogo.animateFadeIn(1000)
        }
        binding.btnLogin.setOnClickListener {
            navigate()
        }

    }

    private fun navigate() {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this@LandingActivity,
            binding.ivKid, "kid"
        )
        val intent = Intent(this@LandingActivity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent, options.toBundle())
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }
}