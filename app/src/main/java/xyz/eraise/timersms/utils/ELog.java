package xyz.eraise.timersms.utils;

import android.util.Log;

/**
 * 创建日期： 2016/4/15.
 * 日志打印控制
 */
public class ELog {

    String tag;

    public ELog(String tag) {
        this.tag = tag;
    }

    public void v(String msg) {
        Log.v(tag, msg);
    }

    public void v(String msg, Throwable tr) {
        Log.v(tag, msg, tr);
    }

    public void d(String msg) {
        Log.d(tag, msg);
    }

    public void d(String msg, Throwable tr) {
        Log.d(tag, msg, tr);
    }

    public void i(String msg) {
        Log.i(tag, msg);
    }

    public void i(String msg, Throwable tr) {
        Log.i(tag, msg, tr);
    }

    public void w(String msg) {
        Log.w(tag, msg);
    }

    public void w(String msg, Throwable tr) {
        Log.w(tag, msg, tr);
    }

    public void w(Throwable tr) {
        Log.w(tag, tr);
    }

    public void e(String msg) {
        Log.e(tag, msg);
    }

    public void e(String msg, Throwable tr) {
        Log.e(tag, msg, tr);
    }

}
