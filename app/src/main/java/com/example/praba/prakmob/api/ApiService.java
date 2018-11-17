package com.example.praba.prakmob.api;

import com.example.praba.prakmob.model.Registrasi;
import com.example.praba.prakmob.model.UserLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @FormUrlEncoded
    @POST("user/login")
    Call<UserLogin>login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/add1")
    Call<Registrasi>registrasi(@Field("name") String name,@Field("username") String username, @Field("email") String email, @Field("password") String password, @Field("passconfirm") String passconfirm);

    @GET("user/view/{id_user}")
    Call<UserLogin>view(@Path("id_user") int id_user);
}
