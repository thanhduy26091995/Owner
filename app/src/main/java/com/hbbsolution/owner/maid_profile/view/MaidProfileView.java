package com.hbbsolution.owner.maid_profile.view;

import com.hbbsolution.owner.work_management.model.listcommentmaid.CommentMaidResponse;

/**
 * Created by tantr on 5/22/2017.
 */

public interface  MaidProfileView {

    void getListCommentMaid(CommentMaidResponse mCommentMaidResponse);
    void getMoreListCommentMaid(CommentMaidResponse mCommentMaidResponse);
    void responseChosenMaid(boolean isResponseChosenMaid);
    void getMessager();
}
