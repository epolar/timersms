package xyz.eraise.timersms.data.source;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import xyz.eraise.timersms.data.pojo.ContactInfo;
import xyz.eraise.timersms.utils.CommonUtils;
import xyz.eraise.timersms.utils.DataNotAvailableAction;
import xyz.eraise.timersms.utils.IBaseCallback;

/**
 * 创建日期： 2016/4/18.
 */
public class ContactsDataSource {

    public interface LoadContactsCallback extends IBaseCallback {

        void onContactsLoad(List<ContactInfo> contacts);

    }

    public void loadContacts(@NonNull ContentResolver contentProvider, @NonNull  LoadContactsCallback callback) {
        final LoadContactsCallback _callback = callback;
        final ContentResolver _contentProvider = contentProvider;
        CommonUtils.asyncTask(new Observable.OnSubscribe<ArrayList<ContactInfo>>() {
            @Override
            public void call(Subscriber<? super ArrayList<ContactInfo>> subscriber) {
                String[] selectColumns = new String[]{ContactsContract.Data.DISPLAY_NAME, ContactsContract.Data.DATA1};
                StringBuilder _sb = new StringBuilder();
                _sb.append(ContactsContract.Data.MIMETYPE)
                        .append("==")
                        .append(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .append(" AND ")
                        .append(ContactsContract.Data.HAS_PHONE_NUMBER)
                        .append("==1");
                Cursor cursor = _contentProvider.query(ContactsContract.Data.CONTENT_URI, selectColumns, null, null , null);
                ArrayList<ContactInfo> contacts = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    contacts.add(new ContactInfo(cursor.getString(0), cursor.getString(1)));
                }
                subscriber.onNext(contacts);
            }
        }).subscribe(new Action1<ArrayList<ContactInfo>>() {
            @Override
            public void call(ArrayList<ContactInfo> contactInfos) {
                _callback.onContactsLoad(contactInfos);
            }
        }, new DataNotAvailableAction(callback));
    }

}
