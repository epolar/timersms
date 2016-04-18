package xyz.eraise.timersms.addedittask;

import android.support.annotation.NonNull;

import xyz.eraise.timersms.data.pojo.SMSInfo;

/**
 * 创建日期： 2016/4/18.
 */
public class AddEditTaskPresenter implements AddEditTaskContract.Presenter {

    private AddEditTaskContract.View mView;

    public AddEditTaskPresenter(@NonNull AddEditTaskContract.View view) {
        this.mView = view;
    }

    @Override
    public void saveTask(SMSInfo info) {

    }

    @Override
    public void deleteTask(SMSInfo info) {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void update() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destory() {

    }
}
