package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_guide

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import net.mizucoffee.hatsuyuki_chinachu.model.ProgramItem
import net.mizucoffee.hatsuyuki_chinachu.model.ScheduleModel
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki
import java.text.SimpleDateFormat
import java.util.*

class ChannelVPageAdapter(fm: FragmentManager, val list: ArrayList<ScheduleModel>) : FragmentPagerAdapter(fm) {

    var fragments: ArrayList<ChannelFragment> = ArrayList()

    init {
        list.forEach { //Channel別
            val array = (0..23).mapTo(ArrayList<List<ProgramItem>>()) { i ->
                it.programs.filter {
                    SimpleDateFormat("d", Locale.JAPANESE).format(it.start).toInt() ==
                            SimpleDateFormat("d", Locale.JAPANESE).format(Date(System.currentTimeMillis())).toInt()
                }.filter {
                    SimpleDateFormat("HH", Locale.ENGLISH).format(Date(it.start)).toInt() == i ||
                            SimpleDateFormat("HH", Locale.ENGLISH).format(Date(it.end)).toInt() == i
                }.sortedBy {
                    it.start
                }.map {
                    it.programItem
                }
            }

            fragments.add(ChannelFragment.newInstance(array))
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
