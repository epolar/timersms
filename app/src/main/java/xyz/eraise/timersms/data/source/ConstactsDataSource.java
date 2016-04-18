package xyz.eraise.timersms.data.source;

import android.content.ContentProvider;
import android.support.annotation.NonNull;

import java.util.List;

import xyz.eraise.timersms.data.pojo.ContactInfo;
import xyz.eraise.timersms.utils.IBaseCallback;

/**
 * 创建日期： 2016/4/18.
 */
public class ConstactsDataSource {

    interface LoadContactsCallback extends IBaseCallback {

        void onContactsLoad(List<ContactInfo> contacts);

    }

    public void loadContacts(@NonNull  ContentProvider contentProvider, @NonNull  LoadContactsCallback callback) {
    }

}
