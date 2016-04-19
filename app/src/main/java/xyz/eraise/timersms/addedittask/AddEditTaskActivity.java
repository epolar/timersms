package xyz.eraise.timersms.addedittask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import xyz.eraise.timersms.R;
import xyz.eraise.timersms.data.source.ContactsDataSource;
import xyz.eraise.timersms.data.source.SMSRepository;
import xyz.eraise.timersms.data.source.local.LocalTasksDataSource;
import xyz.eraise.timersms.utils.ActivityUtils;

/**
 * 创建日期： 2016/4/18.
 */
public class AddEditTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        Toolbar _toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);
        ActionBar _ab = getSupportActionBar();
        _ab.setDisplayHomeAsUpEnabled(true);
        _ab.setDisplayShowHomeEnabled(true);

        AddEditTaskFragment _fragment = (AddEditTaskFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (null == _fragment) {
            _fragment = new AddEditTaskFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), _fragment, R.id.content_frame);
        }

        long smsId = -1;
        if (getIntent().hasExtra("smsId")) {
            smsId = getIntent().getIntExtra("smsId", -1);
            _ab.setTitle(R.string.edit_sms_task);
        } else {
            _ab.setTitle(R.string.add_sms_task);
        }

        SMSRepository _smsrepository = new SMSRepository(new LocalTasksDataSource(this));
        ContactsDataSource _contactsDataSource = new ContactsDataSource();
        AddEditTaskPresenter _presenter = new AddEditTaskPresenter(getApplicationContext(), _fragment, smsId, _smsrepository, _contactsDataSource);
        _fragment.setPresenter(_presenter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
