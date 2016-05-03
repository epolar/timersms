package xyz.eraise.timersms.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

import xyz.eraise.timersms.data.pojo.SMSInfo;
import xyz.eraise.timersms.services.SendSMSService;

/**
 * 创建日期： 2016/5/3.
 * 发送短信用的闹钟相关
 */
public class SMSAlarmUtils {

    /**
     * @param info 设置定时闹钟， {@link SMSInfo#id} 做为 PendingIntent 的id
     */
    public static final void scheduleSMSAlarm(@NonNull Context context, @NonNull  SMSInfo info) {
        Intent intent = new Intent(context, SendSMSService.class);
        intent.setAction(SendSMSService.ACTION_SEND_SMS);
        intent.putExtra(SendSMSService.EXTRA_TASK_Id, info.id);
        PendingIntent pi = PendingIntent.getService(context, info.id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT < 19) {
            am.set(AlarmManager.RTC_WAKEUP, info.scheduleTime, pi);
        } else {
            am.setExact(AlarmManager.RTC_WAKEUP, info.scheduleTime, pi);
        }
    }

    /**
     * 取消定时闹钟
     * @param context
     * @param info
     */
    public static final void cancelScheduleSMS(@NonNull Context context, @NonNull SMSInfo info) {
        Intent intent = new Intent(context, SendSMSService.class);
        PendingIntent pi = PendingIntent.getService(context, info.id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pi);
    }

}
