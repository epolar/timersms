package xyz.eraise.timersms.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import xyz.eraise.timersms.BuildConfig;
import xyz.eraise.timersms.data.pojo.SMSInfo;
import xyz.eraise.timersms.data.pojo.TaskInfo;
import xyz.eraise.timersms.data.source.TasksDataSource;
import xyz.eraise.timersms.utils.CommonUtils;
import xyz.eraise.timersms.utils.DataNotAvailableAction;
import xyz.eraise.timersms.utils.ELog;

/**
 * 创建日期： 2016/4/15.
 */
public class LocalTasksDataSource implements TasksDataSource {

    private final LiteOrm mLiteOrm;
    private final ELog mLog = new ELog("LocalTasksDataSource");

    public LocalTasksDataSource(Context context) {
        mLiteOrm = LiteOrm.newCascadeInstance(context, "tasks.db");
        mLiteOrm.setDebugged(BuildConfig.DEBUG);
    }

    private void getSMSTasks(@NonNull final LoadTasksCallback callback, boolean isSended) {
        final String sended = isSended ? "true" : "false";
        CommonUtils.asyncTask(new Observable.OnSubscribe<ArrayList<SMSInfo>>() {
            @Override
            public void call(Subscriber<? super ArrayList<SMSInfo>> subscriber) {
                try {
                    ArrayList<SMSInfo> result = mLiteOrm.query(QueryBuilder.create(SMSInfo.class).whereEquals("sendState", sended));
                    subscriber.onNext(result);
                } catch (Exception e) {
                    mLog.w(e);
                    subscriber.onError(e);
                }
            }
        }).subscribe(new Action1<ArrayList<SMSInfo>>() {
                    @Override
                    public void call(ArrayList<SMSInfo> data) {
                        if (null != callback) {
                            callback.onTasksLoaded(data);
                        }
                    }
                }, new DataNotAvailableAction(callback));
    }

    @Override
    public void getSendedSMSTasks(@NonNull LoadTasksCallback callback) {
        getSMSTasks(callback, true);
    }

    @Override
    public void getScheduleSMSTasks(@NonNull LoadTasksCallback callback) {
        getSMSTasks(callback, false);
    }

    @Override
    public void getSMS(@NonNull long taskId, @NonNull GetTaskCallback callback) {
        final long _taskId = taskId;
        final GetTaskCallback _callback = callback;
        CommonUtils.asyncTask(new Observable.OnSubscribe<SMSInfo>() {
            @Override
            public void call(Subscriber<? super SMSInfo> subscriber) {
                try {
                    SMSInfo _result = mLiteOrm.queryById(_taskId, SMSInfo.class);
                    subscriber.onNext(_result);
                } catch (Exception e) {
                    mLog.w(e);
                    subscriber.onError(e);
                }
            }
        }).subscribe(new Action1<SMSInfo>() {
            @Override
            public void call(SMSInfo smsInfo) {
                if (null != _callback) {
                    _callback.onTasksLoaded(smsInfo);
                }
            }
        }, new DataNotAvailableAction(_callback));
    }

    @Override
    public void saveSMS(@NonNull SMSInfo task, @NonNull ModifyTaskCallback callback) {
        final SMSInfo _task = task;
        final ModifyTaskCallback _callback = callback;
        CommonUtils.asyncTask(new Observable.OnSubscribe<ModifyTaskCallback>() {
            @Override
            public void call(Subscriber<? super ModifyTaskCallback> subscriber) {
                try {
                    long row = mLiteOrm.save(_task);
                    if (row <= 0) {
                        subscriber.onError(new IllegalAccessException("插入/更新失败"));
                    } else {
                        subscriber.onNext(_callback);
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribe(new OnFinishAction(), new DataNotAvailableAction(callback));
    }

    @Override
    public void sendedSMS(@NonNull SMSInfo sms, @NonNull ModifyTaskCallback callback) {
        final SMSInfo _task = sms;
        _task.sendState = true;
        saveSMS(_task, callback);
    }

    @Override
    public void completeTaskInfo(@NonNull TaskInfo task, @NonNull ModifyTaskCallback callback) {
        final TaskInfo _task = task;
        final ModifyTaskCallback _callback = callback;
        _task.isSended = true;
        CommonUtils.asyncTask(new Observable.OnSubscribe<ModifyTaskCallback>() {
            @Override
            public void call(Subscriber<? super ModifyTaskCallback> subscriber) {
                try {
                    long row = mLiteOrm.save(_task);
                    if (row <= 0) {
                        subscriber.onError(new IllegalAccessException("更新失败"));
                    } else {
                        subscriber.onNext(_callback);
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribe(new OnFinishAction(), new DataNotAvailableAction(callback));
    }

    @Override
    public void deleteSMS(@NonNull SMSInfo sms, @NonNull ModifyTaskCallback callback) {
        final SMSInfo _sms = sms;
        final ModifyTaskCallback _callback = callback;
        CommonUtils.asyncTask(new Observable.OnSubscribe<ModifyTaskCallback>() {
            @Override
            public void call(Subscriber<? super ModifyTaskCallback> subscriber) {
                try {
                    long row = mLiteOrm.delete(_sms);
                    mLiteOrm.delete(_sms.tasks);
                    if (row <= 0) {
                        subscriber.onError(new IllegalAccessException("删除失败"));
                    } else {
                        subscriber.onNext(_callback);
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribe(new OnFinishAction(), new DataNotAvailableAction(callback));
    }

    static class OnFinishAction implements Action1<ModifyTaskCallback> {

        @Override
        public void call(ModifyTaskCallback callback) {
            if (null != callback) {
                callback.onFinish();
            }
        }
    }
}
