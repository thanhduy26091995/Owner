package com.hbbsolution.owner.maid_profile.choose_maid.presenter;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.maid_profile.choose_maid.model.SendRequestResponse;
import com.hbbsolution.owner.maid_profile.choose_maid.view.ChooseMaidView;
import com.hbbsolution.owner.model.TypeJobResponse;
import com.hbbsolution.owner.work_management.model.geocodemap.GeoCodeMapResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by buivu on 30/05/2017.
 */

public class ChooseMaidPresenter {
    private ApiInterface apiService;
    private ChooseMaidView view;

    public ChooseMaidPresenter(ChooseMaidView view) {
        this.view = view;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getAllTypeJob() {
        Call<TypeJobResponse> responseCall = apiService.getAllTypeJob();
        responseCall.enqueue(new Callback<TypeJobResponse>() {
            @Override
            public void onResponse(Call<TypeJobResponse> call, Response<TypeJobResponse> response) {
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    if (status) {
                        view.getAllTypeJob(response.body());
                    }
                } else {
                    view.displayError(response.message());
                }
            }

            @Override
            public void onFailure(Call<TypeJobResponse> call, Throwable t) {
                view.displayError(t.getMessage());
            }
        });
    }

    public void getLocationAddress(String addressOfMaid) {
        Call<GeoCodeMapResponse> responseCall = apiService.getLocaltionAddress("https://maps.googleapis.com/maps/api/geocode/json", addressOfMaid);
        responseCall.enqueue(new Callback<GeoCodeMapResponse>() {
            @Override
            public void onResponse(Call<GeoCodeMapResponse> call, Response<GeoCodeMapResponse> response) {
                if (response.isSuccessful()) {
                    GeoCodeMapResponse mGeoCodeMapResponse = response.body();
                    if (mGeoCodeMapResponse.getResults().size() > 0) {
                        double lat = mGeoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLat();
                        double lng = mGeoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLng();
                        if (lat != 0 || lng != 0) {
                            view.getLocationAddress(mGeoCodeMapResponse);
                        } else {
                            view.displayNotFoundLocation("Không tìm thấy vị trí");
                        }
                    } else {
                        view.displayNotFoundLocation("Không tìm thấy vị trí");
                    }
                }
            }

            @Override
            public void onFailure(Call<GeoCodeMapResponse> call, Throwable t) {
                view.displayError("LOcation" + t.getMessage());
            }
        });
    }

    public void sendRequest(String maidId, String title, String packageId, String workId, String description, Double price,
                            String addressName, Double lat, Double lng, String startAt, String endAt, Double hours, Boolean tools) {
        Call<SendRequestResponse> responseCall = apiService.sendRequest(maidId, title, packageId, workId, description, price, addressName, lat, lng, startAt, endAt, hours, tools);
        responseCall.enqueue(new Callback<SendRequestResponse>() {
            @Override
            public void onResponse(Call<SendRequestResponse> call, Response<SendRequestResponse> response) {
                if (response.isSuccessful()) {
                    view.sendRequestJob(response.body());
                } else {
                    view.displayError("SendRequest: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<SendRequestResponse> call, Throwable t) {
                view.displayError("SendRequest " + t.getMessage());
            }
        });
    }
}
