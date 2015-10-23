package br.com.mowa.timesheet.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.mowa.timesheet.adapter.TasksRecyclerviewAdapter;
import br.com.mowa.timesheet.model.TaskModel;
import br.com.mowa.timesheet.model.UserModel;
import br.com.mowa.timesheet.network.CallJsonNetwork;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.parse.ParseTask;
import br.com.mowa.timesheet.timesheet.R;
import br.com.mowa.timesheet.utils.SharedPreferencesUtil;

public class TasksActivity extends BaseActivity {
    private ListView listView;
    private ParseTask parseTask;
    private List<TaskModel> listTaskModel;
    private CallJsonNetwork callJson;
//    private SwipeRefreshLayout swipeLayout;
    private LinearLayoutManager layoutManager;
    private ProgressDialog progress;
    private RecyclerView recycler;
    private UserModel user;
    private ActionMode actionMode;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        this.progress = createProgressDialog("Loading", "carregando lista de tarefas", true, true);
        this.progress.show();
        this.user = SharedPreferencesUtil.getUserFromSharedPreferences();


        createToolbar(R.id.activity_registros_toolbar);

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
        callJson.callJsonObjectGet(VolleySingleton.URL_GET_TASK_USER_ID + user.getId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    listTaskModel = parseTask.jsonObjectToTaskModel(response);
                    recycler = (RecyclerView) findViewById(R.id.activity_tasks_recycler_view);
                    layoutManager = new LinearLayoutManager(getActivity());
                    recycler.setLayoutManager(layoutManager);
                    recycler.setHasFixedSize(true);
                    TasksRecyclerviewAdapter adapter = new TasksRecyclerviewAdapter(listTaskModel, interfaceOnClick());
                    recycler.setAdapter(adapter);
                    registerForContextMenu(recycler);
                    progress.dismiss();

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
     * Caso um item do Recycler seja clicado, esse metodo da interface será chamado;
     * @return
     */
    private TasksRecyclerviewAdapter.ClickRecyclerTask interfaceOnClick() {
        return new TasksRecyclerviewAdapter.ClickRecyclerTask() {
            @Override
            public void onClickIntemRecycler(int position) {
                TaskModel task = listTaskModel.get(position);
                List<TaskModel> list = getSelectedTasks();
                if (list.size() > 0 ) {
                    if (task.selectd) {
                        task.selectd = false;
                    } else {
                        task.selectd =true;
                    }

                    if (list.size() == 0) {
                        actionMode.finish();
                        actionMode = null;
                    } else {
                        updateActionModeTitle();
                    }

                    recycler.getAdapter().notifyDataSetChanged();

                } else {
                    Intent intent = new Intent(TasksActivity.this, NewTaskActivity.class);
                    Gson gson = new Gson();
                    intent.putExtra("taskEdit", gson.toJson(task));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }


            }

            @Override
            public void onLongClickItemRecycler(int position) {
                if (actionMode == null) {
                    actionMode = startSupportActionMode(getActionModeCallback());
                }
                listTaskModel.get(position).selectd = true;
                recycler.getAdapter().notifyDataSetChanged();
                updateActionModeTitle();
            }
        };
    }



    private void updateActionModeTitle(){
        if (actionMode != null) {
            actionMode.setTitle(R.string.activity_tasks_action_mode_title_selected_task);
            actionMode.setSubtitle(null);
            List<TaskModel> list = getSelectedTasks();
            if (list.size() == 1) {
                actionMode.setSubtitle(" 1 " + getContext().getResources().getString(R.string.activity_tasks_action_mode_subtitle_selected_one_task));
            } else {
                actionMode.setSubtitle(list.size() + " " + getContext().getResources().getString(R.string.activity_tasks_action_mode_subtitle_selected_tasks));
            }
        }

    }


    private List<TaskModel> getSelectedTasks() {
        List<TaskModel> list = new ArrayList<>();
        for (TaskModel task : listTaskModel) {
            if (task.selectd) {
                list.add(task);
            }
        }
        return list;
    }



    private ActionMode.Callback getActionModeCallback() {
        return new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = getActivity().getMenuInflater();
                inflater.inflate(R.menu.menu_context_tasks_activity, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                List<TaskModel> selectedTasks = getSelectedTasks();
                if (item.getItemId() == R.id.menu_context_tasks_action_delete) {
                    toast("Deletando " + selectedTasks.size());
                }
                mode.finish();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                actionMode = null;
                for (TaskModel task : listTaskModel) {
                    task.selectd = false;
                }
                recycler.getAdapter().notifyDataSetChanged();
            }
        };
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu_registros_context, menu);
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        return super.onContextItemSelected(item);
//    }


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
