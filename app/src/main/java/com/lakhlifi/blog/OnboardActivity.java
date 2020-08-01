package com.lakhlifi.blog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lakhlifi.blog.Adapters.ViewPagerAdapter;

public class OnboardActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Button btnRight,btnLeft;
    private ViewPagerAdapter adapter;
    private LinearLayout dotsLayout;
    private TextView dots[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);
        init();
    }

    private void init() {

        //view_pager is the id of ViewPager in OnboardActuvity
        viewPager=(ViewPager)findViewById(R.id.view_pager);
        //onboardActivity
        btnLeft=(Button)findViewById(R.id.btnLesft);
        btnRight=(Button)findViewById(R.id.btnRight);

        dotsLayout=(LinearLayout)findViewById(R.id.dotsLayout);
        adapter=new ViewPagerAdapter(this);

        addDots(0);
        viewPager.addOnPageChangeListener(listner);
        viewPager.setAdapter(adapter);

        /*
        * to support lambda open project structure and change source compate    to 1,8
        *
        * */

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnRight.getText().equals("next")){
                    //if buttom_text==next
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                }else{
                    startActivity(new Intent(OnboardActivity.this,AuthActivity.class));
                    finish();
                }


            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+2);
            }
        });

    }
    //method for create dots from html code
    private void addDots(int position){
            dotsLayout.removeAllViews();
            dots=new TextView[3];
        for (int i = 0; i <dots.length ; i++) {
            dots[i]=new TextView(this);
            //html create dots
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            //color
            dots[i].setTextColor(getResources().getColor(R.color.colorLightGrey));
            dotsLayout.addView(dots[i]);
        }
        //lets change the selected dots  color
        if(dots.length>0){
            dots[position].setTextColor(getResources().getColor(R.color.colorGrey));
        }
    }



    private ViewPager.OnPageChangeListener listner=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDots(position);
            if(position==0){
                btnLeft.setVisibility(View.VISIBLE);
                btnLeft.setEnabled(true);
                btnRight.setText("NEXT");


            }else if(position==1){
                btnLeft.setVisibility(View.GONE);
                btnLeft.setEnabled(false);
                btnRight.setText("NEXT");
            }else{
                btnLeft.setVisibility(View.GONE);
                btnLeft.setEnabled(false);
                btnRight.setText("Finish");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };




}