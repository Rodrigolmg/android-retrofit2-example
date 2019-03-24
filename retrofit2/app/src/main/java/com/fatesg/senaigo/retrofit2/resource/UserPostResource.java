package com.fatesg.senaigo.retrofit2.resource;

import com.fatesg.senaigo.retrofit2.model.UserPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserPostResource {

    @GET("/posts")
    Call<List<UserPost>> get();

    @POST("/posts")
    Call<UserPost> post(@Body UserPost userPost);

    @PUT("/posts/{id}")
    Call<UserPost> put(@Body UserPost userPost, @Path("id") Integer id);

    @DELETE("/posts/{id}")
    Call<Void> delete(@Path("id") Integer id);
}
