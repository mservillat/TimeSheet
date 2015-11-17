package br.com.mowa.timesheet.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import br.com.mowa.timesheet.dialog.LoginEsqueciASenhaDialogFragment;
import br.com.mowa.timesheet.model.UserModel;
import br.com.mowa.timesheet.network.CallJsonNetwork;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.parse.ParseSessions;
import br.com.mowa.timesheet.timesheet.R;
import br.com.mowa.timesheet.utils.SharedPreferencesUtil;


public class LoginActivity extends BaseActivity implements ParseSessions.OnParseFinishListener {
    private EditText editEmail;
    private EditText editSenha;
    private Button btLogin;
    private UserModel user;
    private ProgressDialog progress;
    private CallJsonNetwork callJson;
    private TextView tvEsqueceuASenha;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.callJson = new CallJsonNetwork();
        this.editEmail = (EditText)findViewById(R.id.activity_login_edit_text_email);
        this.editSenha = (EditText)findViewById(R.id.activity_login_edit_text_senha);
        this.btLogin = (Button) findViewById(R.id.activity_login_botao_entrar);
        this.progress = createProgressDialog("Iniciando sessão", "Conectando", true, true);


        UserModel mUser = SharedPreferencesUtil.getUserFromSharedPreferences();

        if (mUser != null) {
            // Usuário já está logado

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();

        } else {
            // Usuário não está logado

            btLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progress.show();
                    JSONObject requestBody = new JSONObject();
                    try {
                        if (isValidEmail(editEmail.getText().toString())) {
                            requestBody.put("username", editEmail.getText().toString());
                        }
                        if (isValidPassword(editSenha.getText().toString())) {
                            requestBody.put("password", editSenha.getText().toString());
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    logar(requestBody);

                }
            });
        }

        this.tvEsqueceuASenha = (TextView) findViewById(R.id.activity_login_text_view_esqueceu_senha);
        this.tvEsqueceuASenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginEsqueciASenhaDialogFragment esqueceuASenhaDialog = new LoginEsqueciASenhaDialogFragment();
                FragmentManager fm = getSupportFragmentManager();
                esqueceuASenhaDialog.show(fm, "EsqueceuASenhaDialog");
            }
        });



    }



    @Override
    public void onParseFinishListener(UserModel user) {
        SharedPreferencesUtil.setUserInSharedPreferences(user);
    }


    /**
     * Metodo que faz a chamada POST PARA criar a sessão
     * @param requestBody Json com usuario e senha digitado
     */
    private void logar(JSONObject requestBody) {
        callJson.callJsonObjectPost(VolleySingleton.URL_POST_CREATE_SESSIONS, requestBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                ParseSessions parse = new ParseSessions();
                parse.parseJsonSessionsToUserModel(response, LoginActivity.this);

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                progress.dismiss();
                startActivity(intent);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toast(getResources().getString(R.string.activity_login_toast_usuario_ou_senha_invalido));
                btLogin.setEnabled(true);
            }
        });
    }


    /**
     * Verifica se o email é valido
     * @param email email digitado pelo usuário
     * @return caso o email seja valido o retorno será true
     */
    private boolean isValidEmail(String email) {
        Pattern emailPattern = Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$");
        if (emailPattern.matcher(email).matches()) {
            return true;
        }
        editEmail.setError(getResources().getString(R.string.activity_login_set_error_email_invalido));
//        btLogin.setEnabled(true);
        progress.dismiss();
        return false;

    }


    /**
     * Verifica se a senha é valida (a senha deve ter mais de 5 digitos)
     * @param password senha digitada pelo usuário
     * @return caso a senha seja valida o retorno será true
     */
    private boolean isValidPassword(String password) {
        if (password.length() >5 ) {
            Pattern passwordPattern = Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789]*");
            if(!(passwordPattern.matcher(password).matches())) {
                editSenha.setError(getResources().getString(R.string.activity_login_set_error_caractere_invalido));
//                btLogin.setEnabled(true);
                progress.dismiss();
                return false;
            }
            return true;
        }
        editSenha.setError(getResources().getString(R.string.activity_login_set_error_minimo_caractere));
//        btLogin.setEnabled(true);
        progress.dismiss();
        return false;
    }

}
