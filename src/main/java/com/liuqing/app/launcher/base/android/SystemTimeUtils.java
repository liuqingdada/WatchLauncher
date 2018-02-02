package com.liuqing.app.launcher.base.android;

import android.app.AlarmManager;
import android.content.Context;
import android.provider.Settings;
import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by liuqing
 * 2017/7/5.
 * Email: 1239604859@qq.com
 */

public class SystemTimeUtils {
    public static final String TIME_12 = "12";
    public static final String TIME_24 = "24";

    /**
     * 判断系统使用的是24小时制还是12小时制
     */
    public static boolean is24HourFormat(Context context) {
        return DateFormat.is24HourFormat(context);
    }

    /**
     * 设置系统的小时制
     */
    public static void setHourFormat(Context context, String hourFormat) {
        Settings.System.putString(context.getContentResolver(),
                                  Settings.System.TIME_12_24, hourFormat);
    }

    /**
     * 判断系统的时区是否是自动获取的
     */
    public static boolean isTimeZoneAuto(Context context) {
        try {
            return Settings.Global.getInt(context.getContentResolver(),
                                          Settings.Global.AUTO_TIME_ZONE) > 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置系统的时区是否自动获取
     */
    public static void setAutoTimeZone(Context context, int checked) {
        Settings.Global.putInt(context.getContentResolver(),
                               Settings.Global.AUTO_TIME_ZONE, checked);
    }


    /**
     * 判断系统的时间是否自动获取的
     */
    public static boolean isDateTimeAuto(Context context) {
        try {
            return Settings.Global.getInt(context.getContentResolver(),
                                          Settings.Global.AUTO_TIME) > 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置系统的时间是否需要自动获取
     */
    public static void setAutoDateTime(Context context, int checked) {
        Settings.Global.putInt(context.getContentResolver(),
                               Settings.Global.AUTO_TIME, checked);
    }

    /**
     * 设置系统日期
     */
    public static void setSysDate(Context context, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);

        long when = c.getTimeInMillis();

        if (when / 1000 < Integer.MAX_VALUE) {
            ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).setTime(when);
        }
    }

    /**
     * 设置系统时间
     */
    public static void setSysTime(Context context, int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        long when = c.getTimeInMillis();

        if (when / 1000 < Integer.MAX_VALUE) {
            ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).setTime(when);
        }
    }

    public static void setSysTime(Context context, long millis) {
        if (millis / 1000 < Integer.MAX_VALUE) {
            ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).setTime(millis);
        }
    }

    public static void setTimeZone(Context context, String timeZone) {
        ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).setTimeZone(timeZone);
    }

    /**
     * 设置系统时区
     */
    public static void setTimeZone(String timeZone) {
        final Calendar now = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone(timeZone);
        now.setTimeZone(tz);
    }

    /**
     * 获取系统当前的时区
     */
    public static String getDefaultTimeZone() {
        return TimeZone.getDefault()
                       .getDisplayName();
    }
}

