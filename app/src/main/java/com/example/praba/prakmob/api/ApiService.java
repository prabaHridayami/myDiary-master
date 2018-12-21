package com.example.praba.prakmob.api;

import com.example.praba.prakmob.model.DiaryList;
import com.example.praba.prakmob.model.Diary;
import com.example.praba.prakmob.model.DiaryShow;
import com.example.praba.prakmob.model.Registrasi;
import com.example.praba.prakmob.model.UserLogin;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    @FormUrlEncoded
    @POST("user/login")
    Call<UserLogin>login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/add1")
    Call<Registrasi>registrasi(@Field("name") String name,@Field("username") String username, @Field("email") String email, @Field("password") String password, @Field("passconfirm") String passconfirm);

    @GET("user/view/{id_user}")
    Call<UserLogin>view(@Path("id_user") String id_user);

    @FormUrlEncoded
    @POST("user/edit")
    Call<Registrasi>edit(@Field("id_user") String id, @Field("name") String name, @Field("username") String username);

    @FormUrlEncoded
    @POST("diary/addmob")
    Call<Diary>addMOb(@Field("title") String title, @Field("diary") String diary, @Field("id_user") String id_user);

    @FormUrlEncoded
    @POST("diary/viewbyuser")
    Call<List<DiaryShow>>viewbyuser(@Field("id_user") String idUser);

    @FormUrlEncoded
    @POST("diary/viewbyid")
    Call<DiaryShow>viewbyid(@Field("id_diary") String idDiary);

    @FormUrlEncoded
    @POST("diary/delete")
    Call<Diary>delete(@Field("id_diary") String idDiary);

    @FormUrlEncoded
    @POST("diary/edit1")
    Call<Diary>edit1(@Field("id_diary") String idDiary, @Field("title") String title, @Field("diary") String diary, @Field("image") String image );

//    @FormUrlEncoded
//    @POST("diary/edit")
//    Call<Diary>editDiary(@Field("id_diary") String idDiary, @Field("title") String title, @Field("diary") String diary );

    @Multipart
    @POST("diary/addmob")
    Call<Diary>add(@Part MultipartBody.Part image, @Part("title") RequestBody title,
                       @Part("diary") RequestBody diary,
                       @Part("id_user") RequestBody id_user);

    @Multipart
    @POST("diary/editmob")
    Call<Diary>edit(@Part MultipartBody.Part image, @Part("title") RequestBody title,
                   @Part("diary") RequestBody diary,
                   @Part("id_diary") RequestBody id_diary);


}
