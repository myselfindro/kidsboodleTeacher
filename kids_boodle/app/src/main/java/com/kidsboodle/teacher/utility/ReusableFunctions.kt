package com.kidsboodle.teacher.utility

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object ReusableFunctions {

    public fun setUpTabLayoutCustomisations(context: Context,tabLayout: TabLayout,viewPager: ViewPager) {
        for (i in 0..tabLayout.tabCount){
            val tab: TabLayout.Tab? = tabLayout.getTabAt(i)
            if (tab != null){
                val tabTextView: TextView = TextView(context)
                tab.customView = tabTextView

                tabTextView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                tabTextView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT

                tabTextView.text = tab.text

                if (i == 0){
                    // This set the font style of the first tab
                    tabTextView.setTypeface(null, Typeface.BOLD)
                }
                if (i == 1){
                    // This set the font style of the first tab
                    tabTextView.setTypeface(null, Typeface.NORMAL)
                }
            }
        }

        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
                val text: TextView = tab.customView as TextView
                text.setTypeface(null, Typeface.BOLD)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val text: TextView = tab?.customView as TextView
                text.setTypeface(null, Typeface.NORMAL)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    fun isPhoneNumberValid(number: String, edt: EditText): Boolean {
        return if(number.length < 10){
            edt.error = "Number cannot be less than 10 digits"
            false
        }
        else {
            edt.error = null
            true
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDate(): String? {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy, EEEE")
        val formatted = current.format(formatter)
        return formatted
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentTime(): String? {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("hh:mm a")
        val formatted = current.format(formatter)
        return formatted
    }

}