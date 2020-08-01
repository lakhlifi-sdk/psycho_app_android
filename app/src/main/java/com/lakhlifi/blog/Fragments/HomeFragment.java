package com.lakhlifi.blog.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

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
import com.lakhlifi.blog.AuthActivity;
import com.lakhlifi.blog.Constant;
import com.lakhlifi.blog.HomeActivity;
import com.lakhlifi.blog.Models.Post;
import com.lakhlifi.blog.Models.User;
import com.lakhlifi.blog.R;
import com.lakhlifi.blog.UserInfoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {
    View view;
    private RecyclerView recyclerView;
    private ArrayList<Post> arrayListPost;
    private SwipeRefreshLayout refreshLayout;
    private PostAdapter postsAdapter;
    //private Toolbar toolbar;
    private MaterialToolbar toolbar;
    private  SharedPreferences sharedPreferences;





    public HomeFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view= inflater.inflate(R.layout.layout_home,container,false);
         init();
         return view;
    }

    private void init() {
        sharedPreferences=getActivity().getApplicationContext().getSharedPreferences("user",getContext().MODE_PRIVATE);
        recyclerView=view.findViewById(R.id.recycleHome);
        refreshLayout=view.findViewById(R.id.swipHome);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        toolbar=view.findViewById(R.id.toolbarHome);
        ((HomeActivity)getContext()).setSupportActionBar(toolbar);
        getPost();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }
    public void getPost(){
        arrayListPost=new ArrayList<>();
        refreshLayout.setRefreshing(true);

           StringRequest request=new StringRequest(Request.Method.GET,Constant.POSTS,response -> {

               try {
                   JSONObject object = new JSONObject(response);
                   if(object.getBoolean("success")){

                       JSONArray array=new JSONArray(object.getString("posts"));

                       for (int i = 0; i <array.length() ; i++) {
                           JSONObject postObject=array.getJSONObject(i);
                           JSONObject userObject=postObject.getJSONObject("user");
                           User user=new User();
                           user.setId(userObject.getInt("id"));
                           user.setLastName(userObject.getString("lastname"));
                           user.setUserName(userObject.getString("name"));
                           user.setPhoto(userObject.getString("photo"));


                           Post post=new Post();
                           post.setId(postObject.getInt("id"));
                           post.setUser(user);
                           post.setComments(postObject.getInt("commentsCount"));
                           post.setLikes(postObject.getInt("likesCount"));
                           post.setDesc(postObject.getString("desc"));
                           post.setDate(postObject.getString("created_at"));
                           post.setSelfLikes(postObject.getBoolean("selfLike"));
                           post.setPhoto(postObject.getString("photo"));
                           arrayListPost.add(post);
                       }

                   }
                   postsAdapter = new PostAdapter(getContext(),arrayListPost);
                   recyclerView.setAdapter(postsAdapter);


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

        RequestQueue queue=Volley.newRequestQueue(getContext());
        queue.add(request);
    }
}
