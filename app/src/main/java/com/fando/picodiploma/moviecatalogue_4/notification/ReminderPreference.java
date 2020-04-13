package com.fando.picodiploma.moviecatalogue_4.notification;

import android.content.Context;
import android.content.SharedPreferences;

public class ReminderPreference {
    private static final String PREFERENCE = "reminder_preference";
    private static final String KEY_DAILY_REMINDER = "daily_reminder";
    private static final String KEY_RELEASE_TODAY_REMINDER = "release_today_reminder";
    private SharedPreferences sharePref;
    private SharedPreferences.Editor editor;

    public ReminderPreference(Context context) {
        sharePref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
    }

    public void setDailyReminder(boolean reminder) {
        editor = sharePref.edit();
        editor.putBoolean(KEY_DAILY_REMINDER, reminder);
        editor.apply();
    }

    public boolean getDailyReminder() {
        return sharePref.getBoolean(KEY_DAILY_REMINDER, false);
    }

    public void setReleaseTodayReminder(boolean reminder) {
        editor = sharePref.edit();
        editor.putBoolean(KEY_RELEASE_TODAY_REMINDER, reminder);
        editor.apply();
    }

    public boolean getReleaseTodayReminder() {
        return sharePref.getBoolean(KEY_RELEASE_TODAY_REMINDER, false);
    }
}
