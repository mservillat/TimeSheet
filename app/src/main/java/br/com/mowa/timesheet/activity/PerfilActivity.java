package br.com.mowa.timesheet.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import br.com.mowa.timesheet.fragment.NavigationDrawerFragment;
import br.com.mowa.timesheet.timesheet.R;

public class PerfilActivity extends BaseActivity {
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private FloatingActionButton btAlterarSenha;
    private EditText editSenhaNova;
    private EditText editSenhaAtual;
    private EditText editSenhaRepetir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Load Toolbar e Navigation Drawer
        this.mToolbar = (Toolbar) findViewById(R.id.activity_perfil_toolbar);
        if (this.mToolbar != null) {
            setSupportActionBar(this.mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.activity_perfil_fragment_container);
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_perfil_drawer_layout);
        drawerFragment.setUp(this.mDrawerLayout, this.mToolbar);

        this.editSenhaAtual = (EditText) findViewById(R.id.activity_perfil_edit_text_senha_atual);
        this.editSenhaNova = (EditText) findViewById(R.id.activity_perfil_edit_text_senha_nova);
        this.editSenhaRepetir = (EditText) findViewById(R.id.activity_perfil_edit_text_senha_nova_repetir);
        this.btAlterarSenha = (FloatingActionButton) findViewById(R.id.activity_perfil_floating_button_alterar);
        this.btAlterarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snack(btAlterarSenha, getResources().getString(R.string.activity_perfil_button_floationg_msg_senha_alterada));
            }
        });

    }

}
