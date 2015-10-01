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
        itemViewHolder.name1.setText(list.get(i).getProject());
        itemViewHolder.name2.setText(list.get(i).getName());
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
        TextView name1;
        TextView name2;

        public ItemViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            name1 = (TextView) itemView.findViewById(R.id.name1L);
            name2 = (TextView) itemView.findViewById(R.id.name2L);

        }
    }
}
