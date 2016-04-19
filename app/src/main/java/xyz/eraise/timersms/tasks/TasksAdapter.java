package xyz.eraise.timersms.tasks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.eraise.timersms.R;
import xyz.eraise.timersms.data.pojo.SMSInfo;
import xyz.eraise.timersms.data.pojo.TaskInfo;

/**
 * 创建日期： 2016/4/19.
 */
public class TasksAdapter extends BaseAdapter {

    private List<SMSInfo> datas;
    private Context mContext;
    private SimpleDateFormat mTimeFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm");

    public TasksAdapter(@NonNull Context context) {
        this.mContext = context;
        datas = new ArrayList<>();
    }

    public void addSMSInfo(SMSInfo info) {
        this.datas.add(info);
    }

    public void addAll(Collection<SMSInfo> newDatas) {
        this.datas.addAll(newDatas);
    }

    public void clear() {
        this.datas.clear();
    }

    public void removeSMSInfo(SMSInfo info) {
        this.datas.remove(info);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public SMSInfo getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SMSViewHolder _holder;
        SMSInfo _info = getItem(i);
        if (null == view) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_sms, viewGroup, false);
            _holder = new SMSViewHolder(view);
            view.setTag(_holder);
        } else {
            _holder = (SMSViewHolder) view.getTag();
        }
        for (TaskInfo _target : _info.tasks) {
            _holder.tvTarget.append(_target.phoneNumber + ";");
        }
        _holder.tvContent.setText(_info.content);
        _holder.tvTime.setText(mTimeFormat.format(new Date(_info.scheduleTime)));
        return view;
    }

    static class SMSViewHolder {
        @Bind(R.id.tv_target)
        AppCompatTextView tvTarget;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.tv_time)
        TextView tvTime;

        SMSViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
