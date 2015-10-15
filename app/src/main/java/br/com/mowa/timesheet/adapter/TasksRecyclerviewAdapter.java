package br.com.mowa.timesheet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.mowa.timesheet.model.TaskModel;
import br.com.mowa.timesheet.timesheet.R;
import br.com.mowa.timesheet.utils.AnimationsUtil;
import br.com.mowa.timesheet.utils.IconCircleColor;

/**
 * Created by walky on 10/1/15.
 */
public class TasksRecyclerviewAdapter extends RecyclerView.Adapter<TasksRecyclerviewAdapter.ItemViewHolder> {
    private List<TaskModel> list;
    private ClickRecycler clickRecycler;
    private ArrayList<Integer> selected = new ArrayList<>();
    private IconCircleColor iconCircleColor;
    private int previousPosition = 0;
    private boolean parAndImpa;

    public TasksRecyclerviewAdapter(List<TaskModel> list, ClickRecycler clickRecycler) {
        this.list = list;
        this.clickRecycler = clickRecycler;
        this.iconCircleColor = new IconCircleColor();
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_custo_item_recycler_task, viewGroup, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(v);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder itemViewHolder, final int i) {
        itemViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRecycler.onClickIntemRecycler(i);
            }
        });

        itemViewHolder.tarefa.setText(list.get(i).getName());
        itemViewHolder.dataInicio.setText(list.get(i).getStartTime());
        itemViewHolder.quantidadeHoras.setText(list.get(i).calculateHoursWorks());
        itemViewHolder.iconTextLetter.setText(list.get(i).getName().substring(0, 1).toUpperCase());
        itemViewHolder.imgIconCircle.setImageResource(iconCircleColor.sortColor());



        if (i > previousPosition) {
            parAndImpa = i % 2 == 0 ? true : false;
            AnimationsUtil.animate(itemViewHolder, true, parAndImpa);
        } else {
            AnimationsUtil.animate(itemViewHolder, false, parAndImpa);
        }

            previousPosition = previousPosition;


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
        TextView projeto;
        TextView tarefa;
        TextView dataInicio;
        TextView dataTermino;
        TextView quantidadeHoras;
        TextView iconTextLetter;
        ImageView imgIconCircle;
        public View container;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tarefa = (TextView) itemView.findViewById(R.id.layout_item_recycler_task_text_view_title);
            dataInicio = (TextView) itemView.findViewById(R.id.layout_item_recycler_task_text_view_subtitle);
            quantidadeHoras = (TextView) itemView.findViewById(R.id.layout_item_recycler_task_text_view_horas);
            iconTextLetter = (TextView) itemView.findViewById(R.id.layout_item_recycler_task_text_view_icon);
            imgIconCircle = (ImageView) itemView.findViewById(R.id.layout_item_recycler_image_view_icon_circle);
            container = itemView;

        }
    }

    public interface ClickRecycler {
        void onClickIntemRecycler(int position);
    }
}
