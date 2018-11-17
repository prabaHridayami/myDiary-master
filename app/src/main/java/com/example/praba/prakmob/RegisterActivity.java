package com.example.praba.prakmob;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praba.prakmob.api.ApiClient;
import com.example.praba.prakmob.api.ApiService;
import com.example.praba.prakmob.model.Registrasi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText etName, etUsername, etEmail, etPassword, etConfirmPass;
    String inputName, inputUsername, inputEmail, inputPassword, inputConfirmPass;
    Button btn_register, btn_loginhere;
    TextView btn_login;
    ApiService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = ApiClient.getService();
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.nameField);
        etUsername = findViewById(R.id.usernameField);
        etEmail = findViewById(R.id.emailField);
        etPassword = findViewById(R.id.passwordfield);
        etConfirmPass = findViewById(R.id.passconfirmField);
        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_toLogin);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputName = etName.getText().toString();
                inputUsername = etUsername.getText().toString();
                inputEmail = etEmail.getText().toString();
                inputPassword = etPassword.getText().toString();
                inputConfirmPass = etConfirmPass.getText().toString();

                if(inputPassword == inputConfirmPass){
                    service.registrasi(inputName,inputUsername,inputEmail,inputPassword, inputConfirmPass).enqueue(new Callback<Registrasi>() {
                        @Override
                        public void onResponse(Call<Registrasi> call, retrofit2.Response<Registrasi> response) {
                            if(response.isSuccessful()){
                                Registrasi registrasi=response.body();
                                if(registrasi.getStatus() == true){
                                    Toast.makeText(RegisterActivity.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(RegisterActivity.this, registrasi.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Registrasi> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "Error :"+t, Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this, "Password is not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
