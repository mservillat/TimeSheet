package br.com.mowa.timesheet.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.mowa.timesheet.fragment.NavigationDrawerFragment;
import br.com.mowa.timesheet.network.CallJsonNetwork;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.timesheet.R;

public class RegistrosActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.activity_registros_toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_registros_drawer_layout);
        NavigationDrawerFragment navDraFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.activity_registros_fragment_navigation_drawer_container);
        navDraFragment.setUp(drawerLayout, mToolbar);

        CallJsonNetwork callJson = new CallJsonNetwork();
        callJson.callJsonObjectGet(VolleySingleton.URL_GET_TASK, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("date");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String name = jsonObject.optString("name");
                    Log.d("walkyTeste", "registros " + name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("walkyTeste", "Deu ruim");
            }
        });
    }
}
