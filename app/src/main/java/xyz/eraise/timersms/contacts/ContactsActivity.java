package xyz.eraise.timersms.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import rx.functions.Action1;
import xyz.eraise.timersms.R;
import xyz.eraise.timersms.data.pojo.ContactInfo;
import xyz.eraise.timersms.utils.ActivityUtils;

/**
 * 创建日期： 2016/4/18.
 */
public class ContactsActivity extends AppCompatActivity {

    private Action1<ArrayList<ContactInfo>> mFinishSelectedCallback = new Action1<ArrayList<ContactInfo>>() {
        @Override
        public void call(ArrayList<ContactInfo> contactInfos) {
            Intent _intent = new Intent();
            _intent.putParcelableArrayListExtra("data", contactInfos);
            setResult(RESULT_OK, _intent);
            finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Toolbar _toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);
        ActionBar _ab = getSupportActionBar();
        _ab.setDisplayShowHomeEnabled(true);
        _ab.setDisplayHomeAsUpEnabled(true);
        _ab.setTitle(R.string.select_receive_contact);

        ContactsFragment _contactsFragment = (ContactsFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (null == _contactsFragment) {
            _contactsFragment = new ContactsFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), _contactsFragment, R.id.content_frame);
        }

        ContactsPresenter _presenter = new ContactsPresenter(getApplicationContext(), _contactsFragment);
        _contactsFragment.setPresenter(_presenter);
        _contactsFragment.setFinishSelectedCallback(mFinishSelectedCallback);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
