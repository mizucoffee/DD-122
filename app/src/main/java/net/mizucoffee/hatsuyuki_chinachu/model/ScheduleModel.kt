package net.mizucoffee.hatsuyuki_chinachu.model

import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.Program;

data class ScheduleModel(
        val type: String,
        val channel: String,
        val name: String,
        val id: String,
        val sid: Int,
        val nid: Int,
        val hasLogoData: Boolean,
        val n: Int,
        val programs: List<Program>
        )
