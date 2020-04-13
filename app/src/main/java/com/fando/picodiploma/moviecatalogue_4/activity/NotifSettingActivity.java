package com.fando.picodiploma.moviecatalogue_4.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.fando.picodiploma.moviecatalogue_4.R;
import com.fando.picodiploma.moviecatalogue_4.notification.DailyReminderReceiver;
import com.fando.picodiploma.moviecatalogue_4.notification.ReleaseTodayReminderReceiver;
import com.fando.picodiploma.moviecatalogue_4.notification.ReminderPreference;

import androidx.appcompat.app.AppCompatActivity;

public class NotifSettingActivity extends AppCompatActivity {

    private static final String DAILY_TYPE = "daily_type";
    private static final String DAILY_MESSAGE = "daily_reminder";
    private static final String RELEASE_TYPE = "release_type";
    private static final String RELEASE_MESSAGE = "release_message";
    private Switch swDailyReminder, swReleaseToday;
    private DailyReminderReceiver dailyReminderReceiver;
    private ReminderPreference reminderPreference;
    private ReleaseTodayReminderReceiver releaseTodayReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_setting);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.notification_settings);

        swDailyReminder = findViewById(R.id.sw_daily_reminder);
        swReleaseToday = findViewById(R.id.sw_release_today_reminder);

        dailyReminderReceiver = new DailyReminderReceiver();
        releaseTodayReceiver = new ReleaseTodayReminderReceiver();
        reminderPreference = new ReminderPreference(this);

        setSwitchDaily();
        setSwitchReleaseToday();

        setDailyReminder();
        setReleaseTodayReminder();

    }

    private void setDailyReminder() {
        swDailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (swDailyReminder.isChecked()) {
                    dailyReminderReceiver.setDailyAlarm(getApplicationContext(), DAILY_TYPE, DAILY_MESSAGE);
                    reminderPreference.setDailyReminder(true);
                    Toast.makeText(NotifSettingActivity.this, R.string.daily_reminder_on, Toast.LENGTH_SHORT).show();
                } else {
                    dailyReminderReceiver.cancelDailyReminder(getApplicationContext());
                    reminderPreference.setDailyReminder(false);
                    Toast.makeText(NotifSettingActivity.this, R.string.daily_reminder_off, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setReleaseTodayReminder() {
        swReleaseToday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (swReleaseToday.isChecked()) {
                    releaseTodayReceiver.setReleaseTodayAlarm(getApplicationContext(), RELEASE_TYPE, RELEASE_MESSAGE);
                    reminderPreference.setReleaseTodayReminder(true);
                    Toast.makeText(NotifSettingActivity.this, R.string.release_today_on, Toast.LENGTH_SHORT).show();
                } else {
                    releaseTodayReceiver.cancelReleaseTodayReminder(getApplicationContext());
                    reminderPreference.setReleaseTodayReminder(false);
                    Toast.makeText(NotifSettingActivity.this, R.string.release_today_off, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setSwitchDaily() {
        if (reminderPreference.getDailyReminder()) swDailyReminder.setChecked(true);
        else swDailyReminder.setChecked(false);
    }

    private void setSwitchReleaseToday() {
        if (reminderPreference.getReleaseTodayReminder()) swReleaseToday.setChecked(true);
        else swReleaseToday.setChecked(false);
    }
}
