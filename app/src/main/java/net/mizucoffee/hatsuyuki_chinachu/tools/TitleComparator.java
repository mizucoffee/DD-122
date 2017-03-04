package net.mizucoffee.hatsuyuki_chinachu.tools;

import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.program.Program;

import java.util.Comparator;

public class TitleComparator implements Comparator<Program> {
    @Override
    public int compare(Program r1, Program r2) {
        return r1.getTitle().compareTo(r2.getTitle());
    }
}