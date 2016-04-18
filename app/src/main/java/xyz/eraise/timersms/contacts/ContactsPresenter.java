package xyz.eraise.timersms.contacts;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import xyz.eraise.timersms.data.pojo.ContactInfo;
import xyz.eraise.timersms.data.source.ContactsDataSource;

/**
 * 创建日期： 2016/4/18.
 */
public class ContactsPresenter implements ContactsContract.Presenter {

    private ContactsContract.View mView;
    private ContactsDataSource mContactsDataSource;
    private Context context;

    public ContactsPresenter (@NonNull Context context, @NonNull ContactsContract.View view) {
        this.mView = view;
        this.context = context;
        mContactsDataSource = new ContactsDataSource();
    }

    @Override
    public void confirmSelected() {

    }

    private void loadData() {
        mContactsDataSource.loadContacts(context.getContentResolver(), new ContactsDataSource.LoadContactsCallback() {
            @Override
            public void onContactsLoad(List<ContactInfo> contacts) {
                mView.showContacts(contacts);
            }

            @Override
            public void onDataNotAvailable() {
            }
        });
    }

    @Override
    public void initialize() {
        loadData();
    }

    @Override
    public void update() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destory() {
        context = null;
    }
}
