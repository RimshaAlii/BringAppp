package com.example.bringappfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {
    Context context;
    List<IntroScreenItem> listscreenitem;

    public IntroViewPagerAdapter(Context context,List<IntroScreenItem> listscreenitem) {
        this.context = context;
        this.listscreenitem=listscreenitem;
    }

    @Override
    public int getCount() {
        return listscreenitem.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View Layoutscreen=inflater.inflate(R.layout.layout_slider,null);
        ImageView imgslide=Layoutscreen.findViewById(R.id.intro_image);
        TextView title=Layoutscreen.findViewById(R.id.intro_title);
        TextView description=Layoutscreen.findViewById(R.id.intro_description);
        title.setText(listscreenitem.get(position).getTitle());
        description.setText(listscreenitem.get(position).getDescription());
        imgslide.setImageResource(listscreenitem.get(position).getSlider_image());
        container.addView(Layoutscreen);
        return Layoutscreen;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
