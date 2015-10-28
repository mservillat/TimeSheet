package br.com.mowa.timesheet.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.mowa.timesheet.activity.TasksActivity;
import br.com.mowa.timesheet.timesheet.R;

/**
 * Created by walky on 10/27/15.
 */
public class ProfileMenuOrderDialogFragment extends DialogFragment{
    private ListView lvMenu;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_profile_order, null);

        this.lvMenu = (ListView) view.findViewById(R.id.fragment_profile_order_list_view);
        lvMenu.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, getList()));
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TasksActivity activity = (TasksActivity) getActivity();
                switch (position) {
                    case  0 :
                        activity.orderRecent();
                        dismiss();
                    break;


                    case 1 :
                        activity.orderOld();
                        dismiss();
                        break;


                    case 2 :
                        activity.orderName();
                        dismiss();
                        break;


                    case 3 :
                        activity.orderMoreTime();
                        dismiss();
                        break;


                    case 4 :
                        activity.orderLessTime();
                        dismiss();
                        break;

                    default:
                        break;

                }
            }
        });


        builder.setView(view);
        return builder.create();
    }

    private List<String> getList() {
        List<String> list = new ArrayList<>();
        list.add("Mais recente");
        list.add("Mais antiga");
        list.add("Nome");
        list.add("Maior duração");
        list.add("Menor duração");

        return list;
    }

}
