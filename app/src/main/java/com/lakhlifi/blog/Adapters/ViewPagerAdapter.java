package com.lakhlifi.blog.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.lakhlifi.blog.R;

public class ViewPagerAdapter extends PagerAdapter {


    private Context context;
    private LayoutInflater inflater;

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }
    private int images[]={
            R.drawable.p1,
            R.drawable.p2,
            R.drawable.p3
    };

    private String titles[]={
            "Learn",
            "Create",
            "Enjoy"};
    private String desc[]={
            "you have to see if your image has become drawable-v23 or v24 which might be higher than your mobile os level, Make sure to avoid creating drawable version image in project",
            "There is more to your stack trace, specifically one or more Caused by stanzas. Please edit your question ",
            "Je suis Essaddiq Lakhlifi "
    };

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(LinearLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.view_pager,container,false);
        //init View
        ImageView imageView=(ImageView)v.findViewById(R.id.imageViewPager);
        TextView txtTitleViewPager=(TextView)v.findViewById(R.id.txtTitleViewPager);
        TextView txtDescViewPager=(TextView)v.findViewById(R.id.txtDescViewPager);
        imageView.setImageResource(images[position]);
        txtTitleViewPager.setText(titles[position]);
        txtDescViewPager.setText(desc[position]);
        container.addView(v);
        return v;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
