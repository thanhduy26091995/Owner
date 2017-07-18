package com.hbbsolution.owner.history;

import com.hbbsolution.owner.base.ConnectionInterface;

/**
 * Created by Administrator on 29/05/2017.
 */

public interface CommentHistoryView extends ConnectionInterface{
    void checkCommentSuccess (String message);
    void checkCommentFail ();
}
