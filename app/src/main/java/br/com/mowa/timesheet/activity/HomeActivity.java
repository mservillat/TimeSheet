package br.com.mowa.timesheet.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.mowa.timesheet.adapter.TasksRecyclerviewAdapter;
import br.com.mowa.timesheet.dialog.HomeExitDialogFragment;
import br.com.mowa.timesheet.fragment.NavigationDrawerFragment;
import br.com.mowa.timesheet.model.ProjectModel;
import br.com.mowa.timesheet.model.TaskModel;
import br.com.mowa.timesheet.model.UserModel;
import br.com.mowa.timesheet.network.CallJsonNetwork;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.parse.ParseProject;
import br.com.mowa.timesheet.parse.ParseTask;
import br.com.mowa.timesheet.timesheet.R;
import br.com.mowa.timesheet.utils.SharedPreferencesUtil;

public class HomeActivity extends BaseActivity implements ParseProject.OnParseFinish {
    private TextView tvQuantidadeDeHoras;
    private CallJsonNetwork jsonNetwork;
    private DrawerLayout mDrawerLayout;
    private ProgressDialog progress;
    private UserModel user;
    private ParseTask parseTask;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TextView tvIrParaTasks;
    private FloatingActionButton floatingButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.progress = createProgressDialog("Loading", "calculando horas trabalhadas", true, true);
        this.progress.show();


        this.user = SharedPreferencesUtil.getUserFromSharedPreferences();
        this.jsonNetwork = new CallJsonNetwork();
        this.parseTask = new ParseTask();


        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_home_drawer_layout);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.activity_home_fragment_navigation_drawer_container);
        drawerFragment.setUp(mDrawerLayout, createToolbarHome(R.id.activity_home_toolbar));

        this.recyclerView = (RecyclerView) findViewById(R.id.include_activity_home_recycler);
        this.layoutManager = new LinearLayoutManager(getActivity());
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setHasFixedSize(true);

        this.tvIrParaTasks = (TextView) findViewById(R.id.include_activity_home_text_view_ir_para);
        this.tvIrParaTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), TasksActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
            }
        });
        this.tvQuantidadeDeHoras = (TextView) findViewById(R.id.include_activity_home_text_view_horas_semanais);
        callProjectInUser();
        loadDisplayUltimateTasks();
        loadDisplayAllHoursWork();


        this.floatingButton = (FloatingActionButton) findViewById(R.id.include_activity_home_floating_button_new_task);
        this.floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NewTaskActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
            }
        });




    }





    private void callProjectInUser() {
        jsonNetwork.callJsonObjectGet(VolleySingleton.URL_GET_PROJECT_USER_ID + this.user.getId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ParseProject parse = new ParseProject();
                parse.parseJsonToProjectModel(response, HomeActivity.this);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    public void onParseFinish(List<ProjectModel> list) {
        try {
            SharedPreferencesUtil.setListProjectInSharedPreferences(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Faz a chamada rest nas tarefas (task) do usuario logado, soma as horas trabalhadas e apresenta no TextView "QuantidadeDEHoras"
     *
     */
    private void loadDisplayAllHoursWork() {
        jsonNetwork.callJsonObjectGet(VolleySingleton.URL_GET_TASK_USER_ID + this.user.getId() + VolleySingleton.URL_CREATED_AT + getDateSunday() , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("data");
                    if (data != null) {
                        Long time = null;
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject = data.getJSONObject(i);
                            if (time == null) {
                                time = jsonObject.optLong("time");
                            } else {
                                time = time + jsonObject.optLong("time");
                            }
                        }

                        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(time),
                                TimeUnit.MILLISECONDS.toMinutes(time) % TimeUnit.HOURS.toMinutes(1),
                                TimeUnit.MILLISECONDS.toSeconds(time) % TimeUnit.MINUTES.toSeconds(1));

                        tvQuantidadeDeHoras.setText(hms);
//                    tvQuantidadeDeHoras.setText(String.format(" %d min ", TimeUnit.MILLISECONDS.toMinutes(time)));
                        tvQuantidadeDeHoras.refreshDrawableState();
                        progress.dismiss();
                    }

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




    private void loadDisplayUltimateTasks() {
        jsonNetwork.callJsonObjectGet(VolleySingleton.URL_GET_TASK_USER_ID + this.user.getId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    List<TaskModel> list = new ArrayList<>();
                    List<TaskModel> listTask = parseTask.jsonObjectToTaskModel(response);
                    if (listTask.size() >= 2) {
                        for(int i = 0; i < 2; i++) {
                            list.add(listTask.get(i));
                        }
                        TasksRecyclerviewAdapter adapter = new TasksRecyclerviewAdapter(list);
                        recyclerView.setAdapter(adapter);
                        registerForContextMenu(recyclerView);
                    }
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


    private String getDateSunday() {
        Calendar calendar = Calendar.getInstance();
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, - (dayWeek - 1));
        int year = calendar.get(Calendar.YEAR);
        int month = (calendar.get(Calendar.MONTH) + 1);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "-" + month + "-" + day;
    }



    @Override
    public void onBackPressed() {
        HomeExitDialogFragment exitDialogFragment = new HomeExitDialogFragment();
        FragmentManager fm = getSupportFragmentManager();
        exitDialogFragment.show(fm, "HomeExitDialogFragment");
    }





//    /**
//     * Interface OnRefreshListener do SwipeRefreshLayout
//     * @return
//     */
//    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener() {
//        return new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if (IsConnectionNetworkAvailable.isNetworkAvailable(getContext())) {
//                    loadDisplayAllHoursWork();
//                    loadDisplayUltimateTasks();
//                    stopRefresh();
//                } else {
//                    toast("erro - verifique sua conexão");
//                    stopRefresh();
//                }
//
//            }
//        };
//    }
//
//    /**
//     * Metodo para cancelar o swipeRefresh
//     */
//    private void stopRefresh() {
//        if (swipeLayout.isRefreshing()) {
//            swipeLayout.setRefreshing(false);
//        }
//    }
}
