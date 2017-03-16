package net.mizucoffee.hatsuyuki_chinachu.tools;

import net.mizucoffee.hatsuyuki_chinachu.model.ProgramItem;

import java.util.Comparator;

public class DateComparator implements Comparator<ProgramItem> {
    @Override
    public int compare(ProgramItem r1, ProgramItem r2) {
        return r1.getStart() < r2.getStart() ? -1 : 1;
    }
}