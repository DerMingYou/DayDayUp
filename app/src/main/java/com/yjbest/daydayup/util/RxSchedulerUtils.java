package com.yjbest.daydayup.util;

import io.reactivex.FlowableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Description: 在RxJava的使用过程中我们会频繁的调用subscribeOn()和observeOn(),通过Transformer结合,Observable.compose()我们可以复用这些代码
 * Data：2018/10/27-15:17
 * Author: DerMing_You
 */
public final class RxSchedulerUtils {

    //FlowableTransformer
    public static <T> ObservableTransformer<T, T> normalSchedulersTransformer() {

        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
