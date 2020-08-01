package com.lakhlifi.blog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lakhlifi.blog.Fragments.SinInFragment;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer,new SinInFragment()).commit();
    }
}