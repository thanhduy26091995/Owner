package com.hbbsolution.owner.more.viet_pham.Presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.more.viet_pham.Model.RegisterResponse;
import com.hbbsolution.owner.more.viet_pham.View.SignUpView;

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
    private SignUpView mSignUpView;
    private ApiInterface mApiService;
    private File mFile;
    private RequestBody requestBody;
    private MultipartBody.Part fileImage;
    private RequestBody requestBodyUserName;
    private RequestBody requestBodyPassword;
    private RequestBody requestBodyEmail;
    private RequestBody requestBodyPhone;
    private RequestBody requestBodyName;
    private RequestBody requestBodyAddress;
    private RequestBody requestBodyGender;
    private RequestBody requestBodyLat;
    private RequestBody requestBodyLng;

    public RegisterPresenter(SignUpView mSignUpView) {
        this.mSignUpView = mSignUpView;
        mApiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void createAccount(String username, String password, String email, String phone, String name, String filePath, String address, double lat, double lng, int gender
            , String fileContentResolver) {
        if ((filePath.trim().length() != 0) && (fileContentResolver.trim().length() != 0)) {
            mFile = new File(filePath);
            requestBody = RequestBody.create(MediaType.parse(fileContentResolver), mFile);
            fileImage = MultipartBody.Part.createFormData("image", mFile.getName(), requestBody);
        }else
        {
            mFile = new File(filePath);
            requestBody = RequestBody.create(MediaType.parse(fileContentResolver), mFile);
            fileImage = null;
        }
        requestBodyUserName = RequestBody.create(MediaType.parse("text"), username);
        requestBodyPassword = RequestBody.create(MediaType.parse("text"), password);
        requestBodyEmail = RequestBody.create(MediaType.parse("text"), email);
        requestBodyPhone = RequestBody.create(MediaType.parse("text"), phone);
        requestBodyName = RequestBody.create(MediaType.parse("text"), name);
        requestBodyAddress = RequestBody.create(MediaType.parse("text"), address);
        requestBodyGender = RequestBody.create(MediaType.parse("text"), String.valueOf(gender));
        requestBodyLat = RequestBody.create(MediaType.parse("text"), String.valueOf(lat));
        requestBodyLng = RequestBody.create(MediaType.parse("text"), String.valueOf(lng));
        mApiService.createAccount(requestBodyUserName, requestBodyPassword, requestBodyEmail, requestBodyPhone, requestBodyName, requestBodyAddress, requestBodyLat,
                requestBodyLng, requestBodyGender, fileImage).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                Log.e("Tag1", String.valueOf(response.body().getStatus()));
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e("Tag2", "Fail");
            }
        });
    }
}
