package br.com.mowa.timesheet.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import br.com.mowa.timesheet.dialog.PerfilAlterarSenhaDialogFragment;
import br.com.mowa.timesheet.fragment.NavigationDrawerFragment;
import br.com.mowa.timesheet.model.ProjectModel;
import br.com.mowa.timesheet.model.UserModel;
import br.com.mowa.timesheet.network.CallJsonNetwork;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.parse.ParseUser;
import br.com.mowa.timesheet.timesheet.R;
import br.com.mowa.timesheet.utils.SharedPreferencesUtil;

public class PerfilActivity extends BaseActivity {
    private DrawerLayout mDrawerLayout;
    private UserModel user;
    private CallJsonNetwork callJson;
    private UserModel userModel;
    private TextView tvNome;
    private TextView tvEmail;
    private TextView tvSituacao;
    private List<ProjectModel> listProjectModel;
    private ListView listViewProjetos;
    private List<String> listProjectString;
    private Button btAlterarSenha;
    private ProgressDialog progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        this.progress = createProgressDialog("Loading", "carregando informações do usuário", true, true);
        this.progress.show();
        this.user = SharedPreferencesUtil.getUserFromSharedPreferences();
        this.callJson = new CallJsonNetwork();

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.activity_perfil_fragment_container);
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_perfil_drawer_layout);
        drawerFragment.setUp(this.mDrawerLayout, createToolbar(R.id.activity_perfil_toolbar));

        this.tvNome = (TextView) findViewById(R.id.activity_perfil_text_view_nome);
        this.tvEmail = (TextView) findViewById(R.id.activity_perfil_text_view_email);
        this.tvSituacao = (TextView) findViewById(R.id.activity_perfil_text_view_situacao);

        this.listViewProjetos = (ListView) findViewById(R.id.activity_perfil_list_view_projetos);



        this.btAlterarSenha = (Button) findViewById(R.id.activity_perfil_button_alterar_senha);
        this.btAlterarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerfilAlterarSenhaDialogFragment dialogAlterarSenha = new PerfilAlterarSenhaDialogFragment();
                FragmentManager fm = getSupportFragmentManager();
                dialogAlterarSenha.show(fm, "DialogAlterarSenha");
            }
        });


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
                    tvNome.setText(userModel.getName());
                    tvEmail.setText(userModel.getUserName());
                    tvSituacao.setText((userModel.isActivite() == true ? "true" : "false"));
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
        List<ProjectModel> list = SharedPreferencesUtil.getListProjectFromSharedPreferences();
        for (int i =0; i < list.size(); i++) {
            Log.d("walkyTeste", list.get(i).getName().toString());
        }








//        ParseProject parse = new ParseProject();
//        listProjectString = parse.parseListProjectEntityToString(listProjectModel);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listProjectString);
//        listViewProjetos.setAdapter(adapter);
//        this.callJson.callJsonObjectGet(VolleySingleton.URL_GET_PROJECT_USER_ID + this.user.getId(), new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    ParseProject parse = new ParseProject();
//                    listProjectModel = parse.parseJsonToProjectEntity(response);
//                    listProjectString = parse.parseListProjectEntityToString(listProjectModel);
//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listProjectString);
//                    listViewProjetos.setAdapter(adapter);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
    }

}
