package com.lakhlifi.blog.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.lakhlifi.blog.AuthActivity;
import com.lakhlifi.blog.Constant;
import com.lakhlifi.blog.HomeActivity;
import com.lakhlifi.blog.R;
import com.lakhlifi.blog.UserInfoActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SinUpFragment extends Fragment {
    private View view;

    private TextView linkSinIn;
    private Button btnSinUp;
    private TextInputLayout txtlayoutEmailsinUp,txtlayoutPasswordsinUp,layoutConfirm;
    private TextInputEditText txtEmailSinUp,txtPasswordSinUp,txtPasswordConfirm;
    private ProgressDialog dialog;



    public SinUpFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.layout_sin_up,container,false);
         init();
        return view;
    }

    private void init() {
        linkSinIn=view.findViewById(R.id.linkSinIn);
        btnSinUp=view.findViewById(R.id.btnSinUp);
        txtlayoutEmailsinUp=view.findViewById(R.id.txtlayoutEmailsinup);
        txtlayoutPasswordsinUp=view.findViewById(R.id.txtlayoutPasswordsinUp);
        txtEmailSinUp=view.findViewById(R.id.txtEmailSinup);
        txtPasswordSinUp=view.findViewById(R.id.txtPasswordSinUp);
        layoutConfirm=view.findViewById(R.id.txtlayoutPassworConfirmdsinUp);
        txtPasswordConfirm=view.findViewById(R.id.txtPassworConfirmdSinUp);
        //dialog
        dialog=new ProgressDialog(getContext());
        dialog.setCancelable(false);

        linkSinIn.setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer,new SinInFragment()).commit();
        });
        btnSinUp.setOnClickListener(view1 -> {

            if(validate()){
                register();
            }

        });

        txtEmailSinUp.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!txtEmailSinUp.getText().toString().isEmpty()){
                    txtlayoutEmailsinUp.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtPasswordSinUp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(txtPasswordSinUp.getText().toString().length()>=2){
                    txtlayoutPasswordsinUp.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txtPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(txtPasswordSinUp.getText().toString().equals(txtPasswordConfirm.getText().toString())){
                    layoutConfirm.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    public boolean validate(){
        if( txtEmailSinUp.getText().toString().isEmpty()){
            txtlayoutEmailsinUp.setErrorEnabled(true);
            txtlayoutEmailsinUp.setError("Email is Required !!");
            return false;
        }
        if( txtPasswordSinUp.getText().toString().length()<=2){
            txtlayoutPasswordsinUp.setErrorEnabled(true);
            txtlayoutPasswordsinUp.setError("Required  at least 8 characters!!");
            return false;
        }
        if(!txtPasswordSinUp.getText().toString().equals(txtPasswordConfirm.getText().toString())){
            layoutConfirm.setErrorEnabled(true);
            layoutConfirm.setError("The password confirm shoud be the same");
        }
        return true;
    }


    public void register(){
        dialog.setMessage("registring ...");
        dialog.show();

        StringRequest request= new StringRequest(Request.Method.POST, Constant.REGISTER, response -> {
            try {
                JSONObject object=new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONObject user= object.getJSONObject("user");
                    //make shared Preference user
                    SharedPreferences userPref=getActivity().getApplicationContext().getSharedPreferences("user",getContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor=userPref.edit();
                    editor.putString("token",object.getString("token"));

                    editor.putString("lastname",user.getString("lastname"));
                    editor.putString("name",user.getString("name"));
                    editor.putString("photo",user.getString("photo"));
                    editor.putString("role",user.getString("role"));
                    editor.apply();
                    editor.putBoolean("isLogin",true);
                    Intent intent=new Intent((AuthActivity)getContext(), UserInfoActivity.class);
                    startActivity(intent);

                    Toast.makeText(getContext(),"your data saved",Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(),"password or email wrong",Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();

        },error -> {
            error.printStackTrace();
            dialog.dismiss();
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map =new HashMap<>();
                map.put("email",txtEmailSinUp.getText().toString().trim());
                map.put("password",txtPasswordSinUp.getText().toString().trim());
                return map;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(request);

    }




}
