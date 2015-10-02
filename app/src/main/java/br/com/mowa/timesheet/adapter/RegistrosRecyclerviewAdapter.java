package br.com.mowa.timesheet.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.mowa.timesheet.timesheet.R;

/**
 * Created by walky on 10/1/15.
 */
public class RegistrosRecyclerviewAdapter extends RecyclerView.Adapter<RegistrosRecyclerviewAdapter.ItemViewHolder> {
    private List<RegistrosTableItem> list;


    public RegistrosRecyclerviewAdapter(List<RegistrosTableItem> list) {
        this.list = list;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_activity_registros, viewGroup, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(v);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.projeto.setText(list.get(i).getProject());
        itemViewHolder.tarefa.setText(list.get(i).getName());
        itemViewHolder.dataInicio.setText(list.get(i).getStart_time());
        itemViewHolder.dataTermino.setText(list.get(i).getEnd_time());
        itemViewHolder.quantidadeHoras.setText(list.get(i).getTime().toString());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView projeto;
        TextView tarefa;
        TextView dataInicio;
        TextView dataTermino;
        TextView quantidadeHoras;

        public ItemViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            projeto = (TextView) itemView.findViewById(R.id.activity_registros_recycler_view_table_text_view_campo_valor_projeto);
            tarefa = (TextView) itemView.findViewById(R.id.activity_registros_recycler_view_table_text_view_campo_valor_tarefa);
            dataInicio = (TextView) itemView.findViewById(R.id.activity_registros_recycler_view_table_text_view_campo_valor_data_inicial);
            dataTermino = (TextView) itemView.findViewById(R.id.activity_registros_recycler_view_table_text_view_campo_valor_data_termino);
            quantidadeHoras = (TextView) itemView.findViewById(R.id.activity_registros_recycler_view_table_text_view_campo_valor_quantidade_horas);

        }
    }
}
