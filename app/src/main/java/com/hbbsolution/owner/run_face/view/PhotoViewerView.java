package com.hbbsolution.owner.run_face.view;

/**
 * Created by buivu on 31/08/2017.
 */

public interface PhotoViewerView {
    void isLoadImageSuccess(boolean result);

    void sendPushNotificationSuccessfully();

    void sendPushNotificationFailed(String error);
}
