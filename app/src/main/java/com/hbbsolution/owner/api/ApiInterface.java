package com.hbbsolution.owner.api;

import com.hbbsolution.owner.history.model.WorkHistoryResponse;
import com.hbbsolution.owner.model.MaidNearByResponse;
import com.hbbsolution.owner.model.TypeJobResponse;
import com.hbbsolution.owner.work_management.model.listcommentmaid.CommentMaidResponse;
import com.hbbsolution.owner.work_management.model.maid.ListMaidResponse;
import com.hbbsolution.owner.work_management.model.workmanager.WorkManagerResponse;

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

    @GET("owner/getAllTasks")
    Call<WorkHistoryResponse> getInfoWorkHistory(@Header("hbbgvauth") String token, @Query("process") String idProcess);

    @GET("more/getAllMaids")
    Call<MaidNearByResponse> getMaidNearBy(@Query("lat") Double lat, @Query("lng") Double lng, @Query("ageMin") Integer ageMin, @Query("ageMax") Integer ageMax,
                                           @Query("gender") Integer gender, @Query("maxDistance") Integer maxDistance);

    @GET("task/getRequest")
    Call<ListMaidResponse> getInfoListMaid(@Header("hbbgvauth") String token, @Query("id") String idTask);

    @GET("maid/getComment")
    Call<CommentMaidResponse> getListCommentMaid(@Header("hbbgvauth") String token, @Query("id") String idTask, @Query("page") int page);

    @GET("work/getAll")
    Call<TypeJobResponse> getAllTypeJob();
}
