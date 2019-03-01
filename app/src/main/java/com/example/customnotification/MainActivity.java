package com.example.customnotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.NotificationTarget;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 123;
    private NotificationTarget notificationTarget;
    private String  url = "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjztOOezODgAhUoheAKHbjdCQQQjRx6BAgBEAU&url=%2Furl%3Fsa%3Di%26source%3Dimages%26cd%3D%26ved%3D%26url%3Dhttps%253A%252F%252Fwww.marca.com%252Fen%252Ffootball%252Fbarcelona%252F2019%252F02%252F25%252F5c7450f8268e3e847e8b4644.html%26psig%3DAOvVaw19qZuE6jxLamcrJm6VQ6MZ%26ust%3D1551517770331500&psig=AOvVaw19qZuE6jxLamcrJm6VQ6MZ&ust=1551517770331500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImageNotification();
            }
        });
    }

    private void notif(){
// create RemoteViews

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("123", "TOKEN", NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
        }
        final RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.notif);

        remoteViews.setImageViewResource(R.id.remoteview_notification_icon, R.mipmap.ic_launcher);

        remoteViews.setTextViewText(R.id.notification_name_group, "TESt kirim");
        remoteViews.setTextViewText(R.id.notification_group_message, "Short Messagesdfdf");

        remoteViews.setTextColor(R.id.notification_name_group, getResources().getColor( android.R.color.black));
        remoteViews.setTextColor(R.id.notification_name_group, getResources().getColor(android.R.color.black));

        // build notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Content Title")
                        .setContentText("Content Text")
                        .setContent(remoteViews)
                        .setPriority( NotificationCompat.PRIORITY_MIN);

        final Notification notification = mBuilder.build();

        // set big content view for newer androids
        notification.bigContentView = remoteViews;
        mNotificationManager.notify(NOTIFICATION_ID, notification);

        notificationTarget = new NotificationTarget(
                this,
                R.id.remoteview_notification_icon,
                remoteViews,
                notification,
                NOTIFICATION_ID);

//        Glide
//                .with( getApplicationContext() ) // safer!
//                .load( eatFoodyImages[3] )
//                .asBitmap()
//                .into( notificationTarget );
    }

    private void loadImageNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("123", "TOKEN", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notif);
        remoteViews.setImageViewResource(R.id.remoteview_notification_icon, R.drawable.ic_launcher_background);
        remoteViews.setTextViewText(R.id.notification_name_group, "Headline");
        remoteViews.setTextViewText(R.id.notification_group_contact_name, "contact Name");
        remoteViews.setTextViewText(R.id.notification_group_message, "Ini pesannya ");

        Notification notification = new NotificationCompat.Builder(this, "123")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setContent(remoteViews)
                .setCustomContentView(remoteViews).build();

        notification.bigContentView = remoteViews;

        notificationTarget = new NotificationTarget(
                this,
                R.id.remoteview_notification_icon,
                remoteViews,
                notification,
                NOTIFICATION_ID);

        new Handler(Looper.getMainLooper()).post(() -> {
            Glide
                    .with(this.getApplicationContext()) // safer!
                    .asBitmap()
                    .load(url)
                    .into(notificationTarget);
        });

        notificationManager.notify(NOTIFICATION_ID, notification);

    }
}
