package br.com.mowa.timesheet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.mowa.timesheet.model.ProjectModel;
import br.com.mowa.timesheet.timesheet.R;
import br.com.mowa.timesheet.utils.IconCircleColor;

/**
 * Created by walky on 10/16/15.
 */
public class ProjectRecyclerviewAdapter extends RecyclerView.Adapter<ProjectRecyclerviewAdapter.ItemViewHolder> {
    private List<ProjectModel> list;
    private IconCircleColor iconCircleColor;

    public ProjectRecyclerviewAdapter(List<ProjectModel> list) {
        this.list = list;
        this.iconCircleColor = new IconCircleColor();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_custom_item_recycler_project, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.projectName.setText(list.get(position).getName());
        holder.dataInicio.setText(list.get(position).getStartDate());
        holder.situacao.setText(list.get(position).isActivite()? "Ativo" : "Finalizado");
        holder.iconTextLetter.setText(list.get(position).getName().substring(0, 1).toUpperCase());
        holder.imgIconCircle.setImageResource(iconCircleColor.sortColor());


    }

    @Override
    public int getItemCount() {
        return list.size() != 0 ? list.size() : null;
    }




    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView projectName;
        TextView dataInicio;
        TextView situacao;
        TextView iconTextLetter;
        ImageView imgIconCircle;
        public View container;

        public ItemViewHolder(View itemView) {
            super(itemView);

            projectName = (TextView) itemView.findViewById(R.id.layout_item_recycler_project_text_view_title);
            dataInicio = (TextView) itemView.findViewById(R.id.layout_item_recycler_project_text_view_subtitle);
            situacao = (TextView) itemView.findViewById(R.id.layout_item_recycler_project_text_view_situacao);
            iconTextLetter = (TextView) itemView.findViewById(R.id.layout_item_recycler_project_text_view_icon);
            imgIconCircle = (ImageView) itemView.findViewById(R.id.layout_item_recycler_project_image_view_icon_circle);
            container = itemView.findViewById(R.id.layout_item_recycler_project_container);
        }
    }
}
