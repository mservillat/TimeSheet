package br.com.mowa.timesheet.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import br.com.mowa.timesheet.network.CallJsonNetwork;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.parse.ParseProject;
import br.com.mowa.timesheet.parse.ParseTask;
import br.com.mowa.timesheet.parse.ParseUser;
import br.com.mowa.timesheet.timesheet.R;
import br.com.mowa.timesheet.utils.ListViewUtils;
import br.com.mowa.timesheet.utils.SharedPreferencesUtil;

public class PerfilActivity extends BaseActivity {
    private UserModel user;
    private CallJsonNetwork callJson;
    private UserModel userModel;
    private TextView tvNome;
    private TextView tvEmail;
    private TextView tvSituacao;
    private Button btAlterarSenha;
    private ProgressDialog progress;
    private ListView listView;
    private List<ProjectModel> listProject;
    private ParseTask parseTask;
    private List<TaskModel> listTask;
    private int contador;
    private ProjetosDetalhesUserListAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        this.progress = createProgressDialog("Loading", "carregando informações do usuário", true, true);
        this.progress.show();
        this.user = SharedPreferencesUtil.getUserFromSharedPreferences();
        this.callJson = new CallJsonNetwork();
        listTask = new ArrayList<>();
        this.listView = (ListView) findViewById(R.id.activity_perfil_list_view);

        loadImageProfile();

        createToolbar(R.id.activity_perfil_toolbar);

//        this.tvNome = (TextView) findViewById(R.id.activity_perfil_text_view_nome);
        this.tvEmail = (TextView) findViewById(R.id.activity_perfil_text_view_email);
//        this.tvSituacao = (TextView) findViewById(R.id.activity_perfil_text_view_situacao);



//
//        this.btAlterarSenha = (Button) findViewById(R.id.activity_perfil_button_alterar_senha);
//        this.btAlterarSenha.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PerfilAlterarSenhaDialogFragment dialogAlterarSenha = new PerfilAlterarSenhaDialogFragment();
//                FragmentManager fm = getSupportFragmentManager();
//                dialogAlterarSenha.show(fm, "DialogAlterarSenha");
//            }
//        });


        loadProjectInUser();
        loadProfileUser();


    }


    /**
     * Chamada REST (GET) no usuario logado, conversão para o objeto UserModel e preenchimento dos campos na activity
     */
    private void loadProfileUser() {
        callJson.callJsonObjectGet(VolleySingleton.URL_GET_USERS_ID + this.user.getId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    userModel = new ParseUser().jsonObjectToUserEntity(response);
//                    tvNome.setText(userModel.getName());
                    tvEmail.setText(userModel.getUserName());
//                    tvSituacao.setText((userModel.isActivite() == true ? "true" : "false"));
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
     * Chamada REST (GET) nos projetos do usuario logado, conversão e Listagem no Listview customizado com adapter
     */
    private void loadProjectInUser() {
        callJson.callJsonObjectGet(VolleySingleton.URL_GET_PROJECT_USER_ID + this.user.getId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ParseProject parseProject = new ParseProject();
                try {
                    listProject = parseProject.parseJsonToProjectModel(response);
                    loadListProject();
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


    private void loadImageProfile() {
        ImageView img = (ImageView) findViewById(R.id.img_perfil);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 12;
        Bitmap blurTemplate = BitmapFactory.decodeResource(getResources(), R.drawable.image_perfil, options);
        BitmapDrawable drawable = new BitmapDrawable(getResources(), blurTemplate);
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            img.setBackground(drawable);
        } else {
            img.setBackgroundDrawable(drawable);
        }

    }



    private void loadListProject() {
        parseTask =new ParseTask();

        adapter = new ProjetosDetalhesUserListAdapter(this, listTask);
        listView.setAdapter(adapter);
        for (contador = 0; contador < listProject.size(); contador++) {
            callJson.callJsonObjectGet(VolleySingleton.URL_GET_TASK_PROJECT_ID + listProject.get(contador).getId(), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        List<TaskModel> listTemp = parseTask.jsonObjectToTaskModel(response);
                        calculateTotalHours(listTemp, contador);

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

    private void calculateTotalHours(List<TaskModel> list, int ii) {
        Long time = new Long(0);
        for (TaskModel task: list) {
            time += task.getTime();
        }

        listTask.add(new TaskModel("Nome do projeto", time));




        adapter.notifyDataSetChanged();
        ListViewUtils.getListViewSize(listView);




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
