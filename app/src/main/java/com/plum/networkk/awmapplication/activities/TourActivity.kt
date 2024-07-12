package com.plum.networkk.awmapplication.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.plum.networkk.awmapplication.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.plum.networkk.awmapplication.databinding.ActivityTourBinding
import com.plum.networkk.awmapplication.fragments.FragmentTour


class TourActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_tour)

        var vBinding = ActivityTourBinding.inflate(layoutInflater)
        val view: View = vBinding.getRoot()
        setContentView(view)

        val adapter = PageAdapter(supportFragmentManager)
        vBinding.viewPager.adapter = adapter
        vBinding.dotsIndicator.setViewPager(vBinding.viewPager);
        vBinding.tvGetStartedBtn.setOnClickListener {
            var mIntent =
                Intent(this@TourActivity, CreateAccountActivity::class.java)
            startActivity(mIntent)
        }

        /*isButtonBackVisible = false
        isButtonNextVisible = false
        isButtonCtaVisible = false
        buttonCtaTintMode = BUTTON_CTA_TINT_MODE_TEXT
        isPagerIndicatorVisible = false


        addSlide(
            FragmentSlide.Builder()
                .background(R.color.transparent_color)
                .backgroundDark(R.color.transparent_color)
                .fragment(FragmentTour(0, 0, R.drawable.tour1))
                .build()
        )
        addSlide(
            FragmentSlide.Builder()
                .background(R.color.transparent_color)
                .backgroundDark(R.color.transparent_color)
                .fragment(FragmentTour(0, 0, R.drawable.tour2))
                .build()
        )
        addSlide(
            FragmentSlide.Builder()
                .background(R.color.transparent_color)
                .backgroundDark(R.color.transparent_color)
                .fragment(FragmentTour(0, 0, R.drawable.tour3))
                .build()
        )*/


//        var tvBtn = TextView(this)
//        tvBtn.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
//        tvBtn.text = "GET STARTED"
//        tvBtn.setOnClickListener(View.OnClickListener {
//
//        })
//        tvBtn.setBackgroundColor(resources.getColor(R.color.app_green_color))
//        tvBtn.setTextColor(Color.WHITE)


    }


    inner class PageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return 3;
        }

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> {
                    return FragmentTour(
                        resources.getString(R.string.txt_tour_title1),
                        resources.getString(R.string.txt_tour_description1),
                        R.drawable.tour1_guy_with_phone_on_desk
                    )
                }
                1 -> {
                    return FragmentTour(
                        resources.getString(R.string.txt_tour_title2),
                        resources.getString(R.string.txt_tour_description2),
                        R.drawable.tour2_phone_with_stats
                    )
                }
                else -> {
                    return FragmentTour(
                        resources.getString(R.string.txt_tour_title3),
                        resources.getString(R.string.txt_tour_description3),
                        R.drawable.tour3_lock_finger
                    )
                }
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> {
                    return "Tab 1"
                }
                1 -> {
                    return "Tab 2"
                }
                2 -> {
                    return "Tab 3"
                }
            }
            return super.getPageTitle(position)
        }

    }
}