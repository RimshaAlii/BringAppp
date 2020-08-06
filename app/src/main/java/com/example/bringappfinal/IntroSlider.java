package com.example.bringappfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroSlider extends AppCompatActivity {
    private ViewPager screenpager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabindicator;
    Button nextbutton;
    Button getStarted;
    Animation button_anim;
    int position;

    DatabaseHelper databaseHelper;
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(RestorePrefData())
        {
            Intent homepage=new Intent(getApplicationContext(),Homepage.class);
            startActivity(homepage);
        }

        setContentView(R.layout.activity_intro_slider);


        tabindicator=(TabLayout) findViewById(R.id.tab_indicator);
        nextbutton=(Button) findViewById(R.id.button_next);
        getStarted=(Button) findViewById(R.id.button_start);
        button_anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation_start);


        final List<IntroScreenItem> introScreenItemList=new ArrayList<>();
        introScreenItemList.add(new IntroScreenItem("Welcome to Bring App","We are pleased to see you here",R.drawable.img1));
        introScreenItemList.add(new IntroScreenItem("Grand List","We have collected 150+ items for you",R.drawable.img2));
        introScreenItemList.add(new IntroScreenItem("Calculate Budget","Calculate your budget and we will notify you ",R.drawable.budgeticon));
        //setup viewpager
        screenpager=findViewById(R.id.viewpager);
        introViewPagerAdapter=new IntroViewPagerAdapter(this,introScreenItemList);
        screenpager.setAdapter(introViewPagerAdapter);

        tabindicator.setupWithViewPager(screenpager);

        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position=screenpager.getCurrentItem();
                if(position<introScreenItemList.size())
                {
                    position++;
                    screenpager.setCurrentItem(position);
                }
                if(position==introScreenItemList.size()-1)
                {
                    LoadLastScreen();
                }

            }

        });
        tabindicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==introScreenItemList.size()-1)
                {
                    LoadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //   databaseHelper.addmainlistindatabase(mainlist_class, main_list_items);


                Intent homepage=new Intent(getApplicationContext(),Homepage.class);
                startActivity(homepage);
                savePrefsData();
                finish();
            }

            private void savePrefsData() {
                SharedPreferences pref=getApplicationContext().getSharedPreferences("myprefintro",MODE_PRIVATE);
                SharedPreferences.Editor editor=pref.edit();
                editor.putBoolean("isfirsttimelaunch",true);
                editor.commit();
            }
        });
    }

    private boolean RestorePrefData() {
        SharedPreferences pref=getApplicationContext().getSharedPreferences("myprefintro",MODE_PRIVATE);
        boolean isfirsttimelaunch=pref.getBoolean("isfirsttimelaunch",false);
        return isfirsttimelaunch;
    }

    private void LoadLastScreen() {
        tabindicator.setVisibility(View.INVISIBLE);
        nextbutton.setVisibility(View.INVISIBLE);
        getStarted.setVisibility(View.VISIBLE);
        getStarted.setAnimation(button_anim);
    }
}