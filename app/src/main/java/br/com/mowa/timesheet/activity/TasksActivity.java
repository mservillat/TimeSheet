package br.com.mowa.timesheet.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.mowa.timesheet.adapter.TasksRecyclerviewAdapter;
import br.com.mowa.timesheet.dialog.ProfileMenuFilterDialogFragment;
import br.com.mowa.timesheet.dialog.ProfileMenuOrderDialogFragment;
import br.com.mowa.timesheet.model.ProjectModel;
import br.com.mowa.timesheet.model.TaskModel;
import br.com.mowa.timesheet.model.UserModel;
import br.com.mowa.timesheet.network.CallJsonNetwork;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.parse.ParseTask;
import br.com.mowa.timesheet.timesheet.R;
import br.com.mowa.timesheet.utils.SharedPreferencesUtil;
import br.com.mowa.timesheet.utils.TaskModelCompare;

public class TasksActivity extends BaseActivity {
    private ListView listView;
    private ParseTask parseTask;
    private List<TaskModel> listDisplay;
    private CallJsonNetwork callJson;
//    private SwipeRefreshLayout swipeLayout;
    private LinearLayoutManager layoutManager;
    private ProgressDialog progress;
    private RecyclerView recycler;
    private UserModel user;
    private ActionMode actionMode;
    private List<TaskModel> listFix;
    private List<TaskModel> listNoChange;
    private TextView tvFilter;
    private TextView tvOrder;
    private BroadcastReceiver mBroadcast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        this.progress = createProgressDialog("Loading", "carregando lista de tarefas", true, true);
        this.progress.show();
        this.user = SharedPreferencesUtil.getUserFromSharedPreferences();



        createToolbar(R.id.activity_registros_toolbar);

        this.tvFilter = (TextView) findViewById(R.id.activity_tasks_text_view_filter);
        this.tvOrder = (TextView) findViewById(R.id.activity_tasks_text_view_order);
//        this.swipeLayout = (SwipeRefreshLayout) findViewById(R.id.activity_registros_swipe_to_refresh);
//        this.swipeLayout.setOnRefreshListener(OnRefreshListener());
//        this.swipeLayout.setColorSchemeResources(R.color.green, R.color.red, R.color.blue);


        this.mBroadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                loadRegistros();
            }
        };


        registerReceiver(mBroadcast, new IntentFilter(NewTaskActivity.BROADCAST));


//        listView = (ListView) findViewById(R.id.activity_registros_list_view);
        parseTask = new ParseTask();
        callJson = new CallJsonNetwork();
        listFix = new ArrayList<>();
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
                    listNoChange = parseTask.jsonObjectToTaskModel(response);
                    listFix = copyList(listNoChange);
                    listDisplay = copyList(listNoChange);
                    recycler = (RecyclerView) findViewById(R.id.activity_tasks_recycler_view);
                    layoutManager = new LinearLayoutManager(getActivity());
                    recycler.setLayoutManager(layoutManager);
                    recycler.setHasFixedSize(true);
                    TasksRecyclerviewAdapter adapter = new TasksRecyclerviewAdapter(listDisplay, interfaceOnClick());
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
                TaskModel task = listDisplay.get(position);
                List<TaskModel> list = getSelectedTasks();
                if (list.size() > 0 ) {
                    if (task.selectd) {
                        task.selectd = false;
                    } else {
                        task.selectd =true;
                    }

                    if (getSelectedTasks().size() == 0) {
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
                    startActivity(intent);
                }



            }

            @Override
            public void onLongClickItemRecycler(int position) {
                if (actionMode == null) {
                    actionMode = startSupportActionMode(getActionModeCallback());
                }
                listDisplay.get(position).selectd = true;
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
        for (TaskModel task : listDisplay) {
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
                    snack(recycler, "Deletando");
                    List<TaskModel> list = getSelectedTasks();
                    for (int i = 0; i < list.size(); i ++) {
                        deleteTask(list.get(i));

                    }

                    loadRegistros();


                }
                mode.finish();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                actionMode = null;
                for (TaskModel task : listDisplay) {
                    task.selectd = false;
                }
                recycler.getAdapter().notifyDataSetChanged();
            }
        };
    }



    private void deleteTask(TaskModel task) {
        this.callJson.callJsonObjectDelete(VolleySingleton.URL_DELETE_TASK_ID + task.getId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadRegistros();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }


    private  List<TaskModel> copyList(List<TaskModel> listTask) {
        List<TaskModel> list = new ArrayList<>();
        for (TaskModel task: listTask) {
            list.add(task);
        }
        return list;
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







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tasks, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.activity_profile_menu_action_order) {
            ProfileMenuOrderDialogFragment dialog = new ProfileMenuOrderDialogFragment();
            FragmentManager fm = getSupportFragmentManager();
            dialog.show(fm, "dialog");
        } else if(item.getItemId() == R.id.activity_profile_menu_action_filter) {
            ProfileMenuFilterDialogFragment dialog = new ProfileMenuFilterDialogFragment();
            FragmentManager fm = getSupportFragmentManager();
            dialog.show(fm, "dialog");
        }else {
            onBackPressed();
        }

        return true;
    }


    
    public void orderOld() {
        listDisplay = copyList(listFix);
        Collections.reverse(listDisplay);
        recycler.setAdapter(new TasksRecyclerviewAdapter(listDisplay, interfaceOnClick()));
        tvOrder.setText(getResources().getString(R.string.activity_tasks_text_view_order_old));
    }


    public void orderName() {
        Collections.sort(listDisplay);
        recycler.getAdapter().notifyDataSetChanged();
        tvOrder.setText(getResources().getString(R.string.activity_tasks_text_view_order_name));
    }

    public void orderRecent() {
        listDisplay = copyList(listFix);
        recycler.setAdapter(new TasksRecyclerviewAdapter(listDisplay, interfaceOnClick()));
        tvOrder.setText(getResources().getString(R.string.activity_tasks_text_view_order_recent));
    }

    public void orderMoreTime() {
        Collections.sort(listDisplay, new TaskModelCompare());
        Collections.reverse(listDisplay);
        recycler.getAdapter().notifyDataSetChanged();
        tvOrder.setText(getResources().getString(R.string.activity_tasks_text_view_order_more_time));
    }

    public void orderLessTime() {
        Collections.sort(listDisplay, new TaskModelCompare());
        recycler.getAdapter().notifyDataSetChanged();
        tvOrder.setText(getResources().getString(R.string.activity_tasks_text_view_order_less_time));
    }

    public void filterProject(ProjectModel project) {
        listDisplay.clear();
        for (TaskModel task : listNoChange) {
            String id = task.getProjectId();
            if (id.equals(project.getId())) {
                listDisplay.add(task);

            } else {
            }
        }
        if (listDisplay.size() > 0 ) {
            listFix.clear();
            listFix = copyList(listDisplay);
            recycler.setAdapter(new TasksRecyclerviewAdapter(listDisplay, interfaceOnClick()));
            tvFilter.setText("Projeto: " + project.getName());
            tvOrder.setText(getResources().getString(R.string.activity_tasks_text_view_order_recent));

        } else {
            toast("Nenhuma há tarefa desse projeto");

        }

    }

    public void filterAllProject() {
        listDisplay.clear();
        listDisplay = copyList(listNoChange);
        listFix = copyList(listNoChange);
        recycler.setAdapter(new TasksRecyclerviewAdapter(listDisplay, interfaceOnClick()));
        tvFilter.setText("Todos os projetos");
    }


}
