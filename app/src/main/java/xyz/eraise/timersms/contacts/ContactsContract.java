package xyz.eraise.timersms.contacts;

import java.util.ArrayList;
import java.util.List;

import xyz.eraise.timersms.IBasePresenter;
import xyz.eraise.timersms.IBaseView;
import xyz.eraise.timersms.data.pojo.ContactInfo;

/**
 * 创建日期： 2016/4/18.
 */
public interface ContactsContract {

    interface View extends IBaseView<ContactsContract.Presenter> {

        void showContacts(List<ContactInfo> infos);

        List<ContactInfo> getContactInfos();

        void finishSelected(ArrayList<ContactInfo> selectedInfos);

        void setLoadIndicator(boolean show);

    }

    interface Presenter extends IBasePresenter {

        void confirmSelected();

    }

}
