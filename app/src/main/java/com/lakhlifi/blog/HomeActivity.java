package com.lakhlifi.blog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lakhlifi.blog.Fragments.HomeFragment;
import com.lakhlifi.blog.Fragments.UserFargmentTest;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private FloatingActionButton fab;
    private static final int GALLERY_ADD_POST=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameHomeContainer, new HomeFragment()).commit();
        //init();
}
    /*
        private void init() {
            fab=(FloatingActionButton) findViewById(R.id.fab);

            fab.setOnClickListener(view -> {
                Intent i= new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,GALLERY_ADPOST);
            });

        }
    /*
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==GALLERY_ADD_POST && resultCode==RESULT_OK){
                Uri imgUri =data.getData();
                imgProfile.setImageURI(imgUri);
                try {
                    bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item_account:
                fragmentManager=getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameHomeContainer, new UserFargmentTest()).commit();

                return true;

            case R.id.item_home:
                fragmentManager=getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameHomeContainer, new HomeFragment()).commit();
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}