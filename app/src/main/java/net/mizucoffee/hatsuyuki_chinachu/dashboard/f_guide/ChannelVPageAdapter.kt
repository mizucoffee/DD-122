package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_guide

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import net.mizucoffee.hatsuyuki_chinachu.model.ScheduleModel
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki

class ChannelVPageAdapter(fm: FragmentManager, val list: ArrayList<ScheduleModel>) : FragmentPagerAdapter(fm) {

    var fragments: ArrayList<ChannelFragment> = ArrayList()

    init {
        list.forEach {
            fragments.add(ChannelFragment.newInstance(it))
            Shirayuki.log(it.name)
        }
    }


    override fun getPageTitle(position: Int): CharSequence {
        return list[position].name
    }

    override fun getItem(position: Int): Fragment? {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}
