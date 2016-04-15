package xyz.eraise.timersms.utils;

import rx.functions.Action1;

/**
 * 创建日期： 2016/4/15.
 */
public class DataNotAvailableAction implements Action1<Throwable> {

    private final IBaseCallback mCallback;

    public DataNotAvailableAction(IBaseCallback callback) {
        this.mCallback = callback;
    }

    @Override
    public void call(Throwable throwable) {
        if (null != mCallback) {
            mCallback.onDataNotAvailable();
        }
    }
}
