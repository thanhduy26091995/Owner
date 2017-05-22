package com.hbbsolution.owner.api;

import com.hbbsolution.owner.history.presenter.WorkHistoryResponse;
import com.hbbsolution.owner.model.MaidNearByResponse;
import com.hbbsolution.owner.more.viet_pham.Model.RegisterResponse;
import com.hbbsolution.owner.work_management.model.WorkManagerResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by buivu on 04/05/2017.
 */

public interface ApiInterface {
    @GET("owner/getAllTasks")
    Call<WorkManagerResponse> getInfo(@Header("hbbgvauth") String token, @Query("process") String idProcess);

    @GET("owner/getAllTasks")
    Call<WorkHistoryResponse> getInfoWorkHistory(@Header("hbbgvauth") String token, @Query("process") String idProcess);

    @GET("maid/getAll")
    Call<MaidNearByResponse> getMaidNearBy(@Header("hbbgvauth") String token, @Query("lat") Double lat, @Query("lng") Double lng);

    @Multipart
    @POST("user/create")
    Call<RegisterResponse> createAccount(
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone,
            @Part("adressName") RequestBody address,
            @Part("lat") double lat,
            @Part("lng") double lng,
            @Part("gender") int gender,
            @Part MultipartBody.Part image
    );
}
