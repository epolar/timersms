package xyz.eraise.timersms.utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期： 2016/4/15.
 */
public class CommonUtils {

    public static <T> Observable<T> asyncTask(Observable.OnSubscribe<T> doInBackground) {
        return Observable.create(doInBackground)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
