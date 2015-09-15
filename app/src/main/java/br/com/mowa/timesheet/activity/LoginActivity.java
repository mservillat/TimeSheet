package br.com.mowa.timesheet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.mowa.timesheet.model.User;
import br.com.mowa.timesheet.network.CallJsonNetwork;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.timesheet.R;
import br.com.mowa.timesheet.utils.SharedPreferencesUtil;


public class LoginActivity extends BaseActivity {
    private EditText editEmail;
    private EditText editSenha;
    private Button btLogin;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = (EditText)findViewById(R.id.activity_login_edit_text_email);
        editSenha = (EditText)findViewById(R.id.activity_login_edit_text_senha);
        btLogin = (Button) findViewById(R.id.activity_login_botao_entrar);

        User mUser = SharedPreferencesUtil.getUserFromSharedPreferences();

        if (mUser != null) {
            // Usuário tá logado

        } else {
            // Usuário mão está logado

            btLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONObject requestBody = new JSONObject();
                    try {
                        requestBody.put("username", editEmail.getText().toString());
                        //editEmail.setError("Campo inválido");

                        requestBody.put("password", editSenha.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    CallJsonNetwork callJson = new CallJsonNetwork();
                    callJson.callJsonObjectPost(VolleySingleton.URL_POST_CREATE_SESSIONS, requestBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            toast("Logado com sucesso");
                            try {
                                String username = response.getString("username");
                                String id = response.getString("user_id");
                                String token = response.getString("token");
                                user = new User(username, id, token);

                                Log.d("walkyTeste", username + " / " + id + "/" + token);

                                SharedPreferencesUtil.setUserInSharedPreferences(user);

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                intent.putExtra("user", user);
                                startActivity(intent);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        }

    }
}
