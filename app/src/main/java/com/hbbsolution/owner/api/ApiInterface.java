package com.hbbsolution.owner.api;

import com.hbbsolution.owner.history.model.commenthistory.CommentHistoryResponse;
import com.hbbsolution.owner.history.model.helper.HistoryHelperResponse;
import com.hbbsolution.owner.history.model.liabilities.LiabilitiesResponse;
import com.hbbsolution.owner.history.model.workhistory.CommentResponse;
import com.hbbsolution.owner.history.model.workhistory.WorkHistoryResponse;
import com.hbbsolution.owner.home.model.ResponseRequest;
import com.hbbsolution.owner.maid_profile.choose_maid.model.SendRequestResponse;
import com.hbbsolution.owner.model.CheckInResponse;
import com.hbbsolution.owner.model.MaidNearByResponse;
import com.hbbsolution.owner.model.TypeJobResponse;
import com.hbbsolution.owner.more.duy_nguyen.model.ContactResponse;
import com.hbbsolution.owner.more.duy_nguyen.model.StatisticResponse;
import com.hbbsolution.owner.more.phuc_tran.model.AboutResponse;
import com.hbbsolution.owner.more.phuc_tran.model.ForgotPassResponse;
import com.hbbsolution.owner.more.viet_pham.Model.signin_signup.BodyResponse;
import com.hbbsolution.owner.more.viet_pham.Model.signin_signup.DataUpdateResponse;
import com.hbbsolution.owner.report.model.ReportResponse;
import com.hbbsolution.owner.work_management.model.billGv24.BillGv24Response;
import com.hbbsolution.owner.work_management.model.chekout.CheckOutResponse;
import com.hbbsolution.owner.work_management.model.geocodemap.GeoCodeMapResponse;
import com.hbbsolution.owner.work_management.model.jobpost.JobPostResponse;
import com.hbbsolution.owner.work_management.model.listcommentmaid.CommentMaidResponse;
import com.hbbsolution.owner.work_management.model.maid.ListMaidResponse;
import com.hbbsolution.owner.work_management.model.workmanager.WorkManagerResponse;
import com.hbbsolution.owner.work_management.model.workmanagerpending.JobPendingResponse;
import com.hbbsolution.owner.work_management.view.payment.model.PaymentResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by buivu on 04/05/2017.
 */

public interface ApiInterface {
//    @GET("owner/getAllTasks")
//    Call<WorkManagerResponse> getInfo(@Query("process") String idProcess);

    @GET("owner/getAllTasks")
    Call<WorkManagerResponse> getInfo(@Query("process") String idProcess, @Query("sortByTaskTime") boolean isSortByTaskTime);

    @GET("owner/getAllTasks")
    Call<JobPendingResponse> getJobPendingResponse(@Query("process") String idProcess);

    @GET("owner/getHistoryTasks")
    Call<WorkHistoryResponse> getInfoWorkHistory(@Query("startAt") String startAt, @Query("endAt") String endAt, @Query("page") int page);

    @GET("owner/getTaskOfMaid")
    Call<WorkHistoryResponse> getListWorkByMaid(@Query("maid") String idMaid, @Query("page") int page);

    @GET("owner/getAllWorkedMaid")
    Call<HistoryHelperResponse> getAllWorkedMaid(@Query("startAt") String startAt, @Query("endAt") String endAt);

    @GET("owner/getDebt")
    Call<LiabilitiesResponse> getLiabilities(@Query("startAt") String startAt, @Query("endAt") String endAt);

    @GET("task/getComment")
    Call<CommentHistoryResponse> checkComment(@Query("task") String idTask);


    @GET("more/getAllMaids")
    Call<MaidNearByResponse> getMaidNearBy(@Query("lat") Double lat, @Query("lng") Double lng, @Query("ageMin") Integer ageMin, @Query("ageMax") Integer ageMax,
                                           @Query("gender") Integer gender, @Query("maxDistance") Integer maxDistance, @Query("priceMin") Integer priceMin,
                                           @Query("priceMax") Integer priceMax, @Query("work") String workId);

    @GET("more/getAllMaids")
    Call<MaidNearByResponse> searchMaidByAddress(@Query("lat") Double lat, @Query("lng") Double lng);

    @GET("task/getRequest")
    Call<ListMaidResponse> getInfoListMaid(@Query("id") String idTask);

    @GET("maid/getComment")
    Call<CommentMaidResponse> getListCommentMaid(@Query("id") String idTask, @Query("page") int page);

    @GET("work/getAll")
    Call<TypeJobResponse> getAllTypeJob();


    @Multipart
    @POST("auth/register")
    Call<BodyResponse> createAccount(
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone,
            @Part("name") RequestBody name,
            @Part("addressName") RequestBody address,
            @Part("lat") RequestBody lat,
            @Part("lng") RequestBody lng,
            @Part("gender") RequestBody gender,
            @Part MultipartBody.Part image
    );


    @Multipart
    @POST("auth/login")
    Call<BodyResponse> signInAccount(@Part("username") RequestBody username,
                                     @Part("password") RequestBody password,
                                     @Part("device_token") RequestBody deviceToken
    );

