package com.hbbsolution.owner.more.duy_nguyen.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.more.duy_nguyen.ContactView;
import com.hbbsolution.owner.more.duy_nguyen.model.ContactResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 21/06/2017.
 */

public class ContactPresenter {
    private ContactView contactView;
    private ApiInterface apiService;

    public ContactPresenter(ContactView contactView) {
        this.contactView = contactView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getContact()
    {
        Call<ContactResponse> call = apiService.getContact();
        call.enqueue(new Callback<ContactResponse>() {
            @Override
            public void onResponse(Call<ContactResponse> call, Response<ContactResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        ContactResponse contactResponse = response.body();
                        contactView.getContactSuccess(contactResponse.getData());
                    } catch (Exception e) {
                        contactView.getContactFail();
                        Log.e("exception", e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ContactResponse> call, Throwable t) {
                contactView.getContactFail();
            }
        });
    }
}
