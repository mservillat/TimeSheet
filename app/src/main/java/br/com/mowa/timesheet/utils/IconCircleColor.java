package br.com.mowa.timesheet.utils;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

import java.util.ArrayList;
import java.util.List;

import br.com.mowa.timesheet.TimeSheetApplication;
import br.com.mowa.timesheet.timesheet.R;

/**
 * Created by walky on 10/15/15.
 */
public class IconCircleColor {
    private List<Integer> list;


    public IconCircleColor() {
        this.list = new ArrayList<>();
        list.add(R.color.circle_aqua);
        list.add(R.color.circle_blue);
        list.add(R.color.circle_dark_sea_green);
        list.add(R.color.circle_golden);
        list.add(R.color.circle_green);
        list.add(R.color.circle_light_coral);
        list.add(R.color.circle_orange);
        list.add(R.color.circle_pink);
        list.add(R.color.circle_red);
        list.add(R.color.circle_yellow_bright);

    }

    public String sortColor() {
        int color = (int)(Math.random() * list.size());
        return ("" + color);

    }


    public Drawable circleColor(String stringColor) {
        int intColor = Integer.parseInt(stringColor);
        ShapeDrawable biggerCircle= new ShapeDrawable( new OvalShape());
        biggerCircle.setIntrinsicHeight(40);
        biggerCircle.setIntrinsicWidth(40);
        biggerCircle.setBounds(new Rect(0, 0, 40, 40));
        biggerCircle.getPaint().setColor(TimeSheetApplication.getAppContext().getResources().getColor(list.get(intColor)));
        return biggerCircle;
    }
}
