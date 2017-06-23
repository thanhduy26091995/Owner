package com.hbbsolution.owner.more.phuc_tran.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.more.phuc_tran.view.AboutView;
import com.hbbsolution.owner.more.phuc_tran.model.AboutResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.data;

/**
 * Created by Tư Lầu on 6/15/17.
 */

public class AboutPresenter {
    private AboutView mAboutView;

    public AboutPresenter(AboutView mAboutView) {
        this.mAboutView = mAboutView;
    }

    public void getAbout(){
        getData("000000000000000000000001");
    }

    public void getTerm(){
        getData("000000000000000000000002");
    }

    public Object getData(String id) {
        try {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<AboutResponse> call = apiService.getAbout(id);
            call.enqueue(new Callback<AboutResponse>() {
                @Override
                public void onResponse(Call<AboutResponse> call, Response<AboutResponse> response) {
                    AboutResponse aboutResponse = response.body();
                    mAboutView.getAbout(aboutResponse.getData().getContent());
                }

                @Override
                public void onFailure(Call<AboutResponse> call, Throwable t) {
                    Log.e("Tag", t.toString());
                }
            });
        } catch (Exception e) {
            Log.v("Failed", e.getMessage());
        }

        return data;
    }
}
