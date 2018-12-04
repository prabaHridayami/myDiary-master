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
import com.example.praba.prakmob.model.DiaryShow;

import retrofit2.Call;
import retrofit2.Callback;

public class EditDiaryActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ApiService service;
    EditText etTitle, etDiary;
    Button btn_edit, btn_gallery1, btn_back1;
    String inputTitle, inputDiary, id_diary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = ApiClient.getService();
        setContentView(R.layout.activity_edit_diary);

        etTitle = findViewById(R.id.ed_title1);
        etDiary = findViewById(R.id.ed_text1);
        btn_gallery1 = findViewById(R.id.btn_gallery1);
        btn_edit = findViewById(R.id.btn_edit);
        btn_back1 = findViewById(R.id.btn_back1);
        sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        final String id = sharedPreferences.getString("id","");

        Bundle extra = getIntent().getExtras();
        id_diary = extra.getString("id_diary");
        getDiaryData();
        btn_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EditDiaryActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputTitle = etTitle.getText().toString();
                inputDiary = etDiary.getText().toString();

                service.editDiary(id_diary,inputTitle,inputDiary).enqueue(new Callback<Diary>() {
                    @Override
                    public void onResponse(Call<Diary> call, retrofit2.Response<Diary> response) {
                        if(response.isSuccessful()){
                            Diary diary =response.body();
                            if(diary.getStatus() == true){
                                Toast.makeText(EditDiaryActivity.this, "Edit Diary Berhasil", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(EditDiaryActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(EditDiaryActivity.this, diary.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Diary> call, Throwable t) {
                        Toast.makeText(EditDiaryActivity.this, "Error :"+t, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public void getDiaryData() {
        Intent intent = getIntent();
        final String id_diary = intent.getStringExtra("id_diary");

        service.viewbyid(id_diary).enqueue(new Callback<DiaryShow>() {
            @Override
            public void onResponse(Call<DiaryShow> call, retrofit2.Response<DiaryShow> response) {
                DiaryShow diaryShow = response.body();
                if (response.isSuccessful()) {
                    etTitle.setText(diaryShow.getTitle());
                    etDiary.setText(diaryShow.getDiary());
                } else {
                    Toast.makeText(EditDiaryActivity.this, "ID diary " + id_diary + " Not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DiaryShow> call, Throwable t) {
                Toast.makeText(EditDiaryActivity.this, "Error :" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
