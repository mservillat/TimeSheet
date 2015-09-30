package br.com.mowa.timesheet.dialog;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;


import br.com.mowa.timesheet.model.UserModel;
import br.com.mowa.timesheet.network.CallJsonNetwork;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.timesheet.R;
import br.com.mowa.timesheet.utils.SharedPreferencesUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilAlterarSenhaDialogFragment extends DialogFragment implements View.OnClickListener{
    private Button btConfirmar;
    private Button btCancelar;
    private CallJsonNetwork callJson;
    private EditText etSenhaAtual;
    private EditText etSenhaNova;
    private EditText etSenhaRepetir;
    private JSONObject requestBody;
    private UserModel user;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_perfil_alterar_senha_dialog, null);


        this.user = SharedPreferencesUtil.getUserFromSharedPreferences();


        this.btConfirmar = (Button) view.findViewById(R.id.dialog_fragment_perfil_alterar_senha_button_confirmar);
        this.btConfirmar.setOnClickListener(this);
        this.btCancelar = (Button) view.findViewById(R.id.dialog_fragment_perfil_alterar_senha_button_cancelar);
        this.btCancelar.setOnClickListener(this);



        this.etSenhaAtual = (EditText) view.findViewById(R.id.dialog_fragment_perfil_edit_text_senha_atual);
        this.etSenhaNova = (EditText) view.findViewById(R.id.dialog_fragment_perfil_edit_text_senha_nova);
        this.etSenhaRepetir = (EditText) view.findViewById(R.id.dialog_fragment_perfil_edit_text_senha_repetir);


        this.requestBody = new JSONObject();
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.dialog_fragment_perfil_alterar_senha_button_confirmar) {
            if (isValidPassword(etSenhaNova.getText().toString())) {
                if (etSenhaNova.getText().toString().equals(etSenhaRepetir.getText().toString())) {
                    try {
                        requestBody.put("username", this.user.getUserName());
                        requestBody.put("password", etSenhaAtual.getText().toString());
                        callJsonVerificaSenhaAtual();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    etSenhaRepetir.setError("senha diferente");
                }
            }

        } else {
                dismiss();
        }
    }


    /**
     * Chama a API para verificar se a senha atual esta correta
     */
    private void callJsonVerificaSenhaAtual() {
        this.callJson = new CallJsonNetwork();
        this.callJson.callJsonObjectPost(VolleySingleton.URL_POST_CREATE_SESSIONS, requestBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    requestBody = new JSONObject();
                    requestBody.put("password", etSenhaNova.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callJsonAlteraSenha();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Deu ruim", Toast.LENGTH_LONG).show();
                dismiss();
            }
        });
    }

    /**
     * Atualiza na API a nova senha
     */
    private void callJsonAlteraSenha() {
        this.callJson.callJsonObjectPut(VolleySingleton.URL_PUT_USERS_ID + this.user.getId(), requestBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getContext(), "Senha alterada com sucesso", Toast.LENGTH_LONG).show();
                HomeExitDialogFragment.setDeleteSharedPreference(true);
                dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "deu ruim", Toast.LENGTH_LONG).show();
                dismiss();
            }
        });
        
    }


    /**
     * Verifica se a senha segue os padrões minimos
     * @param password edit text da nova senha a ser validado.
     * @return true caso a senha esteja nos padrões....false se a senha não atende os padrões.
     */
    private boolean isValidPassword(String password) {
        if (password.length() >5 ) {
            Pattern passwordPattern = Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789]*");
            if(!(passwordPattern.matcher(password).matches())) {
                etSenhaNova.setError(getResources().getString(R.string.dialog_fragment_perfil_set_error_caractere_invalido));
                return false;
            }
            return true;
        }
        etSenhaNova.setError(getResources().getString(R.string.dialog_fragment_perfil_set_error_minimo_caractere));
        return false;
    }


}
