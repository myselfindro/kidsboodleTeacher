package com.kidsboodle.teacher.utility

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.widget.EditText
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.kaopiz.kprogresshud.KProgressHUD
import com.kidsboodle.teacher.R

// Created by Droid Developer on 20-10-2021.

fun View.animateFadeIn(duration: Long) {
    val fadeIn = AlphaAnimation(0f, 1f)
    fadeIn.interpolator = DecelerateInterpolator()
    fadeIn.duration = duration

    val animation = AnimationSet(false)
    animation.addAnimation(fadeIn)
    this.show()
    this.animation = animation
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun Activity.goToActivity(clazz: Class<out Activity>, toFinish: Boolean) {
    val intent = Intent(this, clazz)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    startActivity(intent)
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    if (toFinish)
        finishAffinity()
}

fun Context.showLoader(): KProgressHUD =
    KProgressHUD.create(this)
        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        .setLabel("Please wait...")
        .setCancellable(false)
        .setAnimationSpeed(2)
        .setDimAmount(0.5f)
        .show();

fun hideLoader(loader: KProgressHUD?) {
    if (loader != null) {
        if (loader.isShowing)
            loader.dismiss()
    }
}

fun View.showSnackBar(message: String?) {
    if (message != null) {
        val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(this.resources.getColor(R.color.deep_yellow))
        snackbar.setTextColor(this.resources.getColor(R.color.black))
        snackbar.show()
    }
}

fun isContentValid(content: String, edt: EditText): Boolean {
    return if (content.isEmpty()) {
        edt.error = "Field can't be Empty"
        false
    } else {
        edt.error = null
        true
    }
}
fun clearSessionLocally(context: Context)
{
    PreferenceManager.saveToken("", context)
    PreferenceManager.saveUserDetails("","",context)
    PreferenceManager.saveLoginStatus(
        false,
        "",
        context
    )
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}