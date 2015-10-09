package br.com.mowa.timesheet.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import br.com.mowa.timesheet.dialog.HomeExitDialogFragment;
import br.com.mowa.timesheet.fragment.NavigationDrawerFragment;
import br.com.mowa.timesheet.model.UserModel;
import br.com.mowa.timesheet.network.CallJsonNetwork;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.timesheet.R;
import br.com.mowa.timesheet.utils.SharedPreferencesUtil;

public class HomeActivity extends BaseActivity {
    private TextView tvQuantidadeDeHoras;
    private CallJsonNetwork jsonNetwork;
    private DrawerLayout mDrawerLayout;
    private ProgressDialog progress;
    private UserModel user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.progress = createProgressDialog("Loading", "calculando horas trabalhadas", true, true);
        this.progress.show();


        this.user = SharedPreferencesUtil.getUserFromSharedPreferences();
        jsonNetwork = new CallJsonNetwork();


        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_home_drawer_layout);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.activity_home_fragment_navigation_drawer_container);
        drawerFragment.setUp(mDrawerLayout, createToolbar(R.id.activity_home_toolbar));


//        this.tvQuantidadeDeHoras = (TextView) findViewById(R.id.activity_home_text_view_horas_semanais);
//        loadDisplayAllHoursWork();



    }


    /**
     *
     * Faz a chamada rest nas tarefas (task) do usuario logado, soma as horas trabalhadas e apresenta no TextView "QuantidadeDEHoras"
     *
     */
    private void loadDisplayAllHoursWork() {
        jsonNetwork.callJsonObjectGet(VolleySingleton.URL_GET_TASK_USER_ID + this.user.getId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("data");
                    Long time = null;
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);
                        if (time == null) {
                            time = jsonObject.optLong("time");
                        } else {
                            time = time + jsonObject.optLong("time");
                        }
                    }
                    tvQuantidadeDeHoras.setText(String.format(" %d min ", TimeUnit.MILLISECONDS.toMinutes(time)));
                    tvQuantidadeDeHoras.refreshDrawableState();
                    progress.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.activity_home_menu_action_exit) {
            SharedPreferencesUtil.deleteSharedPreferencesUser(SharedPreferencesUtil.KEY_USER_LOGIN_PREFERENCE_USERNAME);
            Intent intent = new Intent(this, LoginActivity.class);
            getActivity().finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        HomeExitDialogFragment exitDialogFragment = new HomeExitDialogFragment();
        FragmentManager fm = getSupportFragmentManager();
        exitDialogFragment.show(fm, "HomeExitDialogFragment");
    }
}
