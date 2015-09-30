package br.com.mowa.timesheet.utils;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.mowa.timesheet.TimeSheetApplication;
import br.com.mowa.timesheet.model.UserModel;

/**
 * Created by walky on 9/15/15.
 */
public class SharedPreferencesUtil {
    private static final String KEY_USER_LOGIN_PREFERENCE_USERNAME = "KEY_USER_LOGIN_PREFERENCE_USERNAME";
    /**
     * Retorna um objeto do tipo User com o usuário logado, caso não esteja logado, irá retornar null.
     * @return o usuário logado ou null caso não tenha
     */
    public static UserModel getUserFromSharedPreferences() {
        SharedPreferences shared = getContextSharedPreference(KEY_USER_LOGIN_PREFERENCE_USERNAME);

        if (null != shared.getString("username", null)) {
            String username = shared.getString("username",null);
            String id = shared.getString("id", null);
            String token = shared.getString("token", null);
            UserModel user = new UserModel(username, id, token);
            return user;
        }
        return null;
    }

    /**
     * Salva o objeto do tipo User no SharedPreferences
     * @param user objeto a ser salvo
     */
    public static void setUserInSharedPreferences(UserModel user) {
        SharedPreferences.Editor editor= getSharedPreferenceEdit(KEY_USER_LOGIN_PREFERENCE_USERNAME);
        editor.putString("username", user.getUserName());
        editor.putString("id", user.getId());
        editor.putString("token", user.getToken());
        editor.commit();
    }

    public static SharedPreferences.Editor getSharedPreferenceEdit(String key) {
        return TimeSheetApplication.getInstance().getApplicationContext().getSharedPreferences(key, Context.MODE_PRIVATE).edit();

    }

    public static SharedPreferences getContextSharedPreference(String key) {
        return TimeSheetApplication.getInstance().getApplicationContext().getSharedPreferences(key, Context.MODE_PRIVATE);

    }

    public static void deleteSharedPreferences() {
        SharedPreferences.Editor edit = getSharedPreferenceEdit(KEY_USER_LOGIN_PREFERENCE_USERNAME);
        edit.clear().commit();
    }

}
