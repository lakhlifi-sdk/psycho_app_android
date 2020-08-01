package com.lakhlifi.blog.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SinInFragment extends Fragment {

    private View view;
    private TextView linkSinUp;
    private Button btnSinIn;
    private TextInputLayout txtlayoutEmailsinIn,txtlayoutPasswordsinIn;
    private TextInputEditText txtEmailSinIn,txtPasswordSinIn;
    ProgressDialog dialog;

    public SinInFragment(){}


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.layout_sin_in,container,false);
        init();
        return view;

    }

    private void init() {
        linkSinUp=view.findViewById(R.id.linkSinUp);
        btnSinIn=view.findViewById(R.id.btnSinIn);
        txtlayoutEmailsinIn=view.findViewById(R.id.txtlayoutEmailsinIn);
        txtlayoutPasswordsinIn=view.findViewById(R.id.txtlayoutPasswordsinIn);
        txtEmailSinIn=view.findViewById(R.id.txtEmailSinIn);
        txtPasswordSinIn=view.findViewById(R.id.txtPasswordSinIn);
        dialog=new ProgressDialog(getContext());
        dialog.setCancelable(false);

        linkSinUp.setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer,new SinUpFragment()).commit();
        });
        btnSinIn.setOnClickListener(view1 -> {

            if(validate()){
               login();
            }

        });
        txtEmailSinIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!txtEmailSinIn.getText().toString().isEmpty()){
                    txtlayoutEmailsinIn.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
}

    public boolean validate(){
       if( txtEmailSinIn.getText().toString().isEmpty()){
           txtlayoutEmailsinIn.setErrorEnabled(true);
           txtlayoutEmailsinIn.setError("Email is Required !!");
           return false;
       }

       return true;
    }

    public  void login(){
        dialog.setMessage("Login ...");
        dialog.show();

        StringRequest request= new StringRequest(Request.Method.POST, Constant.LOGIN,response -> {
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
                    editor.putBoolean("isLogin",true);
                    editor.apply();
                    //if successe
                    Intent intent=new Intent((AuthActivity)getContext(),HomeActivity.class);
                    startActivity(intent);

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
                HashMap<String, String>map =new HashMap<>();
                map.put("email",txtEmailSinIn.getText().toString().trim());
                map.put("password",txtPasswordSinIn.getText().toString().trim());
                return map;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(request);

    }


}
