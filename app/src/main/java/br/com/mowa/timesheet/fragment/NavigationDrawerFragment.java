package br.com.mowa.timesheet.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import br.com.mowa.timesheet.activity.HomeActivity;
import br.com.mowa.timesheet.activity.PerfilActivity;
import br.com.mowa.timesheet.activity.ProjectInformationActivity;
import br.com.mowa.timesheet.activity.ProjectsActivity;
import br.com.mowa.timesheet.activity.TasksActivity;
import br.com.mowa.timesheet.adapter.NavDrawerMenuAdapter;
import br.com.mowa.timesheet.adapter.NavDrawerMenuItem;
import br.com.mowa.timesheet.timesheet.R;

public class NavigationDrawerFragment extends Fragment {
    public static final String PREF_FILE_NAME="namePreference";
    public static final String KEY_USER_LEANERD_DRAWER= "use_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView listDrawer;
    private DrawerLayout mDrawerLayout;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        this.listDrawer = (ListView) view.findViewById(R.id.fragment_navigation_drawer_listview);
        final List<NavDrawerMenuItem> list = NavDrawerMenuItem.getList();
        this.listDrawer.setAdapter(new NavDrawerMenuAdapter(getActivity(), list));
        this.listDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(getContext(), HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        closeDrawer();
                        startActivity(intent);

                        break;
                    case 1:
                        if (!("br.com.mowa.timesheet.activity.PerfilActivity".equals(getActivity().getLocalClassName()))) {
                            Intent intenteOne = new Intent(getContext(), PerfilActivity.class);
                            closeDrawer();
                            startActivity(intenteOne);
                        }
                        closeDrawer();

                        break;
                    case 2:
                        if (!("br.com.mowa.timesheet.activity.TasksActivity".equals(getActivity().getLocalClassName()))) {
                            Intent intentTwo = new Intent(getContext(), TasksActivity.class);
                            closeDrawer();
                            startActivity(intentTwo);
                        }
                        closeDrawer();

                        break;
                    case 3:
                        if (!("br.com.mowa.timesheet.activity.ProjectsActivity".equals(getActivity().getLocalClassName()))){
                            Intent intentThree = new Intent(getContext(), ProjectsActivity.class);
                            closeDrawer();
                            startActivity(intentThree);
                        }
                        closeDrawer();

                        break;
                    case 4:
                        if (!("br.com.mowa.timesheet.activity.ProjectInformationActivity".equals(getActivity().getLocalClassName()))) {
                            Intent intentd = new Intent(getContext(), ProjectInformationActivity.class);
                            closeDrawer();
                            startActivity(intentd);
                        }
                        closeDrawer();
                        break;

                    default:
                        break;
                }


            }
        });

        return view;
    }

    public void setUp(DrawerLayout drawerLayout, Toolbar toolbar) {
        this.mDrawerLayout = drawerLayout;
        this.mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.navigation_drawer_aberto, R.string.navigation_drawer_fechado) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreference(getActivity(), KEY_USER_LEANERD_DRAWER, mUserLearnedDrawer +"");
                }
                getActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };
//        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
//            openDrawer();
//        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }
//    public void openDrawer() {
//        if (mDrawerLayout != null) {
//            mDrawerLayout.openDrawer(Gravity.START);
//        }
//    }

    public void closeDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPrefences(getActivity(), KEY_USER_LEANERD_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    public static void saveToPreference(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPrefences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }



}
