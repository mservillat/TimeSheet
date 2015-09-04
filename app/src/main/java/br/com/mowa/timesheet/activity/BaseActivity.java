package br.com.mowa.timesheet.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.mowa.timesheet.adapter.NavDrawerMenuAdapter;
import br.com.mowa.timesheet.adapter.NavDrawerMenuItem;
import br.com.mowa.timesheet.timesheet.R;

/**
 * Created by walky on 8/31/15.
 */
public class BaseActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ListView listDrawer;
    private ActionBarDrawerToggle drawerToggle;

    protected void setUpNavigationDrawer(int xmlDrawerLayout, int xmlListView) {

        this.drawerLayout = (DrawerLayout) findViewById(xmlDrawerLayout);
        this.listDrawer = (ListView) findViewById(xmlListView);

        final List<NavDrawerMenuItem> list = NavDrawerMenuItem.getList();
        this.listDrawer.setAdapter(new NavDrawerMenuAdapter(this, list));
        this.listDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0  : replaceActivity(HomeActivity.class);
                        break;
                    case 1 : replaceActivity(PerfilActivity.class);
                        break;
                    default:
                        break;
                }


                drawerLayout.closeDrawer(listDrawer);
            }
        });

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, null, R.string.navigation_drawer_aberto, R.string.navigation_drawer_fechado) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);



    }

    private void replaceActivity(Class activity) {
        Intent intent = new Intent(getContext(), activity);
        startActivity(intent);
        finish();

    }


    protected void setUpToolbar() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.layout_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    protected Context getContext() {
        return this;
    }

    protected Activity getActivity() {
        return this;
    }

    protected void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void toast(int msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
