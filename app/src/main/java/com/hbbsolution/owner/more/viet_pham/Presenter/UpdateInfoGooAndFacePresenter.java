package com.hbbsolution.owner.more.viet_pham.Presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.more.viet_pham.Model.signin_signup.DataUpdateResponse;
import com.hbbsolution.owner.more.viet_pham.View.MoreView;
import com.hbbsolution.owner.work_management.model.geocodemap.GeoCodeMapResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 6/21/2017.
 */

public class UpdateInfoGooAndFacePresenter {
    private MoreView mMoreView;
    private ApiInterface mApiService;
    private File mFile;
    private RequestBody requestBody;
    private MultipartBody.Part fileImage;
    private RequestBody requestBodyId;
    private RequestBody requestBodyToken;
    private RequestBody requestBodyDeviceToken;
    private RequestBody requestBodyEmail;
    private RequestBody requestBodyUserName;
    private RequestBody requestBodyPhone;
    private RequestBody requestBodyName;
    private RequestBody requestBodyLocation;
    private RequestBody requestBodyGender;
    private RequestBody requestBodyLat;
    private RequestBody requestBodyLng;
    private RequestBody requestBodyFileImage;

    public UpdateInfoGooAndFacePresenter(MoreView mMoreView) {
        this.mMoreView = mMoreView;
        mApiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void updateUserInfoGooAndFace(String id,String token,String deviceToken,String email,String username,
                                         String phone, String name, String location, double lat, double lng, int gender
            , String filePath) {
        requestBodyId = RequestBody.create(MediaType.parse("text"), id);
        requestBodyToken = RequestBody.create(MediaType.parse("text"), token);
        requestBodyDeviceToken = RequestBody.create(MediaType.parse("text"), deviceToken);
        requestBodyEmail = RequestBody.create(MediaType.parse("text"), email);
        requestBodyUserName = RequestBody.create(MediaType.parse("text"), username);
        requestBodyPhone = RequestBody.create(MediaType.parse("text"), phone);
        requestBodyName = RequestBody.create(MediaType.parse("text"), name);
        requestBodyLocation = RequestBody.create(MediaType.parse("text"), location);
        requestBodyGender = RequestBody.create(MediaType.parse("text"), String.valueOf(gender));
        requestBodyLat = RequestBody.create(MediaType.parse("text"), String.valueOf(lat));
        requestBodyLng = RequestBody.create(MediaType.parse("text"), String.valueOf(lng));
        requestBodyFileImage = RequestBody.create(MediaType.parse("text"), filePath);
        mApiService.updateGoogleAndFace(requestBodyId,requestBodyToken,requestBodyDeviceToken,requestBodyPhone,requestBodyUserName,requestBodyName,requestBodyEmail,
                requestBodyLocation,requestBodyLat,requestBodyLng,requestBodyGender,requestBodyFileImage).enqueue(new Callback<DataUpdateResponse>() {
            @Override
            public void onResponse(Call<DataUpdateResponse> call, Response<DataUpdateResponse> response) {
                DataUpdateResponse dataUpdateResponse = response.body();
                mMoreView.displaySignInGooAndFace(dataUpdateResponse);

            }

            @Override
            public void onFailure(Call<DataUpdateResponse> call, Throwable t) {

            }
        });
    }

    public void getLocaltionAddress(String addressofOwner) {
        Call<GeoCodeMapResponse> responseCall = mApiService.getLocaltionAddress("https://maps.googleapis.com/maps/api/geocode/json", addressofOwner);
        responseCall.enqueue(new Callback<GeoCodeMapResponse>() {
            @Override
            public void onResponse(Call<GeoCodeMapResponse> call, Response<GeoCodeMapResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("OK")) {
                        GeoCodeMapResponse mGeoCodeMapResponse = response.body();
                        double mLat = mGeoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLat();
                        double mLng = mGeoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLng();
                        if (mLat != 0 || mLng != 0) {
                            mMoreView.getLocaltionAddress(mGeoCodeMapResponse);
                        } else {
                            mMoreView.displayNotFoundLocaltion();
                        }
                    } else {
                        mMoreView.displayNotFoundLocaltion();
                    }
                }
            }

            @Override
            public void onFailure(Call<GeoCodeMapResponse> call, Throwable t) {
                Log.e("Tag", t.toString());
            }
        });
    }
}
