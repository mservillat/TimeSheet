package br.com.mowa.timesheet.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import br.com.mowa.timesheet.adapter.RegistrosTableAdapter;
import br.com.mowa.timesheet.adapter.RegistrosTableItem;
import br.com.mowa.timesheet.entity.TaskEntity;
import br.com.mowa.timesheet.fragment.NavigationDrawerFragment;
import br.com.mowa.timesheet.network.CallJsonNetwork;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.parse.ParseTask;
import br.com.mowa.timesheet.timesheet.R;

public class RegistrosActivity extends BaseActivity {
    private ListView listView;
    private List<RegistrosTableItem> list;
    private ParseTask parseTask;
    private List<TaskEntity> listTaskEntity;
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

        listView = (ListView) findViewById(R.id.activity_registros_list_view);
        parseTask = new ParseTask();




        CallJsonNetwork callJson = new CallJsonNetwork();
        callJson.callJsonObjectGet(VolleySingleton.URL_GET_TASK, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    listTaskEntity = parseTask.jsonObjectToTaskEntity(response);
                    list = RegistrosTableItem.builderList(listTaskEntity);
                    listView.setAdapter(new RegistrosTableAdapter(getActivity(), list));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                    Log.d("walkyTeste", "on Response");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("walkyTeste", "Deu ruim");
            }
        });
    }
}
