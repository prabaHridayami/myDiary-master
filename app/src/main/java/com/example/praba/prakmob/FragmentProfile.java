package com.example.praba.prakmob;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FragmentProfile extends android.support.v4.app.Fragment {
    SharedPreferences sharedPreferences;
    PreferenceHelper preferenceHelper;
    EditText et_name, et_username, et_email;
    View v;
    Button btn_logout;
    public FragmentProfile() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.profile_fragment, container,false);

        preferenceHelper = new PreferenceHelper(getActivity());
        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("nama","");
        String username = sharedPreferences.getString("username","");
        String email = sharedPreferences.getString("email","");
        String id = sharedPreferences.getString("id","");


        et_name = v.findViewById(R.id.nameAcc);
        et_username = v.findViewById(R.id.usernameAcc);
        et_email = v.findViewById(R.id.emailAcc);

        et_name.setText(name);
        et_username.setText(username);
        et_email.setText(email);

        btn_logout = v.findViewById(R.id.logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                preferenceHelper.setLogout();
                Intent intent=new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return v;
    }
}
