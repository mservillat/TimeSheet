package br.com.mowa.timesheet.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import br.com.mowa.timesheet.timesheet.R;
import br.com.mowa.timesheet.utils.SharedPreferencesUtil;

/**
 * A simple {@link DialogFragment} subclass.
 */
public class HomeExitDialogFragment extends DialogFragment implements View.OnClickListener{
    private Activity mActivity;
    private Button btSim;
    private Button btNao;
    private static boolean DELETE_SHARED_PREFERENCE = false;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.fragment_home_exit, null);

        this.btSim = (Button) dialogView.findViewById(R.id.dialog_fragment_home_exit_button_sim);
        this.btSim.setOnClickListener(this);
        this.btNao = (Button) dialogView.findViewById(R.id.dialog_fragment_home_exit_button_nao);
        this.btNao.setOnClickListener(this);


        builder.setView(dialogView);
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.dialog_fragment_home_exit_button_sim) {
            if (DELETE_SHARED_PREFERENCE) {
                SharedPreferencesUtil.deleteSharedPreferences();
            }
            this.mActivity.finish();

        } else {
            dismiss();
        }
    }

    public static void setDeleteSharedPreference(boolean deleteSharedPreference) {
        DELETE_SHARED_PREFERENCE = deleteSharedPreference;
    }
}
