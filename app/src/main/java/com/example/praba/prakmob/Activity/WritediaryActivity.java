package com.example.praba.prakmob.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.praba.prakmob.R;
import com.example.praba.prakmob.api.ApiClient;
import com.example.praba.prakmob.api.ApiService;
import com.example.praba.prakmob.model.Diary;

import retrofit2.Call;
import retrofit2.Callback;

public class WritediaryActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    EditText etTitle, etDiary;
    String inputTitle, inputDiary, id_diary;
    Button btn_save, btn_gallery, btn_back;
    ApiService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = ApiClient.getService();
        setContentView(R.layout.activity_write_diary);

        etTitle = findViewById(R.id.ed_title);
        etDiary = findViewById(R.id.ed_text);
        btn_gallery = findViewById(R.id.btn_gallery);
        btn_save = findViewById(R.id.btn_save);
        btn_back = findViewById(R.id.btn_back);
        sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        final String id = sharedPreferences.getString("id","");

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputTitle = etTitle.getText().toString();
                inputDiary = etDiary.getText().toString();

                service.add(inputTitle,inputDiary,id).enqueue(new Callback<Diary>() {
                    @Override
                    public void onResponse(Call<Diary> call, retrofit2.Response<Diary> response) {
                        if(response.isSuccessful()){
                            Diary diary =response.body();
                            if(diary.getStatus() == true){
                                Toast.makeText(WritediaryActivity.this, "Add Diary Berhasil", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(WritediaryActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(WritediaryActivity.this, diary.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Diary> call, Throwable t) {
                        Toast.makeText(WritediaryActivity.this, "Error :"+t, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WritediaryActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
