package Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praba.prakmob.Activity.LoginActivity;
import com.example.praba.prakmob.Helper.PreferenceHelper;
import com.example.praba.prakmob.R;
import com.example.praba.prakmob.api.ApiClient;
import com.example.praba.prakmob.api.ApiService;
import com.example.praba.prakmob.model.Registrasi;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class FragmentProfile extends android.support.v4.app.Fragment {
    SharedPreferences sharedPreferences;
    PreferenceHelper preferenceHelper;
    EditText et_name, et_username, et_email;
    String inputName, inputUsername,inputId;
    View v;
    Button btn_logout, btn_edit;
    ApiService service;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.profile_fragment, container,false);
        service = ApiClient.getService();

        preferenceHelper = new PreferenceHelper(getActivity());
        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("nama","");
        String username = sharedPreferences.getString("username","");
        String email = sharedPreferences.getString("email","");
        final String id = sharedPreferences.getString("id","");

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

        btn_edit = v.findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputName = et_name.getText().toString();
                inputUsername = et_username.getText().toString();

                service.edit(id,inputName,inputUsername).enqueue(new Callback<Registrasi>() {
                    @Override
                    public void onResponse(Call<Registrasi> call, retrofit2.Response<Registrasi> response) {
                        if(response.isSuccessful()){
                            Registrasi registrasi=response.body();
                            if(registrasi.getStatus() == true){
                                preferenceHelper.setUsername(inputUsername);
                                preferenceHelper.setNama(inputName);
                                Toast.makeText(getActivity(), "Edit Berhasil", Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(getActivity(),getActivity().getClass());
                                startActivity(intent);
                                getActivity().finish();

                            }else{
                                Toast.makeText(getActivity(), registrasi.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Registrasi> call, Throwable t) {
                        Toast.makeText(getActivity(), "Error :"+t, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return v;
    }

}


