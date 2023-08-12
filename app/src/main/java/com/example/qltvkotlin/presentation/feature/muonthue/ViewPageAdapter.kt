package com.example.qltvkotlin.presentation.feature.muonthue

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.qltvkotlin.domain.datastructure.pairLookupOf
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ViewPageAdapter(fragment: Fragment, val viewPage: ViewPager2) :
    FragmentStateAdapter(fragment) {

    private val routing = pairLookupOf<Fragment, String>(
        DangThueFragment() to "Đang Thuê",
        HetHanFragment() to "Hết Hạn"
    )

    fun setupWith(tabMain: TabLayout) {
        TabLayoutMediator(tabMain,viewPage){ tab, position ->
            tab.text = routing.getValueBByPosition(position)
        }.attach()
        tabMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == viewPage.currentItem) return
                viewPage.setCurrentItem(tab.position, true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
        viewPage.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == tabMain.selectedTabPosition) return
                tabMain.getTabAt(position)?.select()
            }
        })
    }

    override fun getItemCount(): Int {
        return routing.getSize()
    }

    override  fun createFragment(position: Int): Fragment {
       return routing.getValueAByPosition(position)
    }

    init {
        viewPage.adapter = this
    }
}
