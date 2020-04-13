package com.fando.picodiploma.favmovie;

import android.os.Bundle;

import com.fando.picodiploma.favmovie.adapter.FavouriteTabAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FavouriteTabAdapter favouriteTabAdapter = new FavouriteTabAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.home_viewpager);
        viewPager.setAdapter(favouriteTabAdapter);
        TabLayout tabs = findViewById(R.id.home_tablayout);
        tabs.setupWithViewPager(viewPager);

        getSupportActionBar().setElevation(0);

    }


}
