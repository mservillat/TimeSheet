package br.com.mowa.timesheet.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import br.com.mowa.timesheet.dialog.HomeExitDialogFragment;
import br.com.mowa.timesheet.model.UserModel;
import br.com.mowa.timesheet.network.CallJsonNetwork;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.timesheet.R;
import br.com.mowa.timesheet.utils.SharedPreferencesUtil;

/**
 * Created by walky on 10/26/15.
 */
public class ProfileEditActivity extends BaseActivity {
    private EditText etPasswordCurrent;
    private EditText etPasswordNew;
    private EditText etPasswordConfirm;
    private JSONObject requestBody;
    private UserModel user;
    private CallJsonNetwork callJson;
    private FloatingActionButton floatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        createToolbar(R.id.activity_profile_edit_toolbar);


        this.user = SharedPreferencesUtil.getUserFromSharedPreferences();
        this.requestBody = new JSONObject();
        this.callJson = new CallJsonNetwork();

        this.etPasswordCurrent = (EditText) findViewById(R.id.activity_profile_edit_edit_text_password_current);
        this.etPasswordNew = (EditText) findViewById(R.id.activity_profile_edit_edit_text_password_new);
        this.etPasswordConfirm = (EditText) findViewById(R.id.activity_profile_edit_edit_text_password_confirm);

        this.floatingButton = (FloatingActionButton) findViewById(R.id.activity_profile_edit_floating_button_edit);
        this.floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyEqualNewPassword();
            }
        });

    }


    private void verifyEqualNewPassword() {
        if (isValidPassword(etPasswordNew.getText().toString())) {
            if (etPasswordNew.getText().toString().equals(etPasswordConfirm.getText().toString())) {
                try {
                    requestBody.put("username", this.user.getUserName());
                    requestBody.put("password", etPasswordCurrent.getText().toString());
                    verifyPasswordCurrent();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                etPasswordConfirm.setError("senha diferente");
            }
        }
    }


    private boolean isValidPassword(String password) {
        if (password.length() > 5) {
            Pattern passwordPattern = Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789]*");
            if (!(passwordPattern.matcher(password).matches())) {
                etPasswordNew.setError(getResources().getString(R.string.activity_profile_edit_caractere_invalido));
                return false;
            }
            return true;
        }
        etPasswordNew.setError(getResources().getString(R.string.activity_profile_edit_set_error_minimum_character));
        return false;
    }


    private void verifyPasswordCurrent() {
        this.callJson = new CallJsonNetwork();
        this.callJson.callJsonObjectPost(VolleySingleton.URL_POST_CREATE_SESSIONS, requestBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    requestBody = new JSONObject();
                    requestBody.put("password", etPasswordNew.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callJsonAlteraSenha();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Deu ruim confirmando a senha atual" , Toast.LENGTH_LONG).show();
            }
        });
    }




    private void callJsonAlteraSenha() {
        this.callJson.callJsonObjectPut(VolleySingleton.URL_PUT_USERS_ID + this.user.getId(), requestBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                snack(floatingButton, getResources().getString(R.string.activity_profile_edit_password_changed_success));
                HomeExitDialogFragment.setDeleteSharedPreference(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "deu ruim para trocar", Toast.LENGTH_LONG).show();
            }
        });

    }

}