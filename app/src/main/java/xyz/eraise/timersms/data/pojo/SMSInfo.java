package xyz.eraise.timersms.data.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.JSON;
import com.litesuits.orm.db.annotation.MapCollection;
import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;
import com.litesuits.orm.db.enums.Relation;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期： 2016/4/15.
 * 定时发送的任务信息
 */
@Table("SMSInfo")
public class SMSInfo implements Parcelable {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    public int id;

    /**
     * 计划发送时间
     */
    public long scheduleTime;

    /**
     * 创建日期
     */
    public long addTime;

    /**
     * 消息内容
     */
    public String content;

    /**
     * 消息发送状态
     * {@code true} 表示已经发送
     */
    public boolean sendState;

    @Mapping(Relation.OneToMany)
    @MapCollection(ArrayList.class)
    public List<TaskInfo> tasks;

    @Override
    public boolean equals(Object o) {
        if (o instanceof SMSInfo) {
            return ((SMSInfo)o).id == this.id;
        } else {
            return false;
        }
    }

    public SMSInfo(){}

    protected SMSInfo(Parcel in) {
        id = in.readInt();
        scheduleTime = in.readLong();
        addTime = in.readLong();
        content = in.readString();
        sendState = in.readByte() != 0x00;
        if (in.readByte() == 0x01) {
            tasks = JSON.parseArray(in.readString(), TaskInfo.class);
        } else {
            tasks = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(scheduleTime);
        dest.writeLong(addTime);
        dest.writeString(content);
        dest.writeByte((byte) (sendState ? 0x01 : 0x00));
        if (tasks == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeString(JSON.toJSONString(tasks));
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SMSInfo> CREATOR = new Parcelable.Creator<SMSInfo>() {
        @Override
        public SMSInfo createFromParcel(Parcel in) {
            return new SMSInfo(in);
        }

        @Override
        public SMSInfo[] newArray(int size) {
            return new SMSInfo[size];
        }
    };
}