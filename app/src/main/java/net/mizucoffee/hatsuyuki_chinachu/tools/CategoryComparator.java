package net.mizucoffee.hatsuyuki_chinachu.tools;

import net.mizucoffee.hatsuyuki_chinachu.model.ProgramItem;

import java.util.Comparator;

public class CategoryComparator implements Comparator<ProgramItem> {
    @Override
    public int compare(ProgramItem r1, ProgramItem r2) {
        return getId(r1.getCategory()) < getId(r2.getCategory()) ? -1 : 1;
    }

    int getId(String category){
        switch (category){
            case "anime": return 0;
            case "information": return 1;
            case "news": return 2;
            case "sports": return 3;
            case "variety": return 4;
            case "drama": return 5;
            case "music": return 6;
            case "cinema": return 7;
            case "theater": return 8;
            case "documentary": return 9;
            case "hobby": return 10;
            case "welfare": return 11;
            case "etc": return 12;
            default: return 15;
        }
    }
}