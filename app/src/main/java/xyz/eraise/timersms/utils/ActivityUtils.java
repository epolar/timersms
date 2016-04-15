package xyz.eraise.timersms.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * 创建日期： 2016/4/15.
 */
public class ActivityUtils {

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @NonNull int frameId) {
        if (null == fragmentManager || null == fragment) {
            return;
        }
        FragmentTransaction _transaction = fragmentManager.beginTransaction();
        _transaction.add(frameId, fragment);
        _transaction.commit();
    }

}
