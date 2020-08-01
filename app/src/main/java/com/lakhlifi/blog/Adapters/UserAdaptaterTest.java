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
import com.lakhlifi.blog.Models.User;
import com.lakhlifi.blog.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdaptaterTest extends RecyclerView.Adapter<UserAdaptaterTest.UserTestHolder> {


        private Context context;
        private ArrayList<User> list;

    public UserAdaptaterTest(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

        @NonNull
        @Override
        public UserAdaptaterTest.UserTestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_infot_test, parent, false);
        return new UserAdaptaterTest.UserTestHolder(view);
    }

        @Override
        public void onBindViewHolder(@NonNull UserTestHolder holder, int position) {
        User user=list.get(position);
        Picasso.get().load(Constant.URL+"/storage/profiles/"+user.getPhoto()).into(holder.imagePostProfileTest);
        holder.txtPostNameTest.setText(user.getUserName()+" "+ user.getLastName());

    }

        @Override
        public int getItemCount() {
        return list.size();
    }

        class UserTestHolder extends RecyclerView.ViewHolder{

            private CircleImageView imagePostProfileTest;
            private TextView txtPostNameTest;



            public UserTestHolder(@NonNull View itemView) {
                super(itemView);
                imagePostProfileTest=itemView.findViewById(R.id.imagePostProfileTest);
                txtPostNameTest=itemView.findViewById(R.id.txtPostNametest);

            }
        }
}

