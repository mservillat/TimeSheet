package br.com.mowa.timesheet.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.mowa.timesheet.adapter.ProjectRecyclerviewAdapter;
import br.com.mowa.timesheet.adapter.ProjetosDetalhesUserListAdapter;
import br.com.mowa.timesheet.model.ProjectModel;
import br.com.mowa.timesheet.model.TaskModel;
import br.com.mowa.timesheet.model.UserModel;
import br.com.mowa.timesheet.network.CallJsonNetwork;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.parse.ParseProject;
import br.com.mowa.timesheet.parse.ParseTask;
import br.com.mowa.timesheet.timesheet.R;

public class ProjectsActivity extends BaseActivity {
    private ParseProject parseProject;
    private List<ProjectModel> listProjectModel;
    private ListView listViewProjetos;
    private ListView listViewDetalhes;
    private CallJsonNetwork callJson;
    private UserModel user;
    private ProjectModel project;
    private List<TaskModel> listDetalhesUser = new ArrayList<>();
    private ProgressDialog progress;
    private TaskModel taskModel;
    private int quantidadeDeErros;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    public static final String KEY_INTENT_PUT_EXTRA_PROJECT_DETAILS = "PROJECT_DETAILS";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        this.progress = createProgressDialog("Loading", "carregando lista de Projetos", true, true);
        this.progress.show();

        createToolbar(R.id.activity_projetos_toolbar);

//        this.taskModel = new TaskModel();
//        this.listViewDetalhes = (ListView) findViewById(R.id.activity_projetos_list_view_detalhes);
//        this.listViewProjetos = (ListView) findViewById(R.id.activity_projetos_list_view_projetos);
        this.layoutManager = new LinearLayoutManager(this);
        this.recyclerView = (RecyclerView) findViewById(R.id.activity_projects_recycler_view);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setHasFixedSize(true);


        this.parseProject = new ParseProject();
        this.callJson = new CallJsonNetwork();
        loadListproject();
    }



    /**
     * Chamada Get em todos os projetos
     */
    private void loadListproject() {
        callJson.callJsonObjectGet(VolleySingleton.URL_GET_PROJECT, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    listProjectModel = parseProject.parseJsonToProjectModel(response);
                    ProjectRecyclerviewAdapter adapter = new ProjectRecyclerviewAdapter(listProjectModel, getClickProject());
                    recyclerView.setAdapter(adapter);
                    registerForContextMenu(recyclerView);
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


    /**
     * Converte o json em uma lista de projetos e cria o adapter no listview
     * @param response Resposta da chamada get que contém todos os projetos
     * @throws JSONException
     */
    private void builderListViewProject(JSONObject response) throws JSONException {
        this.listProjectModel = parseProject.parseJsonToProjectModel(response);
        ArrayAdapter<ProjectModel> adapter = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1, listProjectModel);
        this.listViewProjetos.setAdapter(adapter);
        this.progress.dismiss();
        this.listViewProjetos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                progress.show();
                loadUsersInProjectAndAllHours(position);
            }
        });

    }

    /**
     * Chamada get em todas as tasks dos usuarios que estão no projeto clicado
     * @param position posição do list clicado
     */
    private void loadUsersInProjectAndAllHours(int position) {
        project = listProjectModel.get(position);
        listDetalhesUser.clear();
        quantidadeDeErros = 0;
        listViewDetalhes.setAdapter(null);

        for (int i = 0; i < project.getUsers().size(); i++) {
            user = project.getUsers().get(i);

            callJson.callJsonObjectGet(VolleySingleton.URL_GET_TASK_PROJECT_ID + project.getId() + VolleySingleton.URL_ATRIBUTO_USER + user.getId(), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    ParseTask parseTask = new ParseTask();
                    try {
                        TaskModel item = taskModel.calculateHoursWorks(parseTask.jsonObjectToTaskModel(response));
                        listDetalhesUser.add(item);

                        ProjetosDetalhesUserListAdapter adapter = new ProjetosDetalhesUserListAdapter(getActivity(), listDetalhesUser);
                        listViewDetalhes.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        progress.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    quantidadeDeErros += 1;
                    if (quantidadeDeErros == project.getUsers().size()) {
                        toastCurto("Status code: " + String.valueOf(error.networkResponse.statusCode));
                        progress.dismiss();
                    }
                }
            });
        }
    }

    private ProjectRecyclerviewAdapter.ClickRecyclerProject getClickProject() {
        return new ProjectRecyclerviewAdapter.ClickRecyclerProject() {
            @Override
            public void onClickItemRecycler(int position) {
                Intent intent = new Intent(ProjectsActivity.this, ProjectDetailsActivity.class);
                Gson gson = new Gson();
                intent.putExtra(KEY_INTENT_PUT_EXTRA_PROJECT_DETAILS, gson.toJson(listProjectModel.get(position)));
                startActivity(intent);
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
