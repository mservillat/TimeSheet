package br.com.mowa.timesheet.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.mowa.timesheet.TimeSheetApplication;
import br.com.mowa.timesheet.timesheet.R;

/**
 * Created by walky on 10/1/15.
 */
public class RegistrosRecyclerviewAdapter extends RecyclerView.Adapter<RegistrosRecyclerviewAdapter.ItemViewHolder> {
    private List<RegistrosTableItem> list;
    private ClickRecycler clickRecycler;
    private ArrayList<Integer> selected = new ArrayList<>();
    private Context context;

    public RegistrosRecyclerviewAdapter(List<RegistrosTableItem> list, ClickRecycler clickRecycler) {
        this.list = list;
        this.clickRecycler = clickRecycler;
        this.context = TimeSheetApplication.getAppContext();
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_activity_registros, viewGroup, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(v);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder itemViewHolder, final int i) {
        if (!selected.contains(i)) {
            itemViewHolder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        } else {
            itemViewHolder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.pink));
        }


        itemViewHolder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemViewHolder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.pink));
                if (selected.isEmpty()) {
                    selected.add(i);
                } else {
                    int oldSelected = selected.get(0);
                    selected.clear();
                    selected.add(i);
                    notifyItemChanged(oldSelected);
                }


                clickRecycler.onClickIntemRecycler(v, i);
                return false;
            }
        });
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
        View container;

        public ItemViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            projeto = (TextView) itemView.findViewById(R.id.activity_registros_recycler_view_table_text_view_campo_valor_projeto);
            tarefa = (TextView) itemView.findViewById(R.id.activity_registros_recycler_view_table_text_view_campo_valor_tarefa);
            dataInicio = (TextView) itemView.findViewById(R.id.activity_registros_recycler_view_table_text_view_campo_valor_data_inicial);
            dataTermino = (TextView) itemView.findViewById(R.id.activity_registros_recycler_view_table_text_view_campo_valor_data_termino);
            quantidadeHoras = (TextView) itemView.findViewById(R.id.activity_registros_recycler_view_table_text_view_campo_valor_quantidade_horas);
            container = itemView;
        }
    }

    public interface ClickRecycler {
        void onClickIntemRecycler(View view, int position);
    }
}
