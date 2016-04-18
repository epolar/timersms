package xyz.eraise.timersms.contacts;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View _rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
        ButterKnife.bind(this, _rootView);
        initView();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_confirm) {
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
    public void showContacts(List<ContactInfo> infos) {
        mAdapter = new ContactsAdapter(infos, getActivity());
        rvContent.setAdapter(mAdapter);
    }

    @Override
    public List<ContactInfo> getContactInfos() {
        return mAdapter.datas;
    }

    @Override
    public void setPresenter(ContactsContract.Presenter presenter) {
        this.mPresenter = presenter;
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

        public ContactsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
