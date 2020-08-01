package com.lakhlifi.blog.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.MaterialToolbar;
import com.lakhlifi.blog.Adapters.PostAdapter;
import com.lakhlifi.blog.Adapters.UserAdaptaterTest;
import com.lakhlifi.blog.Constant;
import com.lakhlifi.blog.HomeActivity;

import com.lakhlifi.blog.Models.User;
import com.lakhlifi.blog.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserFargmentTest  extends Fragment {

        View view;
        private RecyclerView recyclerViewTest;
        private ArrayList<User> arrayListUser;
        private SwipeRefreshLayout refreshLayout;
        private UserAdaptaterTest UsersAdapter;
        //private Toolbar toolbar;
        private MaterialToolbar toolbar;
        private SharedPreferences sharedPreferences;





    public UserFargmentTest(){}

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.layout_home,container,false);
        init();
        return view;
    }

        private void init() {
        sharedPreferences=getActivity().getApplicationContext().getSharedPreferences("user",getContext().MODE_PRIVATE);
        recyclerViewTest=view.findViewById(R.id.recycleHome);
        refreshLayout=view.findViewById(R.id.swipHome);
        recyclerViewTest.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTest.setHasFixedSize(true);
        toolbar=view.findViewById(R.id.toolbarHomeTest);
        ((HomeActivity)getContext()).setSupportActionBar(toolbar);
        getUsers();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }
        public void getUsers(){
        arrayListUser=new ArrayList<>();
        refreshLayout.setRefreshing(true);

        StringRequest request=new StringRequest(Request.Method.GET, Constant.GET_USERS, response -> {

            try {
                JSONObject object = new JSONObject(response);
                if(object.getBoolean("success")){

                    JSONArray array=new JSONArray(object.getString("user"));

                    for (int i = 0; i <array.length() ; i++) {
                        JSONObject userObject=array.getJSONObject(i);
                        //JSONObject postObject=array.getJSONObject(i);
                        //JSONObject userObject=postObject.getJSONObject("user");
                        User user=new User();
                        user.setId(userObject.getInt("id"));
                        user.setLastName(userObject.getString("lastname"));
                        user.setUserName(userObject.getString("name"));
                        user.setPhoto(userObject.getString("photo"));
                        arrayListUser.add(user);
                    }

                }

                UsersAdapter = new UserAdaptaterTest(getContext(),arrayListUser);
                recyclerViewTest.setAdapter(UsersAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            refreshLayout.setRefreshing(false);
        }, error -> {
            error.printStackTrace();
            refreshLayout.setRefreshing(false);

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token= sharedPreferences.getString("token","");
                HashMap<String,String> map= new HashMap<>();
                map.put("Authorization","Bearer"+token);
                return map;
            }

        };

        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(request);
    }


}
