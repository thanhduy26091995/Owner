package com.hbbsolution.owner.api;

import com.hbbsolution.owner.history.model.WorkHistoryResponse;
import com.hbbsolution.owner.model.MaidNearByResponse;
import com.hbbsolution.owner.model.TypeJobResponse;
import com.hbbsolution.owner.more.viet_pham.Model.RegisterResponse;
import com.hbbsolution.owner.work_management.model.geocodemap.GeoCodeMapResponse;
import com.hbbsolution.owner.work_management.model.jobpost.JobPostResponse;
import com.hbbsolution.owner.work_management.model.listcommentmaid.CommentMaidResponse;
import com.hbbsolution.owner.work_management.model.maid.ListMaidResponse;
import com.hbbsolution.owner.work_management.model.workmanager.WorkManagerResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

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

    @Multipart
    @POST("auth/register")
    Call<RegisterResponse> createAccount(
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone,
            @Part("name") RequestBody name,
            @Part("adressName") RequestBody address,
            @Part("lat") RequestBody lat,
            @Part("lng") RequestBody lng,
            @Part("gender") RequestBody gender,
            @Part MultipartBody.Part image
    );

    @Multipart
    @POST("auth/login")
    Call<RegisterResponse> signInAccount(@Part("username") RequestBody username,
                                    @Part("password") RequestBody password
    );

    @GET
    Call<GeoCodeMapResponse> getLocaltionAddress(@Url String url, @Query("address") String addressOfOwner);

    @FormUrlEncoded
    @POST("task/create")
    Call<JobPostResponse> postJob(@Header("hbbgvauth") String token, @Field("title") String title, @Field("work") String typeJob,
                                             @Field("description") String description, @Field("addressName") String addressName,
                                             @Field("lat") double lat, @Field("lng") double lng, @Field("tools") boolean isTool,
                                             @Field("package") String packageId, @Field("price") String price,
                                             @Field("startAt") String startAt, @Field("endAt") String endAt);

}
