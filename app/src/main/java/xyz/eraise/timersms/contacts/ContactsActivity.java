package xyz.eraise.timersms.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import xyz.eraise.timersms.R;

/**
 * 创建日期： 2016/4/18.
 */
public class ContactsActivity extends AppCompatActivity {

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
    }

}
