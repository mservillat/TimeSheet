package br.com.mowa.timesheet.dialog;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.mowa.timesheet.timesheet.R;

/**
 * A simple {@link DialogFragment} subclass.
 */
public class HomeExitDialogFragment extends DialogFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_exit, container, false);


        return view;
    }


}
