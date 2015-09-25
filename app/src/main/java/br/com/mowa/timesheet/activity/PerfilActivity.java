package br.com.mowa.timesheet.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import br.com.mowa.timesheet.entity.ProjectEntity;
import br.com.mowa.timesheet.entity.UserEntity;
import br.com.mowa.timesheet.fragment.NavigationDrawerFragment;
import br.com.mowa.timesheet.model.User;
import br.com.mowa.timesheet.network.CallJsonNetwork;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.parse.ParseProject;
import br.com.mowa.timesheet.parse.ParseUser;
import br.com.mowa.timesheet.timesheet.R;
import br.com.mowa.timesheet.utils.SharedPreferencesUtil;

public class PerfilActivity extends BaseActivity {
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private FloatingActionButton btAlterarSenha;
    private EditText editSenhaNova;
    private EditText editSenhaAtual;
    private EditText editSenhaRepetir;
    private User user;
    private CallJsonNetwork callJson;
    private UserEntity userEntity;
    private TextView tvNome;
    private TextView tvEmail;
    private TextView tvSituacao;
    private List<ProjectEntity> listProjectEntity;
    private ListView listViewProjetos;
    private List<String> listProjectString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        this.user = SharedPreferencesUtil.getUserFromSharedPreferences();


        // Load Toolbar e Navigation Drawer
        this.mToolbar = (Toolbar) findViewById(R.id.activity_perfil_toolbar);
        if (this.mToolbar != null) {
            setSupportActionBar(this.mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.activity_perfil_fragment_container);
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_perfil_drawer_layout);
        drawerFragment.setUp(this.mDrawerLayout, this.mToolbar);

        this.tvNome = (TextView) findViewById(R.id.activity_perfil_text_view_nome);
        this.tvEmail = (TextView) findViewById(R.id.activity_perfil_text_view_email);
        this.tvSituacao = (TextView) findViewById(R.id.activity_perfil_text_view_situacao);

        this.listViewProjetos = (ListView) findViewById(R.id.activity_perfil_list_view_projetos);

//        this.editSenhaAtual = (EditText) findViewById(R.id.activity_perfil_edit_text_senha_atual);
//        this.editSenhaNova = (EditText) findViewById(R.id.activity_perfil_edit_text_senha_nova);
//        this.editSenhaRepetir = (EditText) findViewById(R.id.activity_perfil_edit_text_senha_nova_repetir);
        this.btAlterarSenha = (FloatingActionButton) findViewById(R.id.activity_perfil_floating_button_alterar);
        this.btAlterarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snack(btAlterarSenha, getResources().getString(R.string.activity_perfil_button_floationg_msg_senha_alterada));
            }
        });


        this.callJson = new CallJsonNetwork();
        callJson.callJsonObjectGet(VolleySingleton.URL_GET_USER_ID + this.user.getId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    userEntity = new ParseUser().jsonObjectToUserEntity(response);
                    tvNome.setText(userEntity.getName());
                    tvEmail.setText(userEntity.getUserName());
                    tvSituacao.setText((userEntity.isActivite() == true ? "true" : "false"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        this.callJson.callJsonObjectGet(VolleySingleton.URL_GET_PROJECT_USER_ID + this.user.getId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    ParseProject parse = new ParseProject();
                    listProjectEntity = parse.parseJsonToProjectEntity(response);
                    listProjectString = parse.parseListProjectEntityToString(listProjectEntity);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listProjectString);
                    listViewProjetos.setAdapter(adapter);

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
