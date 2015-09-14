package br.com.mowa.timesheet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.com.mowa.timesheet.network.CustomJsonObjectRequest;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.timesheet.R;


public class LoginActivity extends BaseActivity {
    private EditText editEmail;
    private EditText editSenha;
    private Button btLogin;
    private RequestQueue mRequestQueue;
    private Map<String, String> params;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = (EditText)findViewById(R.id.activity_login_edit_text_email);
        editSenha = (EditText)findViewById(R.id.activity_login_edit_text_senha);
        btLogin = (Button) findViewById(R.id.activity_login_botao_entrar);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editEmail.getText().toString().equals("walky")) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    toast(R.string.erro_login);
                }
            }
        });

        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();
        url = "http://walkyteste.goldarkapi.com/sessions";
        callJsonObject();
    }

    private void callJsonObject(){
        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Request.Method.POST, url, getParams(), new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                toast("certo");
                //toast(response.getJSONArray("data").getJSONObject(0).optString("name"));
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toast("erro " + error.getMessage());
            }
        }
        );

        mRequestQueue.add(request);
    }


    private Map<String, String> getParams() {
        this.params = new HashMap<>();
        params.put("username", "walky.silva@mowa.com.br");
        params.put("password", "12345");
        return params;
    }

}
