package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_guide

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import net.mizucoffee.hatsuyuki_chinachu.R
import net.mizucoffee.hatsuyuki_chinachu.model.ProgramItem
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki
import java.text.SimpleDateFormat
import java.util.*


class ProgramAdapter(val list: ArrayList<List<ProgramItem>>) : RecyclerView.Adapter<ProgramAdapter.ViewHolder>() {

    var density: Float = 0f
//    var list = ArrayList<ProgramItem>()
    lateinit var inflater: LayoutInflater

//    init {
//        l.programs.forEach { list.add(it.programItem) }
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramAdapter.ViewHolder? {
        inflater = LayoutInflater.from(parent.context)
        density = parent.resources.displayMetrics.density
        val v = inflater.inflate(R.layout.card_program_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ProgramAdapter.ViewHolder, position: Int) {
        list[position].forEach {
            Shirayuki.log(it.title + "::::" + it.seconds)

//            if(SimpleDateFormat("HH", Locale.ENGLISH).format(Date(it.start)).toInt() ==
//                    SimpleDateFormat("HH", Locale.ENGLISH).format(Date(System.currentTimeMillis() : 1000)).toInt()) return

            val v = inflater.inflate(R.layout.recycler_program_list, null, false)

            val root = v.findViewById(R.id.root_ll) as LinearLayout
            val l = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)

            val min = (it.seconds / 60)
            val viewHeight = 420


            l.height = (((viewHeight / 60) * min) / density + 0.5).toInt()
            root.layoutParams = l

            val t = (v.findViewById(R.id.title_tv) as TextView)
            t.text = (it.simpleDate +" "+ SimpleDateFormat("DD", Locale.ENGLISH).format(Date(it.start)) + it.title)

            val d = (v.findViewById(R.id.des_tv) as TextView)
            d.text = it.description

//        holder.linearLayout.removeAllViews()
            holder.linearLayout.addView(v)
        }



    }


    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var linearLayout = itemView.findViewById(R.id.linearLayout) as LinearLayout
//        var titleTv: TextView = itemView.findViewById(R.id.title_tv) as TextView
//        var desTv: TextView = itemView.findViewById(R.id.des_tv) as TextView


    }
}
