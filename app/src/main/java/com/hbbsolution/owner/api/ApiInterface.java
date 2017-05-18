package com.hbbsolution.owner.api;

import com.hbbsolution.owner.model.MaidNearByResponse;
import com.hbbsolution.owner.work_management.model.WorkManagerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by buivu on 04/05/2017.
 */

public interface ApiInterface {
    @GET("owner/getAllTasks")
    Call<WorkManagerResponse> getInfo(@Header("hbbgvauth") String token, @Query("process") String idProcess);

    @GET("maid/getAll")
    Call<MaidNearByResponse> getMaidNearBy(@Header("hbbgvauth") String token, @Query("lat") Double lat, @Query("lng") Double lng);
}
