package br.com.mowa.timesheet.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import br.com.mowa.timesheet.fragment.NavigationDrawerFragment;
import br.com.mowa.timesheet.timesheet.R;

public class RegistrosActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.activity_registros_toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_registros_drawer_layout);
        NavigationDrawerFragment navDraFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.activity_registros_fragment_navigation_drawer_container);
        navDraFragment.setUp(drawerLayout, mToolbar);

    }
}
