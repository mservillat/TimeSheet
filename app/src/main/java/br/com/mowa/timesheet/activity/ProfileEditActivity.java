package br.com.mowa.timesheet.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.regex.Pattern;

import br.com.mowa.timesheet.dialog.HomeExitDialogFragment;
import br.com.mowa.timesheet.dialog.ProfileEditGalleryOrCamera;
import br.com.mowa.timesheet.model.UserModel;
import br.com.mowa.timesheet.network.CallJsonNetwork;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.timesheet.R;
import br.com.mowa.timesheet.utils.ImageStorage;
import br.com.mowa.timesheet.utils.ImageUtils;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by walky on 10/26/15.
 */
public class ProfileEditActivity extends BaseActivity {
    private static int RESULT_LOAD_IMG = 1;
    private static int CAMERA_REQUEST = 1888;

    private UserModel user;
    private EditText etPasswordCurrent;
    private EditText etPasswordNew;
    private EditText etPasswordConfirm;
    private EditText etName;
    private EditText etEmail;
    private ImageView ivImagePerfil;
    private CircleImageView circleImageProfile;
    private FloatingActionButton floatingButton;
    private Button btAlterarImage;
    private JSONObject requestBody;
    private ProgressDialog progress;
    public static final String BROADCAST = "atualiza_perfil";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        createToolbar(R.id.activity_profile_edit_toolbar);


        progress = createProgressDialog("Loading", "loading new image", true, true);
        Gson gson = new Gson();
        this.user = gson.fromJson(getIntent().getStringExtra(PerfilActivity.KEY_INTENT_PUT_EXTRA_USER), UserModel.class);


        this.requestBody = new JSONObject();

        this.etPasswordCurrent = (EditText) findViewById(R.id.activity_profile_edit_edit_text_password_current);
        this.etPasswordNew = (EditText) findViewById(R.id.activity_profile_edit_edit_text_password_new);
        this.etPasswordConfirm = (EditText) findViewById(R.id.activity_profile_edit_edit_text_password_confirm);

        this.ivImagePerfil = (ImageView) findViewById(R.id.activity_profile_edit_circle_photo);
        this.btAlterarImage = (Button) findViewById(R.id.activity_profile_edit_button_alterar_img);
        this.btAlterarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileEditGalleryOrCamera dialog = new ProfileEditGalleryOrCamera();
                FragmentManager fm = getSupportFragmentManager();
                dialog.show(fm, "dialog");
            }
        });

        this.circleImageProfile = (CircleImageView) findViewById(R.id.activity_profile_edit_circle_photo);
        ViewGroup.LayoutParams layoutParams = this.circleImageProfile.getLayoutParams();
        this.circleImageProfile.setImageBitmap(ImageStorage.getImage(this, user.getProfilePicture(), layoutParams.width, layoutParams.height));

        this.etName = (EditText) findViewById(R.id.activity_profile_edit_edit_text_name);
        this.etName.setText(user.getName());


        this.etEmail = (EditText) findViewById(R.id.activity_profile_edit_edit_text_email);
        this.etEmail.setText(user.getUserName());

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
        CallJsonNetwork callJson = new CallJsonNetwork();
        callJson.callJsonObjectPost(VolleySingleton.URL_POST_CREATE_SESSIONS, requestBody, new Response.Listener<JSONObject>() {
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
                Toast.makeText(getContext(), "Deu ruim confirmando a senha atual", Toast.LENGTH_LONG).show();
            }
        });
    }




    private void callJsonAlteraSenha() {
        CallJsonNetwork callJson = new CallJsonNetwork();
        callJson.callJsonImagePut(VolleySingleton.URL_PUT_USERS_ID + this.user.getId(), requestBody, new Response.Listener<JSONObject>() {
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


    private void callJsonUpdateImageProfile(String imageDecodedString) {
        new CallJsonNetwork().callJsonImagePut(VolleySingleton.URL_PUT_USERS_ID + user.getId() + VolleySingleton.URL_PROFILE_PICTURE, imageDecodedString, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progress.dismiss();
                snack(floatingButton, "Image updaded");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                snack(floatingButton, "error image updaded");
            }
        });
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST || requestCode == RESULT_LOAD_IMG) {
                Bitmap bitmap = getBitmapFromData(data);
                ivImagePerfil.setImageBitmap(bitmap);
                String imageDecodedString = getBitmapRequestBody(bitmap);
                callJsonUpdateImageProfile(imageDecodedString);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public static Bitmap getBitmapFromData(Intent data) {
        Bitmap photo = null;
        Uri photoUri = data.getData();
        if (photoUri != null) {
            photo = BitmapFactory.decodeFile(photoUri.getPath());
        }
        if (photo == null) {
            Bundle extra = data.getExtras();
            if (extra != null) {
                photo = (Bitmap) extra.get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            }
        }

        return photo;
    }



    private Intent getCropIntent(Intent intent) {
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        return intent;
    }


    public void onClickGallery() {
        progress.show();
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(getCropIntent(intent), RESULT_LOAD_IMG);
    }

    public void onclickCamera() {
        progress.show();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(getCropIntent(intent), CAMERA_REQUEST);

    }

    private String getBitmapRequestBody(Bitmap bitmap) {
        String stringBitmap = ImageUtils.bitmapToBase64(bitmap);

        return  stringBitmap;

    }

    @Override
    public void onBackPressed() {
        sendBroadcast(new Intent(BROADCAST));
        super.onBackPressed();
    }
}