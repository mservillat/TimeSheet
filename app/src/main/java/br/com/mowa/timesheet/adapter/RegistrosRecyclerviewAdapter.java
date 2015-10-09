package br.com.mowa.timesheet.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.mowa.timesheet.model.TaskModel;
import br.com.mowa.timesheet.timesheet.R;

/**
 * Created by walky on 10/1/15.
 */
public class RegistrosRecyclerviewAdapter extends RecyclerView.Adapter<RegistrosRecyclerviewAdapter.ItemViewHolder> {
    private List<TaskModel> list;
    private ClickRecycler clickRecycler;
    private ArrayList<Integer> selected = new ArrayList<>();

    public RegistrosRecyclerviewAdapter(List<TaskModel> list, ClickRecycler clickRecycler) {
        this.list = list;
        this.clickRecycler = clickRecycler;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_custo_item_recycler_task, viewGroup, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(v);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder itemViewHolder, final int i) {
        itemViewHolder.tarefa.setText(list.get(i).getName());
        itemViewHolder.dataInicio.setText(list.get(i).getStartTime());
        itemViewHolder.quantidadeHoras.setText(list.get(i).calculateHoursWorks());



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
            tarefa = (TextView) itemView.findViewById(R.id.layout_item_recycler_task_text_view_title);
            dataInicio = (TextView) itemView.findViewById(R.id.layout_item_recycler_task_text_view_subtitle);
            quantidadeHoras = (TextView) itemView.findViewById(R.id.layout_item_recycler_task_text_view_horas);
        }
    }

    public interface ClickRecycler {
        void onClickIntemRecycler(View view, int position);
    }
}
