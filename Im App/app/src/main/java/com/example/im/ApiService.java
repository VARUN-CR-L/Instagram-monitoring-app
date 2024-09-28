package com.example.im;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("instagram/track/{username}/")
    Call<ProfileData> getProfileData(@Path("username") String username);
}
