package xyz.eraise.timersms.addedittask;

import android.content.Intent;

import java.util.List;

import xyz.eraise.timersms.IBasePresenter;
import xyz.eraise.timersms.IBaseView;
import xyz.eraise.timersms.data.pojo.ContactInfo;
import xyz.eraise.timersms.data.pojo.SMSInfo;
import xyz.eraise.timersms.utils.IBaseCallback;

/**
 * 创建日期： 2016/4/14.
 */
public interface AddEditTaskContract {

    int REQUEST_CONTACTS = 1001;

    interface OperateCallback extends IBaseCallback {

        void onFinish(SMSInfo info);

    }

    interface View extends IBaseView<AddEditTaskContract.Presenter> {

        void showContants(List<ContactInfo> contactInfos);

        void showSMSData(SMSInfo info);

        void saveFinish();

        void deleteFinish();

        void setLoadingIndicator();

        void prompt(int msg);

    }

    interface Presenter extends IBasePresenter {

        void onActivityResult(int requestCode, int resultCode, Intent data);

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
