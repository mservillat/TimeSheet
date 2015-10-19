package br.com.mowa.timesheet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.mowa.timesheet.TimeSheetApplication;
import br.com.mowa.timesheet.model.TaskModel;
import br.com.mowa.timesheet.timesheet.R;
import br.com.mowa.timesheet.utils.IconCircleColor;

/**
 * Created by walky on 10/1/15.
 */
public class TasksRecyclerviewAdapter extends RecyclerView.Adapter<TasksRecyclerviewAdapter.ItemViewHolder> {
    private List<TaskModel> list;
    private ClickRecyclerTask clickRecyclerTask;
    private IconCircleColor iconCircleColor;
    private Context context;

    public TasksRecyclerviewAdapter(List<TaskModel> list, ClickRecyclerTask clickRecyclerTask) {
        this.list = list;
        this.clickRecyclerTask = clickRecyclerTask;
        this.iconCircleColor = new IconCircleColor();
        this.context = TimeSheetApplication.getAppContext();
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_custom_item_recycler_task, viewGroup, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(v);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder itemViewHolder, final int i) {
        itemViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRecyclerTask.onClickIntemRecycler(i);
            }
        });
        itemViewHolder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clickRecyclerTask.onLongClickItemRecycler(i);
                return false;
            }
        });

        itemViewHolder.tarefa.setText(list.get(i).getName());
        itemViewHolder.dataInicio.setText(list.get(i).getStartTime());
        itemViewHolder.quantidadeHoras.setText(list.get(i).calculateHoursWorks());
        itemViewHolder.iconTextLetter.setText(list.get(i).getName().substring(0, 1).toUpperCase());
        itemViewHolder.imgIconCircle.setImageResource(iconCircleColor.sortColor());

        if (list.get(i).selectd == true) {
            itemViewHolder.container.setBackgroundColor(context.getResources().getColor(R.color.gray));
        } else {
            itemViewHolder.container.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

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
            imgIconCircle = (ImageView) itemView.findViewById(R.id.layout_item_recycler_task_image_view_icon_circle);
            container = itemView;

        }
    }

    public interface ClickRecyclerTask {
        void onClickIntemRecycler(int position);
        void onLongClickItemRecycler(int position);
    }
}
