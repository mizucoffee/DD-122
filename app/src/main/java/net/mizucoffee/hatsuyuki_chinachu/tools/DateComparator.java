package net.mizucoffee.hatsuyuki_chinachu.tools;

import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.program.Program;

import java.util.Comparator;

public class DateComparator implements Comparator<Program> {
    @Override
    public int compare(Program r1, Program r2) {
        return r1.getStart() < r2.getStart() ? -1 : 1;
    }
}