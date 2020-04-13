package com.fando.picodiploma.moviecatalogue_4.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.fando.picodiploma.moviecatalogue_4.BuildConfig;
import com.fando.picodiploma.moviecatalogue_4.R;
import com.fando.picodiploma.moviecatalogue_4.activity.MainActivity;
import com.fando.picodiploma.moviecatalogue_4.api.ApiService;
import com.fando.picodiploma.moviecatalogue_4.api.RetrofitBuilder;
import com.fando.picodiploma.moviecatalogue_4.model.MoviesItem;
import com.fando.picodiploma.moviecatalogue_4.model.MoviesResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReleaseTodayReminderReceiver extends BroadcastReceiver {
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";
    private static int NOTIFICATION_ID = 106;
    private String TAG = "releaseMovieTag";
    private ArrayList<MoviesItem> movieList = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        getReleaseMovieToday(context);
    }

    private void getReleaseMovieToday(Context context) {

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        ApiService api = RetrofitBuilder.getInstance().create(ApiService.class);
        Call<MoviesResponse> post = api.getReleaseMovie(BuildConfig.API_KEY, date, date);
        post.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                movieList = response.body().getResults();
                for (int i = 0; i < movieList.size(); i++) {
                    String title = movieList.get(i).getTitle();
                    // String message = movieList.get(i).getOverview();

                    showReleaseTodayNotification(context, title, NOTIFICATION_ID);
                    Log.d("releaseToday", title + " has released");

                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e(TAG, t.toString());
            }
        });
    }

    private void showReleaseTodayNotification(Context context, String title, int id) {
        String CHANNEL_ID = "channel_106";
        String CHANNEL_NAME = "movie_channel";

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        // intent.putExtra(EXTRA_MOVIE, movieList.get(0));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        int i;
        for (i = 0; i < movieList.size(); i++) {
            inboxStyle.addLine(movieList.get(i).getTitle());
        }
        inboxStyle.setSummaryText("and mores");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, title)
                .setSmallIcon(R.mipmap.ic_launcher_movie_foreground)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(i + " " + context.getString(R.string.released_movie_today))
                .setStyle(inboxStyle)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setVibrate(new long[]{500, 500, 500, 500, 500})
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
        }
        Objects.requireNonNull(notificationManager).notify(id, builder.build());
    }

    public void setReleaseTodayAlarm(Context context, String type, String message) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseTodayReminderReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

//        if (Calendar.getInstance().after(calendar)) {
//            calendar.add(Calendar.DATE, 1);
//        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

    }

    public void cancelReleaseTodayReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseTodayReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);
        Objects.requireNonNull(alarmManager).cancel(pendingIntent);
    }
}