package com.fando.picodiploma.moviecatalogue_4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.fando.picodiploma.moviecatalogue_4.R;
import com.fando.picodiploma.moviecatalogue_4.fragment.FavouriteFragment;
import com.fando.picodiploma.moviecatalogue_4.fragment.MovieFragment;
import com.fando.picodiploma.moviecatalogue_4.fragment.TVShowFragment;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SmoothBottomBar smoothBottomBar = findViewById(R.id.smooth_bar);

        smoothBottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelect(int position) {
                switch (position) {
                    case 0:
                        handleFragment(new MovieFragment());
                        break;
                    case 1:
                        handleFragment(new TVShowFragment());
                        break;
                    case 2:
                        handleFragment(new FavouriteFragment());
                        break;
                }
            }
        });
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.nav_host_fragment, new MovieFragment()).commit();
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        if (savedInstanceState != null) {

        }
    }

    void handleFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, fragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent settingLanguageIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(settingLanguageIntent);
        } else if (item.getItemId() == R.id.action_Notification) {
            Intent settingNotifIntent = new Intent(this, NotifSettingActivity.class);
            startActivity(settingNotifIntent);
        }
        return super.onOptionsItemSelected(item);
    }

}
