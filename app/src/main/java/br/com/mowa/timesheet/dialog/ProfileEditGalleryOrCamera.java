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

import br.com.mowa.timesheet.activity.ProfileEditActivity;
import br.com.mowa.timesheet.timesheet.R;

/**
 * Created by walky on 11/3/15.
 */
public class ProfileEditGalleryOrCamera extends DialogFragment {
    private ListView lvGalleryOrCamera;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_profile_edit_gallery_camera_dialog, null);


        this.lvGalleryOrCamera = (ListView) view.findViewById(R.id.fragment_profile_edit_listview_gallery_camera);
        this.lvGalleryOrCamera.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, getList()));
        this.lvGalleryOrCamera.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProfileEditActivity activity = (ProfileEditActivity) getActivity();
                switch (position) {
                    case 0 :
                        activity.onClickGallery();
                        dismiss();
                        break;
                    case 1 :
                        activity.onclickCamera();
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
        list.add(getResources().getString(R.string.fragment_profile_edit_gallery));
        list.add(getResources().getString(R.string.fragment_profile_edit_camera));
        return list;
    }
}
