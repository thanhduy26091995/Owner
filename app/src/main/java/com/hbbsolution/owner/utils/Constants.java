package com.hbbsolution.owner.utils;

import com.hbbsolution.owner.model.TypeJob;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by buivu on 28/04/2017.
 */

public class Constants {
    //Maid near by
    public static final String LAT = "lat";
    public static final String LNG = "lng";
    public static final String MAID_LIST = "maidList";
    public static final int FILTER_MAID_INTENT = 100;
    public static final int CAMERA_INTENT = 3;

    public static boolean isLoadTabDoing = false;

    public static final String MAIN_URL = "https://www.nganluong.vn/mobile_checkout_api_post.php";
    public static final String RETURN_URL = "https://www.nganluong.vn/nganluong/homeDeveloper/DeveloperBank.html";
    public static final String CANCEL_URL = "https://www.nganluong.vn/nganluong/homeDeveloper/DeveloperBank.html";
    public static final String NOTIFY_URL = "https://www.nganluong.vn/nganluong/homeDeveloper/DeveloperBank.html";

    public static final int TIMEOUT = 30000;

    public static final String MERCHANT_ID = "51185";
    public static final String MERCHANT_PASSWORD = "0fc156a3ed1cd742a4ec65fe52372d06";

    public static List<TypeJob> listTypeJob = new ArrayList<>();
}
