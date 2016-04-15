package xyz.eraise.timersms.data.pojo;

import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;
import com.litesuits.orm.db.enums.Relation;

/**
 * 创建日期： 2016/4/15.
 * 短信接收方，记录了发送id和目标号码
 */
@Table("TaskInfo")
public class TaskInfo {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    public long id;

    /**
     * 短信消息
     */
    @Mapping(Relation.ManyToOne)
    public TaskInfo sms;

    /**
     * 短信接收者的手机信息
     */
    public String phoneNumber;

    /**
     * {@code true} 表示发送成功
     */
    public boolean isSended;

}
