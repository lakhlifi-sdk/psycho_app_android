package com.lakhlifi.blog.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lakhlifi.blog.Constant;
import com.lakhlifi.blog.Models.Post;
import com.lakhlifi.blog.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter  extends RecyclerView.Adapter<PostAdapter.PostHolder>{

    private Context context;
    private ArrayList<Post> list;

    public PostAdapter(Context context, ArrayList<Post> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post, parent, false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        Post post=list.get(position);

        Picasso.get().load(Constant.URL+"/storage/profiles/"+post.getUser().getPhoto()).into(holder.imagePostProfile);
        Picasso.get().load(Constant.URL+"/storage/posts/"+post.getPhoto()).into(holder.imagePostPhoto);


        //Picasso.get().load(Constant.URL+"storage/profiles/1595967967.jpg").into(holder.imagePostProfile);
        //Picasso.get().load(Constant.URL+"storage/profiles/"+post.getUser().getPhoto()).into(holder.imagePostProfile);
        //Picasso.get().load(Constant.URL+"storage/public/posts/"+post.getPhoto()).into(holder.imagePostPhoto);
       // Picasso.get().load(Constant.URL+"storage/publicprofiles/"+post.getUser().getPhoto()).into(holder.imagePostProfile);
        //Picasso.get().load("https://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png").into(holder.imagePostPhoto);



        holder.txtPostName.setText(post.getUser().getUserName()+" "+post.getUser().getLastName());
        holder.txtPostCommentsCount.setText("View all "+ post.getComments());
        holder.txtPostLikesCount.setText(post.getLikes()+" likes");
        holder.txtPostDesc.setText(post.getDesc());
        holder.txtPostDate.setText(post.getDate());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PostHolder extends RecyclerView.ViewHolder{

        private CircleImageView imagePostProfile;
        private TextView txtPostName,txtPostDate,txtPostDesc,txtPostLikesCount,txtPostCommentsCount;
        private ImageButton btnPostOption,btnPostLike,btnPostComment;
        private ImageView imagePostPhoto;


        public PostHolder(@NonNull View itemView) {
            super(itemView);
            imagePostProfile=itemView.findViewById(R.id.imagePostProfile);
            txtPostName=itemView.findViewById(R.id.txtPostName);
            txtPostDate=itemView.findViewById(R.id.txtPostDate);
            txtPostDesc=itemView.findViewById(R.id.txtPostDesc);
            txtPostLikesCount=itemView.findViewById(R.id.txtPostLikesCount);
            txtPostCommentsCount=itemView.findViewById(R.id.txtPostCommentsCount);
            btnPostOption=itemView.findViewById(R.id.btnPostOption);
            btnPostLike=itemView.findViewById(R.id.btnPostLike);
            btnPostComment=itemView.findViewById(R.id.btnPostComment);
            imagePostPhoto=itemView.findViewById(R.id.imagePostPhoto);


        }
    }
}
