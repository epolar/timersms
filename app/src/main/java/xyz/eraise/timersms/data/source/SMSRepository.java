package xyz.eraise.timersms.data.source;

import android.content.Context;
import android.support.annotation.NonNull;

import xyz.eraise.timersms.data.pojo.SMSInfo;
import xyz.eraise.timersms.data.pojo.TaskInfo;
import xyz.eraise.timersms.data.source.local.LocalTasksDataSource;

/**
 * 创建日期： 2016/4/15.
 * 通过Repository让Presenter和DataSource分离开，假如说以后需要做扩展，
 * 比如说将数据保存到网络上就只主要变动这个类，对Presenter的改动小一些，或者是现在就已经预留出网络模块接口，
 * 提供出缓存加载等
 */
public class SMSRepository implements TasksDataSource {

    private TasksDataSource mLocalTasksDataSource;

    public static final SMSRepository createDefaultRepository(@NonNull Context context) {
        return new SMSRepository(new LocalTasksDataSource(context));
    }

    public SMSRepository(@NonNull LocalTasksDataSource localTasksDataSource) {
        mLocalTasksDataSource = localTasksDataSource;
    }

    @Override
    public void getSendedSMSTasks(@NonNull LoadTasksCallback callback) {
        mLocalTasksDataSource.getSendedSMSTasks(callback);
    }

    @Override
    public void getScheduleSMSTasks(@NonNull LoadTasksCallback callback) {
        mLocalTasksDataSource.getScheduleSMSTasks(callback);
    }

    @Override
    public void getSMS(@NonNull long taskId, @NonNull GetTaskCallback callback) {
        mLocalTasksDataSource.getSMS(taskId, callback);
    }

    @Override
    public void saveSMS(@NonNull SMSInfo task, @NonNull ModifyTaskCallback callback) {
        mLocalTasksDataSource.saveSMS(task, callback);
    }

    @Override
    public void sendedSMS(@NonNull SMSInfo sms, @NonNull ModifyTaskCallback callback) {
        mLocalTasksDataSource.sendedSMS(sms, callback);
    }

    @Override
    public void completeTaskInfo(@NonNull TaskInfo task, @NonNull ModifyTaskCallback callback) {
        mLocalTasksDataSource.completeTaskInfo(task, callback);
    }

    @Override
    public void deleteSMS(@NonNull SMSInfo task, @NonNull ModifyTaskCallback callback) {
        mLocalTasksDataSource.deleteSMS(task, callback);
    }

    @Override
    public void updateSMSState(int smsId, boolean isSend) {
        mLocalTasksDataSource.updateSMSState(smsId, isSend);
    }

    @Override
    public void updateTaskState(int taskId, int state) {
        mLocalTasksDataSource.updateTaskState(taskId, state);
    }
}
