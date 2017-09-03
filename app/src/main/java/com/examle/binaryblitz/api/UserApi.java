package com.examle.binaryblitz.api;

import com.examle.binaryblitz.model.Message;
import com.examle.binaryblitz.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface UserApi {
    @GET("/users.json")
    Observable<List<User>> getUsers();

    @POST("/users.json")
    Observable<Message> saveUser(@Body SaveUserQuery saveUserQuery);

    @PATCH("/users/1.json")
    Observable<Message> editUser(@Body SaveUserQuery saveUserQuery);
}
