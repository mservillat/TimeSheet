package br.com.mowa.timesheet.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.mowa.timesheet.TimeSheetApplication;
import br.com.mowa.timesheet.model.ProjectModel;
import br.com.mowa.timesheet.model.UserModel;

/**
 * Created by walky on 9/15/15.
 */
public class SharedPreferencesUtil {
    public static final String KEY_USER_LOGIN_PREFERENCE_USERNAME = "key_user_login_preference_username";
    public static final String KEY_LIST_PREFERENCE_PROJECT_MODEL = "key_list_preference_project_model";
    public static final String KEY_PROJECT_DATE_UPDATED = "key_project_date_updated";




    public static SharedPreferences.Editor getSharedPreferenceEdit(String key) {
        return TimeSheetApplication.getInstance().getApplicationContext().getSharedPreferences(key, Context.MODE_PRIVATE).edit();
    }

    public static SharedPreferences getContextSharedPreference(String key) {
        return TimeSheetApplication.getInstance().getApplicationContext().getSharedPreferences(key, Context.MODE_PRIVATE);
    }

    public static void deleteSharedPreferencesUser(String key) {
        SharedPreferences.Editor edit = getSharedPreferenceEdit(key);
        edit.clear().commit();
    }









    /**
     * Salva o objeto do tipo User no SharedPreferences
     * @param user objeto a ser salvo
     */
    public static void setUserInSharedPreferences(UserModel user) {
        SharedPreferences.Editor editor= getSharedPreferenceEdit(KEY_USER_LOGIN_PREFERENCE_USERNAME);
        editor.clear();
        editor.putString("username", user.getUserName());
        editor.putString("id", user.getId());
        editor.putString("token", user.getToken());
        editor.putString("updated_at", user.getUpdatedAt());
        editor.putString("name", user.getName());
        editor.commit();
    }

    /**
     * Retorna um objeto do tipo User com o usuário logado, caso não esteja logado, irá retornar null.
     * @return o usuário logado ou null caso não tenha
     */
    public static UserModel getUserFromSharedPreferences() {
        SharedPreferences shared = getContextSharedPreference(KEY_USER_LOGIN_PREFERENCE_USERNAME);

        if (shared.getString("username", null) != null) {
            String username = shared.getString("username",null);
            String id = shared.getString("id", null);
            String token = shared.getString("token", null);
            String name = shared.getString("name", null);
            String updatedAt = shared.getString("updated_at", null);

            UserModel user = new UserModel(id, username, token, name, updatedAt);
            return user;
        }
        return null;
    }


//    public static void setProjectDateUpdated(ProjectModel project) {
//        SharedPreferences.Editor editor = getSharedPreferenceEdit(KEY_PROJECT_DATE_UPDATED);
//        editor.clear();
//        editor.putString(project.getId(), project.getUpdatedAt());
//        editor.commit();
//    }
//
//    public static String getPorjectDateUpdated(ProjectModel project) {
//        SharedPreferences shared = getContextSharedPreference(KEY_PROJECT_DATE_UPDATED);
//
//        if (shared.getString(project.getId(), null) != null ) {
//            String updated = shared.getString(project.getId(), null);
//            return updated;
//        }
//        return null;
//    }


    public static void updateUserSharedPreference(UserModel user) {
        deleteSharedPreferencesUser(SharedPreferencesUtil.KEY_USER_LOGIN_PREFERENCE_USERNAME);
        setUserInSharedPreferences(user);
    }



    public static void setListProjectInSharedPreferences(List<ProjectModel> list) throws JSONException {
        SharedPreferences.Editor editor = getSharedPreferenceEdit(KEY_LIST_PREFERENCE_PROJECT_MODEL);
        Gson gson = new Gson();
        editor.putString("listProject", gson.toJson(list));
        editor.commit();


    }

    public static List<ProjectModel> getListProjectFromSharedPreferences() {
        SharedPreferences shared = getContextSharedPreference(KEY_LIST_PREFERENCE_PROJECT_MODEL);
        Gson gson = new Gson();
        String string = shared.getString("listProject", "");
        ProjectModel[] projectModels = gson.fromJson(string, ProjectModel[].class);
        return new ArrayList<>(Arrays.asList(projectModels));
    }
}
