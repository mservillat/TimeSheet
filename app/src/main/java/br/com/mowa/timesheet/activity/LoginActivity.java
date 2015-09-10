package br.com.mowa.timesheet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.mowa.timesheet.timesheet.R;


public class LoginActivity extends BaseActivity {
    private EditText editEmail;
    private EditText editSenha;
    private Button btLogin;

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

    }


}
