package com.hbbsolution.owner.history;

import com.hbbsolution.owner.base.ConnectionInterface;

/**
 * Created by Administrator on 26/05/2017.
 */

public interface CommentView extends ConnectionInterface {
    void commentSuccess (String message);
    void commentFail (String message);
}
