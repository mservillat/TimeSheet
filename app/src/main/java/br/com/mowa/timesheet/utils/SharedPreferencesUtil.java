package br.com.mowa.timesheet.utils;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.mowa.timesheet.model.User;

/**
 * Created by walky on 9/15/15.
 */
public class SharedPreferencesUtil {
    private static final String KEY_USER_LOGIN_PREFERENCE_USERNAME = "KEY_USER_LOGIN_PREFERENCE_USERNAME";
    private static final String KEY_USER_LOGIN_PREFERENCE_ID = "KEY_USER_LOGIN_PREFERENCE_ID";
    private static final String KEY_USER_LOGIN_PREFERENCE_TOKEN = "KEY_USER_LOGIN_PREFERENCE_TOKEN";
    /**
     * Retorna um objeto do tipo User com o usuário logado, caso não esteja logado, irá retornar null.
     * @return o usuário logado ou null caso não tenha
     */
    public static User getUserFromSharedPreferences() {

        return null;
    }

    /**
     * Salva o objeto do tipo User no SharedPreferences
     * @param user objeto a ser salvo
     */
    public static void setUserInSharedPreferences(Context context, User user) {
        SharedPreferences sharedPref;
        SharedPreferences.Editor editor;
        sharedPref =  context.getSharedPreferences(KEY_USER_LOGIN_PREFERENCE_USERNAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        editor.putString(KEY_USER_LOGIN_PREFERENCE_USERNAME, user.getUsername());
        editor.putString(KEY_USER_LOGIN_PREFERENCE_ID, user.getId());
        editor.putString(KEY_USER_LOGIN_PREFERENCE_TOKEN, user.getToken());
    }

}
