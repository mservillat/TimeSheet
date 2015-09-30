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

/**
 * Created by walky on 9/23/15.
 */
public class ProjetosDetalhesUserListAdapter extends BaseAdapter {
    private List<TaskModel.ObjectDisplayUserAndTotalHours> list;
    private Context context;
    private LayoutInflater inflater;


    public ProjetosDetalhesUserListAdapter(Context context, List<TaskModel.ObjectDisplayUserAndTotalHours> list) {
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
        HolderDetalhes holder = null;
        if (convertView == null) {
            holder = new HolderDetalhes();
            convertView = this.inflater.inflate(R.layout.adapter_projetos_detalhes_user, parent, false);
            convertView.setTag(holder);
            holder.name = (TextView) convertView.findViewById(R.id.adapter_projetos_detalhes_user_text_view_name);
            holder.time = (TextView) convertView.findViewById(R.id.adapter_projetos_detalhes_user_text_view_time);
        } else {
            holder = (HolderDetalhes) convertView.getTag();
        }
        TaskModel.ObjectDisplayUserAndTotalHours item = list.get(position);
        holder.name.setText(item.getName());
        holder.time.setText(item.getTimeDisplay());


        return convertView;
    }


    static class HolderDetalhes{
        TextView name;
        TextView time;
    }



}
