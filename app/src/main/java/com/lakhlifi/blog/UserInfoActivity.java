package com.lakhlifi.blog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends AppCompatActivity {

    private CircleImageView btnImageUpload,imgProfile;
    private TextInputLayout layoutUserNmae,layoutUserLstname;
    private TextInputEditText txtUserNameLastname,txtUserName;
    private Button btnSaveInfo;
    private static final int GALLERY_ADD_PROFILE=1;
    private Bitmap bitmap=null;
    private SharedPreferences userPref;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        init();
    }

    private void init() {
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        userPref=getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        btnImageUpload = findViewById(R.id.btnImageUpload);
        imgProfile = findViewById(R.id.imgProfile);
        layoutUserNmae = findViewById(R.id.layoutUserNmae);
        layoutUserLstname = findViewById(R.id.layoutUserLstname);
        txtUserNameLastname = findViewById(R.id.txtUserNameLastname);
        layoutUserNmae = findViewById(R.id.layoutUserNmae);
        txtUserName = findViewById(R.id.txtUserName);
        btnSaveInfo = findViewById(R.id.btnSaveInfo);




        btnImageUpload.setOnClickListener(view -> {
            Intent i= new Intent(Intent.ACTION_PICK);
            i.setType("image/*");
            startActivityForResult(i,GALLERY_ADD_PROFILE);
        });

        btnSaveInfo.setOnClickListener(view -> {

            if(validate()){
                saveUserInfo();

            }


        });
        txtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!txtUserName.getText().toString().isEmpty()){
                    layoutUserNmae.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txtUserNameLastname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!txtUserNameLastname.getText().toString().isEmpty()){
                    layoutUserLstname.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_ADD_PROFILE && resultCode==RESULT_OK){
            Uri imgUri =data.getData();
            imgProfile.setImageURI(imgUri);
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





    public boolean validate(){
        if( txtUserName.getText().toString().isEmpty()){
            layoutUserNmae.setErrorEnabled(true);
            layoutUserNmae.setError("First name is Required !!");
            return false;
        }
        if( txtUserNameLastname.getText().toString().isEmpty()){
            layoutUserLstname.setErrorEnabled(true);
            layoutUserLstname.setError("last name is Required !!");
            return false;
        }

        return true;
    }


    public void saveUserInfo(){
        dialog.setMessage("Login ...");
        dialog.show();

        String name=txtUserName.getText().toString();
        String lastname=txtUserNameLastname.getText().toString();
        StringRequest request= new StringRequest(Request.Method.POST, Constant.SAVE_USER_INFO,response -> {

            try {
                JSONObject object=new JSONObject(response);
                if(object.getBoolean("success")){
                    SharedPreferences userPref=getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=userPref.edit();
                    editor.putString("photo",object.getString("photo"));
                    editor.apply();
                    Intent homeActivity= new Intent(UserInfoActivity.this,HomeActivity.class);
                    startActivity(homeActivity);
                    finish();


                }
            } catch (JSONException e) {
                e.printStackTrace();

            }
            dialog.dismiss();

        },error -> {
            error.printStackTrace();
            dialog.dismiss();

        }){




            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map =new HashMap<>();
                String token = userPref.getString("token","");
                map.put("Authorization","Bearer"+token  );

                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map =new HashMap<>();

                map.put("name",name);
                map.put("lastname",lastname);
                map.put("photo",bitmapToString(bitmap));
                return map;
            }


        };

        RequestQueue queue= Volley.newRequestQueue(UserInfoActivity.this);
        queue.add(request);
    }

    private String bitmapToString(Bitmap bitmap) {
        if(bitmap!=null){
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte[] array=byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(array,Base64.DEFAULT);
        }
        return "";
    }

}