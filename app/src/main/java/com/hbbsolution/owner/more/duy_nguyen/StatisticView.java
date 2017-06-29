package com.hbbsolution.owner.more.duy_nguyen;

import com.hbbsolution.owner.more.duy_nguyen.model.Task;

import java.util.List;

/**
 * Created by Administrator on 01/06/2017.
 */

public interface StatisticView {
    void getStatisticSuccess(List<Task> listTask ,long total,long wallet);
    void getStatisticFail();
}
