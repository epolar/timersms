package xyz.eraise.timersms.contacts;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
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
        List<ContactInfo> infos = mView.getContactInfos();
        if (null == infos) {
            return;
        } else {
            mView.setLoadIndicator(true);
            Observable.just(infos)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Func1<List<ContactInfo>, ArrayList<ContactInfo>>() {
                        @Override
                        public ArrayList<ContactInfo> call(List<ContactInfo> contactInfos) {
                            ArrayList<ContactInfo> _results = new ArrayList<>();
                            ContactInfo _temp;
                            for (int _index = 0; _index < contactInfos.size(); _index ++) {
                                _temp = contactInfos.get(_index);
                                if (_temp.isSelected) {
                                    _results.add(_temp);
                                }
                            }
                            return _results;
                        }
                    }).subscribe(new Action1<ArrayList<ContactInfo>>() {
                @Override
                public void call(ArrayList<ContactInfo> contactInfos) {
                    mView.setLoadIndicator(false);
                    mView.finishSelected(contactInfos);
                }
            });
        }
    }

    private void loadData() {
        mView.setLoadIndicator(true);
        mContactsDataSource.loadContacts(context.getContentResolver(), new ContactsDataSource.LoadContactsCallback() {
            @Override
            public void onContactsLoad(List<ContactInfo> contacts) {
                mView.showContacts(contacts);
                mView.setLoadIndicator(false);
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
