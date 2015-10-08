package br.com.mowa.timesheet.adapter;

import java.util.ArrayList;
import java.util.List;

import br.com.mowa.timesheet.timesheet.R;

/**
 * Created by walky on 9/3/15.
 */
public class NavDrawerMenuItem {
    public String name;
    public int img;
    public boolean selected;

    public NavDrawerMenuItem(String name, int img) {
        this.name = name;
        this.img = img;
    }

    public static List<NavDrawerMenuItem> getList() {
        List<NavDrawerMenuItem> list = new ArrayList<>();


        list.add(new NavDrawerMenuItem("Home", R.mipmap.ic_launcher));
        list.add(new NavDrawerMenuItem("Perfil", R.mipmap.ic_launcher));
        list.add(new NavDrawerMenuItem("Registros", R.mipmap.ic_launcher));
        list.add(new NavDrawerMenuItem("Projetos", R.mipmap.ic_launcher));
        list.add(new NavDrawerMenuItem("Nova tarefa", R.mipmap.ic_launcher));


        return list;
    }

    @Override
    public String toString() {
        return name;
    }
}
