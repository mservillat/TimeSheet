package br.com.mowa.timesheet.activity;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import br.com.mowa.timesheet.timesheet.R;

/**
 * Created by walky on 8/31/15.
 */
public class BaseActivity extends AppCompatActivity {

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


    protected void toast(int msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
