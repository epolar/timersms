package xyz.eraise.timersms.data.source;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

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

    protected String[] selectColumns = new String[]{ContactsContract.Data.DISPLAY_NAME, ContactsContract.Data.DATA1, ContactsContract.Data.PHOTO_THUMBNAIL_URI};
    private LruCache<String, ContactInfo> mContactInfoCache;

    public interface LoadContactsCallback extends IBaseCallback {

        void onContactsLoad(List<ContactInfo> contacts);

    }

    public interface GetContactCallback extends IBaseCallback {

        void onContactGet(ContactInfo contact);

    }

    public ContactInfo getContactsByPhoneNumberSync(@NonNull ContentResolver contentResolver, @NonNull final String phoneNumber) {
        ContactInfo _result = null;
        if (null == mContactInfoCache) {
            mContactInfoCache = new LruCache<>(50);
        } else {
            _result = mContactInfoCache.get(phoneNumber);
        }
        if (null != _result) {
            // 已经从缓存中取到了，那就直接拿来用了
            return _result;
        }

        StringBuilder _sb = new StringBuilder();
        _sb.append(ContactsContract.Data.MIMETYPE)
                .append("==")
                .append(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .append(" AND ")
                .append(ContactsContract.Data.DATA1)
                .append("==")
                .append(phoneNumber);
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, selectColumns, _sb.toString(), null ,null);
        if (cursor.getCount() == 0) {
            _result = new ContactInfo(phoneNumber, phoneNumber, null);
        } else {
            cursor.moveToNext();
            _result = new ContactInfo(cursor.getString(0), cursor.getString(1), cursor.getString(2));
        }
        mContactInfoCache.put(phoneNumber, _result);
        return _result;
    }

    public void getContactsByPhoneNumber(@NonNull ContentResolver contentResolver, @NonNull final String phoneNumber, @NonNull ContactsDataSource.GetContactCallback callback) {
        final ContentResolver _contentResolver = contentResolver;
        final String _phoneNumber = phoneNumber;
        final ContactsDataSource.GetContactCallback _callback = callback;
        CommonUtils.asyncTask(new Observable.OnSubscribe<ContactInfo>() {
            @Override
            public void call(Subscriber<? super ContactInfo> subscriber) {
                subscriber.onNext(getContactsByPhoneNumberSync(_contentResolver, _phoneNumber));
            }
        }).subscribe(new Action1<ContactInfo>() {
            @Override
            public void call(ContactInfo contactInfo) {
                _callback.onContactGet(contactInfo);
            }
        });
    }

    public void loadContacts(@NonNull ContentResolver contentResolver, @NonNull  LoadContactsCallback callback) {
        final LoadContactsCallback _callback = callback;
        final ContentResolver _contentResolver = contentResolver;
        CommonUtils.asyncTask(new Observable.OnSubscribe<ArrayList<ContactInfo>>() {
            @Override
            public void call(Subscriber<? super ArrayList<ContactInfo>> subscriber) {
                StringBuilder _sb = new StringBuilder();
                _sb.append(ContactsContract.Data.MIMETYPE)
                        .append("==")
                        .append(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .append(" AND ")
                        .append(ContactsContract.Data.HAS_PHONE_NUMBER)
                        .append("==1");
                Cursor cursor = _contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, selectColumns, null, null , null);
                ArrayList<ContactInfo> contacts = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    contacts.add(new ContactInfo(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
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