/*
<timezones>
    <timezone id="Pacific/Majuro">马朱罗</timezone>
    <timezone id="Pacific/Midway">中途岛</timezone>
    <timezone id="Pacific/Honolulu">檀香山</timezone>
    <timezone id="America/Anchorage">安克雷奇</timezone>
    <timezone id="America/Los_Angeles">美国太平洋时间 (洛杉矶)</timezone>
    <timezone id="America/Tijuana">美国太平洋时间 (提华纳)</timezone>
    <timezone id="America/Phoenix">美国山区时间 (凤凰城)</timezone>
    <timezone id="America/Chihuahua">奇瓦瓦</timezone>
    <timezone id="America/Denver">美国山区时间 (丹佛)</timezone>
    <timezone id="America/Costa_Rica">美国中部时间 (哥斯达黎加)</timezone>
    <timezone id="America/Chicago">美国中部时间 (芝加哥)</timezone>
    <timezone id="America/Mexico_City">美国中部时间 (墨西哥城)</timezone>
    <timezone id="America/Regina">美国中部时间 (里贾纳)</timezone>
    <timezone id="America/Bogota">哥伦比亚时间 (波哥大)</timezone>
    <timezone id="America/New_York">美国东部时间 (纽约)</timezone>
    <timezone id="America/Caracas">委内瑞拉时间 (加拉加斯)</timezone>
    <timezone id="America/Barbados">大西洋时间 (巴巴多斯)</timezone>
    <timezone id="America/Manaus">亚马逊标准时间 (马瑙斯)</timezone>
    <timezone id="America/Santiago">圣地亚哥</timezone>
    <timezone id="America/St_Johns">纽芬兰时间 (圣约翰)</timezone>
    <timezone id="America/Sao_Paulo">圣保罗</timezone>
    <timezone id="America/Argentina/Buenos_Aires">布宜诺斯艾利斯</timezone>
    <timezone id="America/Godthab">戈特霍布</timezone>
    <timezone id="America/Montevideo">乌拉圭时间 (蒙得维的亚)</timezone>
    <timezone id="Atlantic/South_Georgia">南乔治亚</timezone>
    <timezone id="Atlantic/Azores">亚述尔群岛</timezone>
    <timezone id="Atlantic/Cape_Verde">佛得角</timezone>
    <timezone id="Africa/Casablanca">卡萨布兰卡</timezone>
    <timezone id="Europe/London">格林尼治标准时间 (伦敦)</timezone>
    <timezone id="Europe/Amsterdam">中欧标准时间 (阿姆斯特丹)</timezone>
    <timezone id="Europe/Belgrade">中欧标准时间 (贝尔格莱德)</timezone>
    <timezone id="Europe/Brussels">中欧标准时间 (布鲁塞尔)</timezone>
    <timezone id="Europe/Sarajevo">中欧标准时间 (萨拉热窝)</timezone>
    <timezone id="Africa/Windhoek">温得和克</timezone>
    <timezone id="Africa/Brazzaville">西部非洲标准时间 (布拉扎维)</timezone>
    <timezone id="Asia/Amman">东欧标准时间 (安曼)</timezone>
    <timezone id="Europe/Athens">东欧标准时间 (雅典)</timezone>
    <timezone id="Asia/Beirut">东欧标准时间 (贝鲁特)</timezone>
    <timezone id="Africa/Cairo">东欧标准时间 (开罗)</timezone>
    <timezone id="Europe/Helsinki">东欧标准时间 (赫尔辛基)</timezone>
    <timezone id="Asia/Jerusalem">以色列时间 (耶路撒冷)</timezone>
    <timezone id="Europe/Minsk">明斯克</timezone>
    <timezone id="Africa/Harare">中部非洲标准时间 (哈拉雷)</timezone>
    <timezone id="Asia/Baghdad">巴格达</timezone>
    <timezone id="Europe/Moscow">莫斯科</timezone>
    <timezone id="Asia/Kuwait">科威特</timezone>
    <timezone id="Africa/Nairobi">东部非洲标准时间 (内罗毕)</timezone>
    <timezone id="Asia/Tehran">伊朗标准时间 (德黑兰)</timezone>
    <timezone id="Asia/Baku">巴库</timezone>
    <timezone id="Asia/Tbilisi">第比利斯</timezone>
    <timezone id="Asia/Yerevan">埃里温</timezone>
    <timezone id="Asia/Dubai">迪拜</timezone>
    <timezone id="Asia/Kabul">阿富汗时间 (喀布尔)</timezone>
    <timezone id="Asia/Karachi">卡拉奇</timezone>
    <timezone id="Asia/Oral">乌拉尔</timezone>
    <timezone id="Asia/Yekaterinburg">叶卡捷林堡</timezone>
    <timezone id="Asia/Calcutta">加尔各答</timezone>
    <timezone id="Asia/Colombo">科伦坡</timezone>
    <timezone id="Asia/Katmandu">尼泊尔时间 (加德满都)</timezone>
    <timezone id="Asia/Almaty">阿拉木图</timezone>
    <timezone id="Asia/Rangoon">缅甸时间 (仰光)</timezone>
    <timezone id="Asia/Krasnoyarsk">克拉斯诺亚尔斯克</timezone>
    <timezone id="Asia/Bangkok">曼谷</timezone>
    <timezone id="Asia/Shanghai">中国标准时间 (北京)</timezone>
    <timezone id="Asia/Hong_Kong">香港时间 (香港)</timezone>
    <timezone id="Asia/Irkutsk">伊尔库茨克时间 (伊尔库茨克)</timezone>
    <timezone id="Asia/Kuala_Lumpur">吉隆坡</timezone>
    <timezone id="Australia/Perth">佩思</timezone>
    <timezone id="Asia/Taipei">台北时间 (台北)</timezone>
    <timezone id="Asia/Seoul">首尔</timezone>
    <timezone id="Asia/Tokyo">日本时间 (东京)</timezone>
    <timezone id="Asia/Yakutsk">雅库茨克时间 (雅库茨克)</timezone>
    <timezone id="Australia/Adelaide">阿德莱德</timezone>
    <timezone id="Australia/Darwin">达尔文</timezone>
    <timezone id="Australia/Brisbane">布里斯班</timezone>
    <timezone id="Australia/Hobart">霍巴特</timezone>
    <timezone id="Australia/Sydney">悉尼</timezone>
    <timezone id="Asia/Vladivostok">海参崴时间 (符拉迪沃斯托克)</timezone>
    <timezone id="Pacific/Guam">关岛</timezone>
    <timezone id="Asia/Magadan">马加丹时间 (马加丹)</timezone>
    <timezone id="Pacific/Auckland">奥克兰</timezone>
    <timezone id="Pacific/Fiji">斐济</timezone>
    <timezone id="Pacific/Tongatapu">东加塔布</timezone>
</timezones>
 */