package br.com.mowa.timesheet.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import br.com.mowa.timesheet.activity.HomeActivity;
import br.com.mowa.timesheet.activity.PerfilActivity;
import br.com.mowa.timesheet.adapter.NavDrawerMenuAdapter;
import br.com.mowa.timesheet.adapter.NavDrawerMenuItem;
import br.com.mowa.timesheet.timesheet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {
    public static final String PREF_FILE_NAME="namePreference";
    public static final String KEY_USER_LEANERD_DRAWER= "use_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView listDrawer;
    private DrawerLayout mDrawerLayout;
    private View containerView;
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
                        replaceActivity(HomeActivity.class);
                        break;
                    case 1:
                        replaceActivity(PerfilActivity.class);
                        break;
                    default:
                        break;
                }


            }
        });

        return view;
    }

    public void setUp(int containerId, DrawerLayout drawerLayout, Toolbar toolbar) {
        this.containerView = getActivity().findViewById(containerId);
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
//            mDrawerLayout.openDrawer(containerView);
//        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    protected void replaceActivity(Class activity) {
        Intent intent = new Intent(getContext(), activity);
        if (activity == HomeActivity.class) {
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
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
