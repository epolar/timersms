package xyz.eraise.timersms.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;

import xyz.eraise.timersms.data.pojo.SMSInfo;
import xyz.eraise.timersms.data.pojo.TaskInfo;
import xyz.eraise.timersms.data.source.SMSRepository;
import xyz.eraise.timersms.data.source.TasksDataSource;

/**
 * 创建日期： 2016/5/3.
 * 发送短信
 */
public class SendSMSService extends Service {

    public static final String ACTION_SEND_SMS = "xyz.eraise.timersms.ACTION_SEND_SMS";
    public static final String ACTION_CHANGE_TASK_STATE = "xyz.eraise.timersms.ACTION_CHANGE_TASK_STATE";

    public static final String EXTRA_TASK_Id = "extraTaskId";
    public static final String EXTRA_TASK_STATE = "extraTaskState";

    private SMSRepository mRepository;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int superResult = super.onStartCommand(intent, flags, startId);
        if (null == intent) {
            return superResult;
        }
        String action = intent.getAction();
        if (ACTION_SEND_SMS.equals(action)) {
            int smsId = intent.getIntExtra(EXTRA_TASK_Id, -1);
            if (smsId < 0) {
                return superResult;
            }
            sendSMS(smsId);
        } else if (ACTION_CHANGE_TASK_STATE.equals(action)) {
            int taskId = intent.getIntExtra(EXTRA_TASK_Id, -1);
            int taskState = intent.getIntExtra(EXTRA_TASK_STATE, -1);
            if (taskId != -1 && taskState != -1) {
                changeTaskState(taskId, taskState);
            }
        }
        return superResult;
    }

    private SMSRepository getRepository() {
        if (null == mRepository) {
            mRepository = SMSRepository.createDefaultRepository(getApplicationContext());
        }
        return mRepository;
    }

    private void sendSMS(int smsId) {
        getRepository().getSMS(smsId, new TasksDataSource.GetTaskCallback() {
            @Override
            public void onTasksLoaded(SMSInfo smsInfo) {

                if (null == smsInfo || null == smsInfo.tasks) {
                    return;
                }
                SmsManager manager = SmsManager.getDefault();
                for (TaskInfo task : smsInfo.tasks) {
                    manager.sendTextMessage(task.phoneNumber, null, smsInfo.content, createSentPendingIntent(task), createDeliverPendingIntent(task));
                    changeTaskState(task.id, TaskInfo.STATE_SENDING);
                }
                changeSMSState(smsInfo.id, true);
            }

            @Override
            public void onDataNotAvailable() {
                // 没有找到id对应的短信计划
            }
        });
    }

    private void changeSMSState(int id, boolean isSend) {

        getRepository().updateSMSState(id, isSend);
    }

    private void changeTaskState(int id, int state) {
        getRepository().updateTaskState(id, state);
    }

    private PendingIntent createSentPendingIntent(@NonNull  TaskInfo info) {
        Intent intent = new Intent(getApplicationContext(), SendSMSService.class);
        intent.setAction(ACTION_CHANGE_TASK_STATE);
        intent.putExtra(EXTRA_TASK_Id, info.id);
        intent.putExtra(EXTRA_TASK_STATE, TaskInfo.STATE_SENT);
        return PendingIntent.getService(getApplicationContext(), info.id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent createDeliverPendingIntent(@NonNull TaskInfo info) {
        Intent intent = new Intent(getApplicationContext(), SendSMSService.class);
        intent.setAction(ACTION_CHANGE_TASK_STATE);
        intent.putExtra(EXTRA_TASK_Id, info.id);
        intent.putExtra(EXTRA_TASK_STATE, TaskInfo.STATE_DELIVER);
        return PendingIntent.getService(getApplicationContext(), info.id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