    @Multipart
    @POST("auth/thirdLogin")
    Call<BodyResponse> signInGoogleAndFace(@Part("id") RequestBody username,
                                           @Part("token") RequestBody password,
                                           @Part("device_token") RequestBody deviceToken
    );

    @Multipart
    @POST("auth/thirdRegister")
    Call<BodyResponse> updateGoogleAndFace(@Part("id") RequestBody id,
                                           @Part("token") RequestBody Token,
                                           @Part("device_token") RequestBody deviceToken,
                                           @Part("phone") RequestBody phone,
                                           @Part("username") RequestBody username,
                                           @Part("name") RequestBody name,
                                           @Part("email") RequestBody email,
                                           @Part("addressName") RequestBody address,
                                           @Part("lat") RequestBody lat,
                                           @Part("lng") RequestBody lng,
                                           @Part("gender") RequestBody gender,
                                           @Part("image") RequestBody image
    );

    @Multipart
    @PUT("owner/update")
    Call<DataUpdateResponse> updateOwner(
            @Part("phone") RequestBody phone,
            @Part("name") RequestBody name,
            @Part("addressName") RequestBody address,
            @Part("lat") RequestBody lat,
            @Part("lng") RequestBody lng,
            @Part("gender") RequestBody gender,
            @Part MultipartBody.Part image
    );


    @GET
    Call<GeoCodeMapResponse> getLocaltionAddress(@Url String url, @Query("address") String addressOfOwner);

    @FormUrlEncoded
    @POST("task/create")
    Call<JobPostResponse> postJob(@Field("title") String title, @Field("work") String typeJob,
                                  @Field("description") String description, @Field("addressName") String addressName,
                                  @Field("lat") double lat, @Field("lng") double lng, @Field("tools") boolean isTool,
                                  @Field("package") String packageId, @Field("price") String price,
                                  @Field("startAt") String startAt, @Field("endAt") String endAt);

    @FormUrlEncoded
    @PUT("task/update")
    Call<JobPostResponse> updatePostJob(@Field("id") String idTask, @Field("title") String title, @Field("work") String typeJob,
                                        @Field("description") String description, @Field("addressName") String addressName,
                                        @Field("lat") double lat, @Field("lng") double lng, @Field("tools") boolean isTool,
                                        @Field("package") String packageId, @Field("price") String price,
                                        @Field("startAt") String startAt, @Field("endAt") String endAt);


    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "task/delete", hasBody = true)
    Call<JobPostResponse> deleteJob(@Field("id") String idTask, @Field("ownerId") String ownerId);

    @FormUrlEncoded
    @POST("owner/comment")
    Call<CommentResponse> postComment(@Field("task") String taskID, @Field("toId") String toId,
                                      @Field("content") String content, @Field("evaluation_point") int evaluation_point);


    @FormUrlEncoded
    @POST("task/sendRequest")
    Call<SendRequestResponse> sendRequest(@Field("maidId") String maidId, @Field("title") String title, @Field("package") String packageId,
                                          @Field("work") String work, @Field("description") String description, @Field("price") Double price,
                                          @Field("addressName") String addressName, @Field("lat") Double lat, @Field("lng") Double lng,
                                          @Field("startAt") String startAt, @Field("endAt") String endAt, @Field("hour") Double hour,
                                          @Field("tools") Boolean tools);


    @FormUrlEncoded
    @POST("owner/report")
    Call<ReportResponse> reportMaid(@Field("toId") String toId, @Field("content") String content);

    @GET("owner/statistical")
    Call<StatisticResponse> getStatistic(@Query("startAt") String startAt, @Query("endAt") String endAt);

    @GET("owner/getWallet")
    Call<PaymentResponse> getWallet();

    @FormUrlEncoded
    @POST("task/submit")
    Call<JobPostResponse> sentRequestChosenMaid(@Field("id") String id, @Field("maidId") String maidId);

    @GET("owner/checkToken")
    Call<ResponseRequest> checkToken();

    @Multipart
    @POST("task/checkin")
    Call<CheckInResponse> checkIn(@Part MultipartBody.Part fileImage, @Part("ownerId") RequestBody ownerId, @Part("id") RequestBody taskId);


    @GET("more/getGV24HInfo")
    Call<AboutResponse> getAbout(@Query("id") String idTask);

    @GET("more/getContact")
    Call<ContactResponse> getContact();

    @FormUrlEncoded
    @POST("task/checkout")
    Call<CheckOutResponse> getInfoCheckOut(@Field("id") String idTask);

    @FormUrlEncoded
    @POST("payment/payBillGV")
    Call<BillGv24Response> getInfoBill24h(@Field("billId") String idBill);

    @FormUrlEncoded
    @POST("payment/payDirectly")
    Call<BillGv24Response> getInfoPaymnetByMoney(@Field("billId") String idBill);

    @FormUrlEncoded
    @POST("payment/payOnlineConfirm")
    Call<BillGv24Response> getInfoPaymnetByOnline(@Field("billId") String idBill);

    @FormUrlEncoded
    @POST("owner/forgotPassword")
    Call<ForgotPassResponse> forgotPassword(@Field("email") String email,
                                            @Field("username") String username);

}
