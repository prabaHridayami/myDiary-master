package com.example.praba.prakmob.Activity;

import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.praba.prakmob.Helper.PreferenceHelper;
import com.example.praba.prakmob.R;
import com.example.praba.prakmob.Adapter.ViewPagerAdapter;

import Fragment.FragmentDiary;
import Fragment.FragmentProfile;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView text;

    PreferenceHelper preferenceHelper;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferenceHelper = new PreferenceHelper(this);
        tabLayout = findViewById(R.id.tabLayout_id);
        viewPager = findViewById(R.id.viewPager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //add fragment here
        adapter.AddFragment(new FragmentDiary());
        adapter.AddFragment(new FragmentProfile());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_local_post_office_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_person_black_24dp2);

        ActionBar actionBar =getSupportActionBar();
        actionBar.setElevation(15);

    }
}
