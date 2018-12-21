package com.example.praba.prakmob.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.praba.prakmob.R;
import com.example.praba.prakmob.api.ApiClient;
import com.example.praba.prakmob.api.ApiService;
import com.example.praba.prakmob.model.Diary;
import com.example.praba.prakmob.model.DiaryShow;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDiaryActivity extends AppCompatActivity {

    private static final int IMG_REQUEST = 777;
    SharedPreferences sharedPreferences;
    ApiService service;
    EditText etTitle, etDiary;
    ImageView iv_pic;
    Button btn_edit, btn_gallery1, btn_back1, btn_delete;
    String inputTitle, inputDiary, id_diary, image;
    private Bitmap bitmap;
    private Context mContext;
    MultipartBody.Part body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = ApiClient.getService();
        setContentView(R.layout.activity_edit_diary);



        etTitle = findViewById(R.id.ed_title1);
        etDiary = findViewById(R.id.ed_text1);
        btn_gallery1 = findViewById(R.id.btn_gallery1);
        btn_delete = findViewById(R.id.btn_delete);
        btn_edit = findViewById(R.id.btn_edit);
        btn_back1 = findViewById(R.id.btn_back1);
        iv_pic = findViewById(R.id.iv_pic);

        sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        final String id = sharedPreferences.getString("id","");
        Intent intent = getIntent();
        final String idDiary = intent.getStringExtra("id_diary");

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
                Bundle extra = getIntent().getExtras();
                if(body == null){
                    inputTitle = etTitle.getText().toString();
                    inputDiary = etDiary.getText().toString();
                    service.edit1(idDiary,inputTitle,inputDiary,image).enqueue(new Callback<Diary>() {
                        @Override
                        public void onResponse(Call<Diary> call, Response<Diary> response) {
                            Diary diary = response.body();
                            if (response.isSuccessful()){
                                Toast.makeText(EditDiaryActivity.this, "Edit Diary Success", Toast.LENGTH_LONG).show();

                                Intent intent=new Intent(EditDiaryActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(EditDiaryActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Diary> call, Throwable t) {
                            Toast.makeText(EditDiaryActivity.this, "Error :"+t, Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    RequestBody title = RequestBody.create(okhttp3.MultipartBody.FORM, etTitle.getText().toString());
                    RequestBody diary = RequestBody.create(okhttp3.MultipartBody.FORM, etDiary.getText().toString());
                    RequestBody id_diary = RequestBody.create(okhttp3.MultipartBody.FORM, extra.getString("id_diary"));

                    service.edit(body,title,diary,id_diary).enqueue(new Callback<Diary>() {
                        @Override
                        public void onResponse(Call<Diary> call, retrofit2.Response<Diary> response) {
                            Diary diary = response.body();
                            if (response.isSuccessful()){
                                Toast.makeText(EditDiaryActivity.this, "Edit Diary Success", Toast.LENGTH_LONG).show();

                                Intent intent=new Intent(EditDiaryActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(EditDiaryActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Diary> call, Throwable t) {
                            Toast.makeText(EditDiaryActivity.this, "Error :"+t, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        btn_gallery1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkStoragePermission()) {
                        selectImage();
                    }
                }
                else {
                    selectImage();
                }

            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.delete(idDiary).enqueue(new Callback<Diary>() {
                    @Override
                    public void onResponse(Call<Diary> call, retrofit2.Response<Diary> response) {
                        if (response.isSuccessful()){
                            Intent intent=new Intent(EditDiaryActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(EditDiaryActivity.this, "Delete Diary Success", Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(EditDiaryActivity.this, "Delete Failed", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EditDiaryActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        // Don't add finish here.
        //This is necessary because you finished your last activity with finish();
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
                    image = diaryShow.getImage();
                    Glide.with(EditDiaryActivity.this)
                            .load(diaryShow.getImage())
                            .into(iv_pic);
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

    public static final int MY_PERMISSIONS_REQUEST_STORAGE= 1;
    private boolean checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE);
            }
            return false;
        } else {
            return true;
        }
    }

    private void selectImage(){
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==IMG_REQUEST && resultCode==RESULT_OK&&data!=null){
            Uri selectedImage=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
                iv_pic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String wholeID = DocumentsContract.getDocumentId(selectedImage);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = { MediaStore.Images.Media.DATA };

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = getContentResolver().
                    query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            column, sel, new String[]{ id }, null);

            String filePath = "";

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
            File file = new File(filePath);

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);

            body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);
//            uploadImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Izin diberikan.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {

                        selectImage();
                    }

                } else {
                    // Izin ditolak.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
