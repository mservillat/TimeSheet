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


        list.add(new NavDrawerMenuItem("Home", R.drawable.ic_home_black_24dp));
        list.add(new NavDrawerMenuItem("Perfil", R.drawable.ic_person_black_24dp));
        list.add(new NavDrawerMenuItem("Tarefas", R.drawable.ic_task_black_24dp));
        list.add(new NavDrawerMenuItem("Projetos", R.drawable.ic_content_paste_black_24dp));


        return list;
    }

    @Override
    public String toString() {
        return name;
    }
}
