package br.com.mowa.timesheet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.mowa.timesheet.timesheet.R;

/**
 * Created by walky on 9/3/15.
 */
public class NavDrawerMenuAdapter extends BaseAdapter {
    private final List<NavDrawerMenuItem> list;
    private final Context context;
    private LayoutInflater inflater;

    public NavDrawerMenuAdapter(Context context, List list) {
        this.list = list;
        this.context = context;
        this.inflater = (LayoutInflater)LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return this.list != null ? this.list.size() : 0 ;
    }

    @Override
    public Object getItem(int position) {
        return this.list != null ? this.list.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.adapter_nav_drawer, parent, false);
            view.setTag(holder);
            holder.text =  (TextView) view.findViewById(R.id.adapter_nav_drawer_text);
            holder.img = (ImageView) view.findViewById(R.id.adapter_nav_drawer_img);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // Atualiza a view
        NavDrawerMenuItem item = list.get(position);
        holder.text.setText(item.name);
        holder.img.setImageResource(item.img);
        if (item.selected) {
            view.setBackgroundResource(R.drawable.seletor_nav_drawer_selected);
            holder.text.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            view.setBackgroundResource(R.drawable.seletor_nav_drawer);
            holder.text.setTextColor(context.getResources().getColor(R.color.black));
        }

        return view;
    }

    public void setSelected(int position, boolean selected) {
        clearSelected();
        list.get(position).selected = selected;
        notifyDataSetChanged();
    }

    public void clearSelected() {
        if (list != null) {
            for (NavDrawerMenuItem item : list) {
                item.selected = false;
            }
            notifyDataSetChanged();
        }
    }

    static class ViewHolder {
        TextView text;
        ImageView img;
    }

}
