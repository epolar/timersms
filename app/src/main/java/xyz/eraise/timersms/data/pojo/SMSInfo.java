package xyz.eraise.timersms.data.pojo;

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
public class SMSInfo {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    public long id;

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

}
