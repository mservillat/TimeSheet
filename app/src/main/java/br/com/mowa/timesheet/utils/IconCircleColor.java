package br.com.mowa.timesheet.utils;

import java.util.ArrayList;
import java.util.List;

import br.com.mowa.timesheet.timesheet.R;

/**
 * Created by walky on 10/15/15.
 */
public class IconCircleColor {
    private final int bright_yellow = R.drawable.circle_bright_yellow;
    private final int pink = R.drawable.circle_pink;
    private final int green = R.drawable.circle_green;
    private final int indigo = R.drawable.circle_indigo;
    private final int yellow = R.drawable.circle_blue;
    private final int red = R.drawable.circle_red;
    private List<Integer> list;


    public IconCircleColor() {
        this.list = new ArrayList<>();
        list.add(bright_yellow);
        list.add(pink);
        list.add(green);
        list.add(indigo);
        list.add(yellow);
        list.add(red);
    }

    public int sortColor() {
        return this.list.get((int)(Math.random() * list.size()));
    }
}
