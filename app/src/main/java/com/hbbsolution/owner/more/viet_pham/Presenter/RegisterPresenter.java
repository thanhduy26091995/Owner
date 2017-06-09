package com.hbbsolution.owner.more.viet_pham.Presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.more.viet_pham.Model.signin_signup.BodyResponse;
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
 * Created by Administrator on 5/22/2017.
 */

public class RegisterPresenter {
    private MoreView mMoreView;
    private ApiInterface mApiService;
    private File mFile;
    private RequestBody requestBody;
    private MultipartBody.Part fileImage;
    private RequestBody requestBodyUserName;
    private RequestBody requestBodyPassword;
    private RequestBody requestBodyEmail;
    private RequestBody requestBodyPhone;
    private RequestBody requestBodyName;
    private RequestBody requestBodyLocation;
    private RequestBody requestBodyGender;
    private RequestBody requestBodyLat;
    private RequestBody requestBodyLng;

    public RegisterPresenter(MoreView mMoreView) {
        this.mMoreView = mMoreView;
        mApiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void createAccount(String username, String password, String email, String phone, String name, String filePath, String location, double lat, double lng, int gender
            , String fileContentResolver) {
        if ((filePath.trim().length() != 0) && (fileContentResolver.trim().length() != 0)) {
            mFile = new File(filePath);
            requestBody = RequestBody.create(MediaType.parse(fileContentResolver), mFile);
            fileImage = MultipartBody.Part.createFormData("image", mFile.getName(), requestBody);
        } else {
            mFile = new File(filePath);
            requestBody = RequestBody.create(MediaType.parse(fileContentResolver), mFile);
            fileImage = null;
        }
        requestBodyUserName = RequestBody.create(MediaType.parse("text"), username);
        requestBodyPassword = RequestBody.create(MediaType.parse("text"), password);
        requestBodyEmail = RequestBody.create(MediaType.parse("text"), email);
        requestBodyPhone = RequestBody.create(MediaType.parse("text"), phone);
        requestBodyName = RequestBody.create(MediaType.parse("text"), name);
        requestBodyLocation = RequestBody.create(MediaType.parse("text"), location);
        requestBodyGender = RequestBody.create(MediaType.parse("text"), String.valueOf(gender));
        requestBodyLat = RequestBody.create(MediaType.parse("text"), String.valueOf(lat));
        requestBodyLng = RequestBody.create(MediaType.parse("text"), String.valueOf(lng));
        mApiService.createAccount(requestBodyUserName, requestBodyPassword, requestBodyEmail, requestBodyPhone, requestBodyName, requestBodyLocation, requestBodyLat,
                requestBodyLng, requestBodyGender, fileImage).enqueue(new Callback<BodyResponse>() {
            @Override
            public void onResponse(Call<BodyResponse> call, Response<BodyResponse> response) {
                try {
                    BodyResponse bodyResponse = response.body();
                    mMoreView.displaySignUpAndSignIn(bodyResponse);
                } catch (Exception e) {
                    Log.e("exception", e.toString());
                    mMoreView.displayError();
                }
            }

            @Override
            public void onFailure(Call<BodyResponse> call, Throwable t) {

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
