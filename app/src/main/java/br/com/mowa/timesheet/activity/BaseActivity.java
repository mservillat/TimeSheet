package br.com.mowa.timesheet.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import br.com.mowa.timesheet.timesheet.R;

/**
 * Created by walky on 8/31/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context getContext() {
        return this;
    }

    protected Activity getActivity() {
        return this;
    }

    protected void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    protected void snack(View view, String msg) {
        Snackbar snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        View snackBarView = snackBar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.primary));
        snackBar.show();
    }

    protected void snackWithButton(View view, String msg, String stringButton) {
        Snackbar snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackBar.setAction(stringButton, null);
        snackBar.setActionTextColor(getResources().getColor(R.color.white));
        View snackBarView = snackBar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.primary));
        snackBar.show();
    }


    /**
     *Criação do ProgressDialog
     * @param title Titulo para ser exibido no ProgressDialog
     * @param message mensagem para ser exibida no ProgressDialog
     * @param indeterminate caso seja true a Progress será exibido por tempo indeterminado
     * @param cancelable caso seja true o usuário poderá cancelar o ProgressDialog
     * @return Objeto ProgressDialog contendo title, message, indeteminate e cancelables
     */
    protected ProgressDialog createProgressDialog(String title, String message, Boolean indeterminate, Boolean cancelable) {
        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle(title);
        progress.setMessage(message);
        progress.setIndeterminate(indeterminate);
        progress.setCancelable(cancelable);

        return progress;
    }


    /**
     * Criação da Toolbar
     * @param id do include toolbar do xml
     * @return objeto Toolbar  (provavelmente usado posteriormente para criar o Navigation Drawer)
     */
    protected Toolbar createToolbarHome(int id) {
        Toolbar toolbar = (Toolbar) findViewById(id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        return toolbar;
    }

    protected Toolbar createToolbar(int id) {
        Toolbar toolbar = (Toolbar) findViewById(id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return toolbar;
    }


    protected void toast(int msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void toastCurto(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
