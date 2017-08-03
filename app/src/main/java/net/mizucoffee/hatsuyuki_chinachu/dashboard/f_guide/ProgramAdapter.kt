package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_guide

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.mizucoffee.hatsuyuki_chinachu.R
import net.mizucoffee.hatsuyuki_chinachu.model.ProgramItem
import net.mizucoffee.hatsuyuki_chinachu.model.ScheduleModel


class ProgramAdapter(l: ScheduleModel) : RecyclerView.Adapter<ProgramAdapter.ViewHolder>() {

    var list = ArrayList<ProgramItem>()
    init {
        l.programs.forEach { list.add(it.programItem) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramAdapter.ViewHolder? {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.card_program_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ProgramAdapter.ViewHolder, position: Int) {
        holder.tv.text = list[position].title
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv: TextView = itemView.findViewById(R.id.tv) as TextView


    }
}
