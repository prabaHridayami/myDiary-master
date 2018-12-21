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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.praba.prakmob.R;
import com.example.praba.prakmob.api.ApiClient;
import com.example.praba.prakmob.api.ApiService;
import com.example.praba.prakmob.model.Diary;
import com.example.praba.prakmob.model.Response;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import Database.DbHelper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class WritediaryActivity extends AppCompatActivity {

    private static final int IMG_REQUEST = 777;
    SharedPreferences sharedPreferences;
    ImageView diary_img;
    EditText etTitle, etDiary;
    String inputTitle, inputDiary, id_diary;
    Button btn_save, btn_gallery, btn_back;
    ApiService service;
    private Bitmap bitmap;
    MultipartBody.Part body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = ApiClient.getService();
        setContentView(R.layout.activity_write_diary);

//        Toolbar mTopToolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(mTopToolbar);

        diary_img = findViewById(R.id.diary_img);
        etTitle = findViewById(R.id.ed_title);
        etDiary = findViewById(R.id.ed_text);
        btn_gallery = findViewById(R.id.btn_gallery);
        btn_save = findViewById(R.id.btn_save);
        btn_back = findViewById(R.id.btn_back);
        sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        final String id_user = sharedPreferences.getString("id","");
        inputTitle = etTitle.getText().toString();
        inputDiary = etDiary.getText().toString();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody title = RequestBody.create(okhttp3.MultipartBody.FORM, etTitle.getText().toString());
                RequestBody diary = RequestBody.create(okhttp3.MultipartBody.FORM, etDiary.getText().toString());
                RequestBody id_user = RequestBody.create(okhttp3.MultipartBody.FORM, sharedPreferences.getString("id",""));
//        RequestBody lng = RequestBody.create(okhttp3.MultipartBody.FORM, mLongitude.getText().toString());
                service.add(body,title,diary,id_user).enqueue(new Callback<Diary>() {
                    @Override
                    public void onResponse(Call<Diary> call, retrofit2.Response<Diary> response) {
                        Diary diary = response.body();
                        if (response.isSuccessful()){
                            Intent intent=new Intent(WritediaryActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(WritediaryActivity.this, "Add Diary Success", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(WritediaryActivity.this, "Failed", Toast.LENGTH_SHORT).show();
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

        btn_gallery.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(WritediaryActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        // Don't add finish here.
        //This is necessary because you finished your last activity with finish();
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
                diary_img.setImageBitmap(bitmap);
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

    private void upload() {
        RequestBody title = RequestBody.create(okhttp3.MultipartBody.FORM, etTitle.getText().toString());
        RequestBody diary = RequestBody.create(okhttp3.MultipartBody.FORM, etDiary.getText().toString());
        RequestBody id_user = RequestBody.create(okhttp3.MultipartBody.FORM, sharedPreferences.getString("id",""));
//        RequestBody lng = RequestBody.create(okhttp3.MultipartBody.FORM, mLongitude.getText().toString());
        service.add(body,title, diary, id_user).enqueue(new Callback<Diary>() {
            @Override
            public void onResponse(Call<Diary> call, retrofit2.Response<Diary> response) {
                Diary diary =response.body();
                if (response.isSuccessful()){
                    Toast.makeText(WritediaryActivity.this, "Add Diary Success", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(WritediaryActivity.this, diary.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Diary> call, Throwable t) {
                Toast.makeText(WritediaryActivity.this, "Error :"+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addDiaryLocal(){
        int id = 1;
        String title=etTitle.getText().toString();
        String diary=etDiary.getText().toString();
        String id_user = sharedPreferences.getString("id","");
        String image = body.toString();

        DbHelper dbHelper = new DbHelper(this);
        long insertLastId = dbHelper.insertDiary(id,title, diary, image, id_user );

        if(insertLastId > 0){
            Toast.makeText(this, "Insert berhasil dengan id :"+insertLastId, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Insert Gagal", Toast.LENGTH_SHORT).show();
        }
    }

}
