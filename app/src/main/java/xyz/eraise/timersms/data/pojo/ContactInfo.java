package xyz.eraise.timersms.data.pojo;

/**
 * 创建日期： 2016/4/18.
 */
public class ContactInfo {

    public String contactName;
    public String phoneNumber;
    public boolean isSelected;

    public ContactInfo (String contactName, String phoneNumber) {
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
    }

}
