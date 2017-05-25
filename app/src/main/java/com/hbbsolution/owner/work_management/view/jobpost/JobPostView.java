package com.hbbsolution.owner.work_management.view.jobpost;

import com.hbbsolution.owner.model.TypeJobResponse;
import com.hbbsolution.owner.work_management.model.geocodemap.GeoCodeMapResponse;

/**
 * Created by tantr on 5/23/2017.
 */

public interface JobPostView {
    void getAllTypeJob(TypeJobResponse typeJobResponse);
    void getLocaltionAddress(GeoCodeMapResponse geoCodeMapResponse);
    void displayNotifyJobPost(boolean isJobPost);
    void displayNotFoundLocaltion();
    void displayError(String error);
}
