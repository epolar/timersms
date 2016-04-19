package xyz.eraise.timersms.data.pojo;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import xyz.eraise.timersms.utils.ELog;

/**
 * 创建日期： 2016/4/18.
 */
public class ContactInfo implements Parcelable {

    public String contactName;
    public String phoneNumber;
    public Uri photo;
    public boolean isSelected;

    public ContactInfo (String contactName, String phoneNumber, String photo) {
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
        if (null != photo) {
            try {
                this.photo = Uri.parse(photo);
            } catch (Exception e) {
                new ELog("ContactInfo").e("construction", e);
            }
        }
    }


    protected ContactInfo(Parcel in) {
        contactName = in.readString();
        phoneNumber = in.readString();
        if (in.readByte() == (byte)0x01) {
            photo = Uri.parse(in.readString());
        }
        isSelected = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(contactName);
        dest.writeString(phoneNumber);
        if (null == photo) {
            dest.writeByte((byte)0x00);
        } else {
            dest.writeByte((byte)0x01);
            dest.writeString(photo.toString());
        }
        dest.writeByte((byte) (isSelected ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ContactInfo> CREATOR = new Parcelable.Creator<ContactInfo>() {
        @Override
        public ContactInfo createFromParcel(Parcel in) {
            return new ContactInfo(in);
        }

        @Override
        public ContactInfo[] newArray(int size) {
            return new ContactInfo[size];
        }
    };
}