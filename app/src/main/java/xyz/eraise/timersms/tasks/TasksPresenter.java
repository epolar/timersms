package xyz.eraise.timersms.tasks;

import android.support.annotation.NonNull;

import java.util.List;

import xyz.eraise.timersms.data.pojo.SMSInfo;
import xyz.eraise.timersms.data.source.TasksDataSource;

/**
 * 创建日期： 2016/4/15.
 */
public class TasksPresenter implements TasksContract.Persenter {

    private TasksContract.View mView;
    private TasksDataSource mDataSource;

    public TasksPresenter(@NonNull TasksContract.View view, @NonNull TasksDataSource dataSource) {
        this.mView = view;
        this.mDataSource = dataSource;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void update() {
        mView.setLoadingIndicator(true);
        loadTasks();
    }

    @Override
    public void pause() {

    }

    @Override
    public void destory() {

    }

    @Override
    public void openTaskDetail(SMSInfo sms) {
        mView.showSMSDetail(sms);
    }

    @Override
    public void addNewTask() {
        mView.showAddNewSMS();
    }

    @Override
    public void loadTasks() {
        mDataSource.getScheduleSMSTasks(new TasksDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<SMSInfo> tasks) {
                if (tasks.size() == 0) {
                    mView.showEmpty();
                } else {
                    mView.showData(tasks, true);
                    mView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onDataNotAvailable() {
                mView.showLoadingTasksError();
            }
        });
    }

    @Override
    public void addTask(SMSInfo task) {
        mView.addShowSMS(task);
    }

    @Override
    public void removeTask(SMSInfo task) {
        mView.removeShowSMS(task);
    }

}
