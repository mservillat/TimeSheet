package br.com.mowa.timesheet.activity;

import android.os.Bundle;

import br.com.mowa.timesheet.timesheet.R;

public class PerfilActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Load Toolbar e Navigation Drawer
        setUpToolbar();
        setUpNavigationDrawer(R.id.activity_perfil_drawer_layout, R.id.activity_perfil_menu_lateral);
    }

}
