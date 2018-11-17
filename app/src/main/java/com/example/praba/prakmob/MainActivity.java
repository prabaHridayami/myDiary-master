package com.example.praba.prakmob;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView text;
    Button btn_logout;
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
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_person_black_24dp);

        ActionBar actionBar =getSupportActionBar();
        actionBar.setElevation(0);

//        Boolean login = preferenceHelper.getLogin();
//        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
//        String name = sharedPreferences.getString("nama","");
//        String username = sharedPreferences.getString("username","");
//        String email = sharedPreferences.getString("email","");
//        String id = sharedPreferences.getString("id","");


//        text = findViewById(R.id.sharedPreference);
//        text.setText(String.valueOf(login));

//        text.setText(name+"\n");
//        text.append(username+"\n");
//        text.append(email+"\n");
//        text.append(id);

        btn_logout = findViewById(R.id.logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                preferenceHelper.setLogout();
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
