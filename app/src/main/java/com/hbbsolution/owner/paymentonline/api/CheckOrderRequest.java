package com.hbbsolution.owner.paymentonline.api;

import android.content.Context;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.paymentonline.bean.CheckOrderBean;
import com.hbbsolution.owner.utils.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;


/**
 * Created by DucChinh on 6/13/2016.
 */
public class CheckOrderRequest {

    private CheckOrderRequestOnResult checkOrderRequestOnResult;

    public void execute(final Context pContext, CheckOrderBean checkOrderBean) {
        RequestParams lvParams = new RequestParams();
        lvParams.put("func", checkOrderBean.getFunc());
        lvParams.put("version", checkOrderBean.getVersion());
        lvParams.put("merchant_id", checkOrderBean.getMerchantID());
        lvParams.put("token_code", checkOrderBean.getTokenCode());
        lvParams.put("checksum", checkOrderBean.getChecksum());

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(Constants.TIMEOUT);
        client.get(Constants.MAIN_URL, lvParams, new AsyncHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (checkOrderRequestOnResult != null) {
                    String content = pContext.getString(R.string.session_timeout);
                    checkOrderRequestOnResult.onCheckOrderRequestOnResult(false, content);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content = new String(responseBody);
                try {
                    if (checkOrderRequestOnResult != null) {
                        checkOrderRequestOnResult.onCheckOrderRequestOnResult(true, content);
                    }
                } catch (NullPointerException e) {
                    if (checkOrderRequestOnResult != null) {
                        checkOrderRequestOnResult.onCheckOrderRequestOnResult(false, content);
                    }
                } catch (Exception e) {
                    if (checkOrderRequestOnResult != null) {
                        checkOrderRequestOnResult.onCheckOrderRequestOnResult(false, content);
                    }
                } catch (OutOfMemoryError e) {
                    if (checkOrderRequestOnResult != null) {
                        checkOrderRequestOnResult.onCheckOrderRequestOnResult(false, content);
                    }
                }
            }

        });
    }

    public void getCheckOrderRequestOnResult(CheckOrderRequestOnResult checkOrderRequestOnResult) {
        this.checkOrderRequestOnResult = checkOrderRequestOnResult;
    }

    public interface CheckOrderRequestOnResult {
        void onCheckOrderRequestOnResult(boolean result, String data);
    }
}
