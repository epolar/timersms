package xyz.eraise.timersms.contacts;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import xyz.eraise.timersms.R;
import xyz.eraise.timersms.data.pojo.ContactInfo;

/**
 * 创建日期： 2016/4/18.
 */
public class ContactsFragment extends Fragment implements ContactsContract.View {

    @Bind(R.id.rv_content)
    RecyclerView rvContent;

    private ContactsAdapter mAdapter;
    private ContactsContract.Presenter mPresenter;
    private Action1<ArrayList<ContactInfo>> mFinishCallback;
    private MaterialDialog mDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View _rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
        ButterKnife.bind(this, _rootView);
        initView();
        // 标记为有菜单的
        setHasOptionsMenu(true);
        return _rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.initialize();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.update();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destory();
    }

    private void initView() {
        rvContent.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // inflater 出菜单
        inflater.inflate(R.menu.menu_contacts, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_confirm) {
            mPresenter.confirmSelected();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void finishSelected(final ArrayList<ContactInfo> selectedInfos) {
        if (null == mFinishCallback) {
            return;
        }
        Observable.just(selectedInfos).subscribe(mFinishCallback);
    }

    @Override
    public void showContacts(List<ContactInfo> infos) {
        mAdapter = new ContactsAdapter(infos, getActivity());
        rvContent.setAdapter(mAdapter);
    }

    @Override
    public void setLoadIndicator(boolean show) {
        if (show) {
            if (null == mDialog || !mDialog.isShowing()) {
                mDialog = new MaterialDialog.Builder(getActivity()).progress(true, 0).show();
            }
        } else {
            if (null != mDialog) {
                mDialog.dismiss();
                mDialog = null;
            }
        }
    }

    @Override
    public List<ContactInfo> getContactInfos() {
        return mAdapter.datas;
    }

    @Override
    public void setPresenter(ContactsContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    public void setFinishSelectedCallback(Action1<ArrayList<ContactInfo>> callback) {
        this.mFinishCallback = callback;
    }

    static class ContactsAdapter extends RecyclerView.Adapter<ContactsViewHolder> {

        private List<ContactInfo> datas;
        private LayoutInflater inflater;

        ContactsAdapter(List<ContactInfo> datas, Context context) {
            this.datas = datas;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ContactsViewHolder(inflater.inflate(R.layout.item_contact, parent, false));
        }

        @Override
        public void onBindViewHolder(ContactsViewHolder holder, int position) {
            ContactInfo _info = datas.get(position);
            holder.tvName.setText(_info.contactName);
            holder.tvNumber.setText(_info.phoneNumber);
            holder.btnSelected.setChecked(_info.isSelected);
            holder.info = _info;
        }

        @Override
        public int getItemCount() {
            return null == datas ? 0 : datas.size();
        }
    }

    static class ContactsViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_number)
        TextView tvNumber;
        @Bind(R.id.btn_is_selected)
        AppCompatCheckBox btnSelected;

        ContactInfo info;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.btn_is_selected)
        public void clickSelectBtn() {
            if (null != info) {
                info.isSelected = !info.isSelected;
            }
        }
    }
}
