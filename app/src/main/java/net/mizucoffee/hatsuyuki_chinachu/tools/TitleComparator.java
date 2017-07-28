package net.mizucoffee.hatsuyuki_chinachu.tools;

import net.mizucoffee.hatsuyuki_chinachu.model.ProgramItem;

import java.util.Comparator;

public class TitleComparator implements Comparator<ProgramItem> {
    @Override
    public int compare(ProgramItem r1, ProgramItem r2) {
        return r1.getTitle().compareTo(r2.getTitle());
    }
}