package br.com.mowa.timesheet.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import br.com.mowa.timesheet.timesheet.R;

/**
 * Created by walky on 10/5/15.
 */
public class LoginEsqueciASenhaDialogFragment extends DialogFragment implements View.OnClickListener{
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_login_esqueci_senha_dialog, null);



        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onClick(View v) {

    }
}
