package xyz.eraise.timersms.tasks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import xyz.eraise.timersms.R;
import xyz.eraise.timersms.data.pojo.SMSInfo;

/**
 * 创建日期： 2016/4/15.
 */
public class TasksFragment extends Fragment implements TasksContract.View {

    @Bind(R.id.lv_content)
    ListViewCompat lvContent;
    @Bind(R.id.iv_icon)
    ImageView ivIcon;
    @Bind(R.id.tv_emtpy)
    TextView tvEmtpy;
    @Bind(R.id.container_empty)
    LinearLayout containerEmpty;
    @Bind(R.id.pb)
    MaterialProgressBar pb;

    private TasksContract.Persenter mPresenter;
    private TasksAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View _rootView = inflater.inflate(R.layout.fragment_tasks, container, false);
        ButterKnife.bind(this, _rootView);
        mAdapter = new TasksAdapter(getActivity());
        lvContent.setAdapter(mAdapter);
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mPresenter.destory();
    }

    @Override
    public void setPresenter(TasksContract.Persenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showSMSDetail(SMSInfo sms) {

    }

    @Override
    public void showAddNewSMS() {

    }

    @Override
    public void showData(List<SMSInfo> data, boolean isRefresh) {
        if (isRefresh)
            mAdapter.clear();
        mAdapter.addAll(data);
        if (isRefresh) {
            mAdapter.notifyDataSetInvalidated();
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addShowSMS(SMSInfo sms) {
        mAdapter.addSMSInfo(sms);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeShowSMS(SMSInfo sms) {
        mAdapter.removeSMSInfo(sms);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            pb.setVisibility(View.VISIBLE);
        } else {
            pb.setVisibility(View.GONE);
        }
    }

    @Override
    public void showEmpty() {
        pb.setVisibility(View.GONE);
        tvEmtpy.setText(R.string.load_error);
        lvContent.setVisibility(View.GONE);
        containerEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadingTasksError() {
        pb.setVisibility(View.GONE);
        tvEmtpy.setText(R.string.no_tasks);
        lvContent.setVisibility(View.GONE);
        containerEmpty.setVisibility(View.VISIBLE);
    }

}
