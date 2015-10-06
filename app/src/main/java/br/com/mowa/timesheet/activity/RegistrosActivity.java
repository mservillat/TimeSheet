package br.com.mowa.timesheet.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import br.com.mowa.timesheet.adapter.RegistrosRecyclerviewAdapter;
import br.com.mowa.timesheet.adapter.RegistrosTableItem;
import br.com.mowa.timesheet.fragment.NavigationDrawerFragment;
import br.com.mowa.timesheet.model.TaskModel;
import br.com.mowa.timesheet.network.CallJsonNetwork;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.parse.ParseTask;
import br.com.mowa.timesheet.timesheet.R;

public class RegistrosActivity extends BaseActivity {
    private ListView listView;
    private List<RegistrosTableItem> list;
    private ParseTask parseTask;
    private List<TaskModel> listTaskModel;
    private CallJsonNetwork callJson;
//    private SwipeRefreshLayout swipeLayout;
    private LinearLayoutManager layoutManager;
    private ProgressDialog progress;
    private RecyclerView recycler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);

        this.progress = createProgressDialog("Loading", "carregando lista de tarefas", true, true);
        this.progress.show();
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_registros_drawer_layout);
        NavigationDrawerFragment navDraFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.activity_registros_fragment_navigation_drawer_container);
        navDraFragment.setUp(drawerLayout, createToolbar(R.id.activity_registros_toolbar));

//        this.swipeLayout = (SwipeRefreshLayout) findViewById(R.id.activity_registros_swipe_to_refresh);
//        this.swipeLayout.setOnRefreshListener(OnRefreshListener());
//        this.swipeLayout.setColorSchemeResources(R.color.green, R.color.red, R.color.blue);


//        listView = (ListView) findViewById(R.id.activity_registros_list_view);
        parseTask = new ParseTask();
        callJson = new CallJsonNetwork();
        loadRegistros();

    }

    /**
     * Chamada GET em todas as tarefas do usuário logado
     * adapta a lista em um recyclerView
     */
    private void loadRegistros() {
        callJson.callJsonObjectGet(VolleySingleton.URL_GET_TASK, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    listTaskModel = parseTask.jsonObjectToTaskEntity(response);
                    list = RegistrosTableItem.builderList(listTaskModel);
                    recycler = (RecyclerView) findViewById(R.id.rv);
                    layoutManager = new LinearLayoutManager(getActivity());
                    recycler.setLayoutManager(layoutManager);
                    recycler.setAnimation(null);
                    recycler.setHasFixedSize(true);
                    RegistrosRecyclerviewAdapter adapter = new RegistrosRecyclerviewAdapter(list, interfaceOnClick());
                    recycler.setAdapter(adapter);
                    progress.dismiss();

//                    listView.setAdapter(new RegistrosTableAdapter(getActivity(), list));
//                    stopRefresh();
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


    /**
     * Caso um item do Recycler seja clicado, esse metodo será chamado;
     * @return
     */
    private RegistrosRecyclerviewAdapter.ClickRecycler interfaceOnClick() {
        return new RegistrosRecyclerviewAdapter.ClickRecycler() {
            @Override
            public void onClickIntemRecycler(View view, int position) {
                RegistrosTableItem r = list.get(position);
                toast(r.getName());
            }
        };
    }


    /**
     * Interface OnRefreshListener do SwipeRefreshLayout
     * @return
     */
//    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener() {
//        return new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if (IsConnectionNetworkAvailable.isNetworkAvailable(getContext())) {
//                    loadRegistros();
//                } else {
//                    toast("erro - verifique sua conexão");
//                    stopRefresh();
//                }
//
//            }
//        };
//    }

    /**
     * Metodo para cancelar o swipeRefresh
     */
//    private void stopRefresh() {
//        if (swipeLayout.isRefreshing()) {
//            swipeLayout.setRefreshing(false);
//        }
//    }
}
