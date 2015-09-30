package br.com.mowa.timesheet.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.mowa.timesheet.adapter.ProjetosDetalhesUserListAdapter;
import br.com.mowa.timesheet.model.ProjectModel;
import br.com.mowa.timesheet.model.TaskModel;
import br.com.mowa.timesheet.model.UserModel;
import br.com.mowa.timesheet.fragment.NavigationDrawerFragment;
import br.com.mowa.timesheet.network.CallJsonNetwork;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.parse.ParseProject;
import br.com.mowa.timesheet.parse.ParseTask;
import br.com.mowa.timesheet.timesheet.R;

public class ProjetosActivity extends BaseActivity {
    private ParseProject parseProject;
    private List<ProjectModel> listProjectModel;
    private ListView listViewProjetos;
    private ListView listViewDetalhes;
    private CallJsonNetwork callJson;
    private UserModel user;
    private ProjectModel project;
    private List<TaskModel.ObjectDisplayUserAndTotalHours> listDetalhesUser;
    private List<TaskModel> taskEntities;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projetos);


        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_projetos_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_projetos_drawer_layout);
        NavigationDrawerFragment navDraFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.activity_projetos_fragment_container);
        navDraFragment.setUp(drawerLayout, toolbar);

        this.listViewDetalhes = (ListView) findViewById(R.id.activity_projetos_list_view_detalhes);
        this.listViewProjetos = (ListView) findViewById(R.id.activity_projetos_list_view_projetos);
        this.parseProject = new ParseProject();
        this.callJson = new CallJsonNetwork();
        callJson.callJsonObjectGet(VolleySingleton.URL_GET_PROJECT, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    builderListViewProject(response);

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

    private void builderListViewProject(JSONObject response) throws JSONException {
        this.listProjectModel = parseProject.parseJsonToProjectEntity(response);
        ArrayAdapter<ProjectModel> adapter = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1, listProjectModel);
        this.listViewProjetos.setAdapter(adapter);
        this.listViewProjetos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listViewProjetos.setEnabled(false);
                project = listProjectModel.get(position);
                listDetalhesUser = new ArrayList<>();
                listViewDetalhes.setAdapter(null);

                for (int i = 0; i < project.getUsers().size(); i++) {
                    user = project.getUsers().get(i);

                    callJson.callJsonObjectGet(VolleySingleton.URL_GET_TASK_PROJECT_ID + project.getId() + VolleySingleton.URL_ATRIBUTO_USER + user.getId(), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            ParseTask parseTask = new ParseTask();
                            try {
                                TaskModel.ObjectDisplayUserAndTotalHours item = TaskModel.builderListTaskToObjectDisplay(parseTask.jsonObjectToTaskEntity(response));
                                listDetalhesUser.add(item);

                                ProjetosDetalhesUserListAdapter adapter = new ProjetosDetalhesUserListAdapter(getActivity(), listDetalhesUser);
                                listViewDetalhes.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                                listViewProjetos.setEnabled(true);
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

            }

        });

    }

}
