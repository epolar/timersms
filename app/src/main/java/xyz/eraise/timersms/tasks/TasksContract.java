package xyz.eraise.timersms.tasks;

import java.util.List;

import xyz.eraise.timersms.IBasePresenter;
import xyz.eraise.timersms.IBaseView;
import xyz.eraise.timersms.data.pojo.SMSInfo;

/**
 * 创建日期： 2016/4/15.
 * 为任务列表的View和Presenter定一个接口，所有任务列表的Presenter和View都需要实现对应的接口
 */
public interface TasksContract {

    interface View extends IBaseView<TasksContract.Persenter> {

        void showSMSDetail(SMSInfo sms);

        void showAddNewSMS();

        void showData(List<SMSInfo> data, boolean isRefresh);

        void addShowSMS(SMSInfo sms);

        void removeShowSMS(SMSInfo sms);

        void setLoadingIndicator(boolean active);

        void showEmpty();

        void showLoadingTasksError();

    }

    interface Persenter extends IBasePresenter {

        void openTaskDetail(SMSInfo sms);

        void addNewTask();

        void loadTasks();

        void addTask(SMSInfo task);

        void removeTask(SMSInfo task);

    }
}
