package br.com.mowa.timesheet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.mowa.timesheet.timesheet.R;

/**
 * Created by walky on 9/21/15.
 */
public class RegistrosTableAdapter extends BaseAdapter {
    private List<RegistrosTableItem> list;
    private Context context;
    private LayoutInflater inflater;

    public RegistrosTableAdapter(Context context, List<RegistrosTableItem> list) {
        this.context = context;
        this.list = list;
        this.inflater =  LayoutInflater.from(this.context);
    }



    @Override
    public int getCount() {
        return this.list != null ? this.list.size() : 0;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holderRegistros = null;
        if (convertView == null) {
            holderRegistros = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_registros_table, parent, false);
            convertView.setTag(holderRegistros);
            holderRegistros.project = (TextView) convertView.findViewById(R.id.adapter_campo0);
            holderRegistros.name = (TextView) convertView.findViewById(R.id.adapter_campo1);
            holderRegistros.start_time = (TextView) convertView.findViewById(R.id.adapter_campo2);
            holderRegistros.end_time = (TextView) convertView.findViewById(R.id.adapter_campo3);
            holderRegistros.time = (TextView) convertView.findViewById(R.id.adapter_campo4);
        } else {
            holderRegistros = (ViewHolder) convertView.getTag();
        }
        RegistrosTableItem item = list.get(position);
        holderRegistros.project.setText(item.getProject());
        holderRegistros.name.setText(item.getName());
        holderRegistros.start_time.setText(item.getStart_time());
        holderRegistros.end_time.setText(item.getEnd_time());
        holderRegistros.time.setText(item.getTime().toString());

        return convertView;
    }

    static class ViewHolder {
        TextView project;
        TextView name;
        TextView start_time;
        TextView end_time;
        TextView time;
    }
}
