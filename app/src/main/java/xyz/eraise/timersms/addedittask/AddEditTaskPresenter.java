package xyz.eraise.timersms.addedittask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import xyz.eraise.timersms.R;
import xyz.eraise.timersms.data.pojo.ContactInfo;
import xyz.eraise.timersms.data.pojo.SMSInfo;
import xyz.eraise.timersms.data.pojo.TaskInfo;
import xyz.eraise.timersms.data.source.ContactsDataSource;
import xyz.eraise.timersms.data.source.TasksDataSource;

/**
 * 创建日期： 2016/4/18.
 */
public class AddEditTaskPresenter implements AddEditTaskContract.Presenter {

    private AddEditTaskContract.View mView;

    private ArrayList<ContactInfo> contacts;

    private TasksDataSource mTasksDataSource;
    private ContactsDataSource mContactsDataSource;

    private long mSMSId;
    private Context context;

    public AddEditTaskPresenter(@NonNull Context context, @NonNull AddEditTaskContract.View view, long sMSId,
                                @NonNull  TasksDataSource tasksDataSource, @NonNull ContactsDataSource contactsDataSource) {
        this.mView = view;
        this.mSMSId = sMSId;
        this.mTasksDataSource = tasksDataSource;
        this.mContactsDataSource = contactsDataSource;
        this.context = context;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AddEditTaskContract.REQUEST_CONTACTS) {
                contacts = data.getParcelableArrayListExtra("data");
                mView.showContants(contacts);
            }
        }
    }

    @Override
    public void saveTask(SMSInfo info) {
        if (info.addTime == 0) {
            info.addTime = System.currentTimeMillis();
        }
        // 比现在还早的不加入
        if (info.scheduleTime < info.addTime) {
            mView.prompt(R.string.time_need_after_now);
            return;
        }
        mTasksDataSource.saveSMS(info, new TasksDataSource.ModifyTaskCallback() {
            @Override
            public void onFinish() {
                mView.saveFinish();
            }

            @Override
            public void onDataNotAvailable() {
                mView.prompt(R.string.save_failed);
            }
        });
    }

    @Override
    public void deleteTask(SMSInfo info) {
        mTasksDataSource.deleteSMS(info, new TasksDataSource.ModifyTaskCallback() {
            @Override
            public void onFinish() {
                mView.deleteFinish();
            }

            @Override
            public void onDataNotAvailable() {
                mView.prompt(R.string.delete_failed);
            }
        });
    }

    @Override
    public void initialize() {
        if (mSMSId >= 0) {
            mTasksDataSource.getSMS(mSMSId, new TasksDataSource.GetTaskCallback() {
                @Override
                public void onTasksLoaded(SMSInfo task) {
                    mView.showSMSData(task);
                    loadContactInfos(task);
                }

                @Override
                public void onDataNotAvailable() {
                    mView.prompt(R.string.get_sms_failed);
                }
            });
        }
    }

    private void loadContactInfos(SMSInfo task) {
        if (null == task.tasks || task.tasks.size() == 0) {
            return;
        }
        final ArrayList<ContactInfo> contactInfos = new ArrayList<>();
        Observable.from(task.tasks).map(new Func1<TaskInfo, ContactInfo>() {
            @Override
            public ContactInfo call(TaskInfo taskInfo) {
                return mContactsDataSource.getContactsByPhoneNumberSync(context.getContentResolver(), taskInfo.phoneNumber);
            }
        }).subscribe(new Action1<ContactInfo>() {
            @Override
            public void call(ContactInfo contactInfo) {
                contactInfos.add(contactInfo);
            }
        }, null, new Action0() {
            @Override
            public void call() {
                mView.showContants(contactInfos);
            }
        });
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
