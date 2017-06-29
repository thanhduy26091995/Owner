package com.hbbsolution.owner.more.viet_pham.View;

import com.hbbsolution.owner.model.AnnouncementResponse;

/**
 * Created by buivu on 29/06/2017.
 */

public interface MoreForAnnouncementView {
    void onAnnouncement(AnnouncementResponse announcementResponse);

    void offAnnouncement(AnnouncementResponse announcementResponse);

    void getError(String error);
}
