package xyz.eraise.timersms.data.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;
import com.litesuits.orm.db.enums.Relation;

/**
 * 创建日期： 2016/4/15.
 * 短信接收方，记录了发送id和目标号码
 */
@JSONType(serialzeFeatures = SerializerFeature.BeanToArray, parseFeatures = Feature.SupportArrayToBean)
@Table("TaskInfo")
public class TaskInfo implements Parcelable {

    /**
     * 计划中未发送
     */
    public static final int STATE_SCHEDULE = 0;

    /**
     * 发送中
     */
    public static final int STATE_SENDING = 1;

    /**
     * 发送成功
     */
    public static final int STATE_SENT = 2;

    /**
     * 已经被接收
     */
    public static final int STATE_DELIVER = 3;

    /**
     * 发送失败
     */
    public static final int STATE_SEND_FAILURE = 4;

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    public int id;

    /**
     * 短信消息
     */
    @Mapping(Relation.ManyToOne)
    public SMSInfo sms;

    /**
     * 短信接收者的手机信息
     */
    public String phoneNumber;

    /**
     * <br/> {@link #STATE_SCHEDULE}
     * <br/> {@link #STATE_SENDING}
     * <br/> {@link #STATE_SENT}
     * <br/> {@link #STATE_DELIVER}
     * <br/> {@link #STATE_SEND_FAILURE}
     */
    public int state;

    public TaskInfo(){}

    public TaskInfo(SMSInfo sms, String phoneNumber) {
        this.sms = sms;
        this.phoneNumber = phoneNumber;
    }

    protected TaskInfo(Parcel in) {
        id = in.readInt();
        sms = (SMSInfo) in.readValue(SMSInfo.class.getClassLoader());
        phoneNumber = in.readString();
        state = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeValue(sms);
        dest.writeString(phoneNumber);
        dest.writeInt(state);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TaskInfo> CREATOR = new Parcelable.Creator<TaskInfo>() {
        @Override
        public TaskInfo createFromParcel(Parcel in) {
            return new TaskInfo(in);
        }

        @Override
        public TaskInfo[] newArray(int size) {
            return new TaskInfo[size];
        }
    };
}