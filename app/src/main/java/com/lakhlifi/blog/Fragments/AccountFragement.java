package com.lakhlifi.blog.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lakhlifi.blog.R;

public class AccountFragement extends Fragment {
    View view;

    public AccountFragement(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.layout_home,container,false);
        init();
        return view;
    }

    private void init() {
    }
}
