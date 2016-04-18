package xyz.eraise.timersms.addedittask;

import xyz.eraise.timersms.IBasePresenter;
import xyz.eraise.timersms.IBaseView;
import xyz.eraise.timersms.data.pojo.SMSInfo;
import xyz.eraise.timersms.utils.IBaseCallback;

/**
 * 创建日期： 2016/4/14.
 */
public interface AddEditTaskContract {

    interface OperateCallback extends IBaseCallback {

        void onFinish(SMSInfo info);

    }

    interface View extends IBaseView<AddEditTaskContract.Presenter> {

        void showData(SMSInfo info);

        void saveFinish();

        void deleteFinish();

        void setLoadingIndicator();

        void prompt(String msg);

    }

    interface Presenter extends IBasePresenter {

        /**
         * 保存计划
         * @param info
         */
        void saveTask(SMSInfo info);

        /**
         * 删除计划
         * @param info
         */
        void deleteTask(SMSInfo info);

    }

}
