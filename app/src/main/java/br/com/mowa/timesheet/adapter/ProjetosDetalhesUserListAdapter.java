package br.com.mowa.timesheet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.mowa.timesheet.model.TaskModel;
import br.com.mowa.timesheet.timesheet.R;
import br.com.mowa.timesheet.utils.UtilsTime;

/**
 * Created by walky on 9/23/15.
 */
public class ProjetosDetalhesUserListAdapter extends BaseAdapter {
    private List<TaskModel> list;
    private Context context;
    private LayoutInflater inflater;


    public ProjetosDetalhesUserListAdapter(Context context, List<TaskModel> list) {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list != null ? list.get(position): null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = this.inflater.inflate(R.layout.layout_custom_item_projetos_adapter_perfil, parent, false);
            convertView.setTag(holder);
            holder.name = (TextView) convertView.findViewById(R.id.layout_item_projetos_adapter_perfil_text_name);
            holder.time = (TextView) convertView.findViewById(R.id.layout_item_projetos_adapter_perfil_text_hours);
        } else {
            holder = (Holder) convertView.getTag();
        }
        TaskModel item = list.get(position);
        holder.name.setText(item.getName());
        holder.time.setText(UtilsTime.longMillisToString(item.getTime()));



        return convertView;
    }


    static class Holder {
        TextView name;
        TextView time;
    }



}
