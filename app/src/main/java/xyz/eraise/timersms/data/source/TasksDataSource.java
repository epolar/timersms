package xyz.eraise.timersms.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import xyz.eraise.timersms.utils.IBaseCallback;
import xyz.eraise.timersms.data.pojo.SMSInfo;
import xyz.eraise.timersms.data.pojo.TaskInfo;

/**
 * 创建日期： 2016/4/15.
 */
public interface TasksDataSource {

    interface LoadTasksCallback extends IBaseCallback {

        void onTasksLoaded(List<SMSInfo> tasks);

    }

    interface GetTaskCallback extends IBaseCallback {

        void onTasksLoaded(SMSInfo task);

    }

    interface ModifyTaskCallback extends IBaseCallback {

        void onFinish();

    }

    /**
     * 获取已经执行过计划的任务
     * @param callback
     */
    void getSendedSMSTasks(@NonNull LoadTasksCallback callback);

    /**
     * 获取计划列表中的任务
     * @param callback
     */
    void getScheduleSMSTasks(@NonNull LoadTasksCallback callback);

    void getSMS(@NonNull long taskId, @NonNull GetTaskCallback callback);

    void saveSMS(@NonNull SMSInfo task, @NonNull ModifyTaskCallback callback);

    void sendedSMS(@NonNull SMSInfo sms, @NonNull ModifyTaskCallback callback);

    void completeTaskInfo(@NonNull TaskInfo task, @NonNull ModifyTaskCallback callback);

    void deleteSMS(@NonNull SMSInfo task, @NonNull ModifyTaskCallback callback);

}
