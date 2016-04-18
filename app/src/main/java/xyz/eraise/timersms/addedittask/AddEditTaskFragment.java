package xyz.eraise.timersms.addedittask;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.eraise.timersms.R;
import xyz.eraise.timersms.contacts.ContactsActivity;
import xyz.eraise.timersms.data.pojo.SMSInfo;

/**
 * 创建日期： 2016/4/18.
 */
public class AddEditTaskFragment extends Fragment implements AddEditTaskContract.View {

    private static final int REQUEST_CONTACTS = 1001;

    @Bind(R.id.et_target)
    AutoCompleteTextView etTarget;
    @Bind(R.id.btn_date)
    AppCompatButton btnDate;
    @Bind(R.id.btn_time)
    AppCompatButton btnTime;
    @Bind(R.id.btn_contacts)
    ImageButton btnContacts;
    @Bind(R.id.et_msg_content)
    EditText etMsgContent;
    @Bind(R.id.btn_save)
    FloatingActionButton btnSave;

    private int minute = -1;
    private int hourOfDay = -1;
    private int dayOfMonth = -1;
    private int month = -1;
    private int year = -1;

    private MaterialDialog mDialog;

    protected AddEditTaskContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View _rootView = inflater.inflate(R.layout.fragment_add_edit_task, container, false);
        ButterKnife.bind(this, _rootView);

        Calendar _calendar = Calendar.getInstance();
        dayOfMonth = _calendar.get(Calendar.DAY_OF_MONTH);
        month =_calendar.get(Calendar.MONTH);
        year = _calendar.get(Calendar.YEAR);
        btnDate.setText(year + " - " + (month + 1) + " - " + dayOfMonth);

        return _rootView;
    }

    @Override
    public void showData(SMSInfo info) {

    }

    @Override
    public void saveFinish() {
        getActivity().finish();
    }

    @Override
    public void deleteFinish() {
        getActivity().finish();
    }

    @Override
    public void setLoadingIndicator() {
        ProgressDialog.show(getActivity(), null, null);
        if (null != mDialog && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        mDialog = new MaterialDialog.Builder(getActivity())
                .progress(true, 0)
                .build();
        mDialog.show();
    }

    @Override
    public void setPresenter(AddEditTaskContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void prompt(String msg) {
        if (null != mDialog && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
        new MaterialDialog.Builder(getActivity())
                .content(msg)
                .build()
                .show();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.initialize();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.update();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mPresenter.destory();
    }

    @OnClick({R.id.btn_time, R.id.btn_date, R.id.btn_contacts, R.id.btn_save})
    public void onClick(View v) {
        if (v == btnDate) {
            // 日期选择
            selectedDate();
        } else if (v == btnTime) {
            // 时间选择
            selectedTime();
        } else if (v == btnContacts) {
            // 选择联系人
            startActivityForResult(new Intent(getActivity(), ContactsActivity.class), REQUEST_CONTACTS);
        } else if (v == btnSave) {
            // 保存
        }
    }

    private void selectedTime() {
        Calendar _calendar = Calendar.getInstance();
        int _hourOfDay = hourOfDay == -1 ? _calendar.get(Calendar.HOUR_OF_DAY) : hourOfDay;
        int _minute = minute == -1 ? _calendar.get(Calendar.MINUTE) : minute;
        new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                AddEditTaskFragment.this.hourOfDay = hourOfDay;
                AddEditTaskFragment.this.minute = minute;
                btnTime.setText(hourOfDay + " : " + minute);
            }
        }, _hourOfDay, _minute, true).show();
    }

    private void selectedDate() {
        new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                AddEditTaskFragment.this.year = year;
                AddEditTaskFragment.this.month = monthOfYear;
                AddEditTaskFragment.this.dayOfMonth = dayOfMonth;
                btnDate.setText(year + " - " + (monthOfYear + 1) + " - " + dayOfMonth);
            }
        }, year, month, dayOfMonth)
                .show();
    }

}
