package com.example.customnotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.target.NotificationTarget;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 123;
    //    private NotificationTarget notificationTarget;
    private String url = "https://www.w3.org/People/mimasa/test/imgformat/img/w3c_home.jpg";

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

    private void loadImageNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("123", "TOKEN", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        ImageView iv = findViewById(R.id.remoteview_notification_icon);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notif);
        remoteViews.setImageViewResource(R.id.remoteview_notification_icon, R.drawable.ic_launcher_background);
        remoteViews.setTextViewText(R.id.notification_name_group, "Headline");
        remoteViews.setTextViewText(R.id.notification_group_contact_name, "contact Name");
        remoteViews.setTextViewText(R.id.notification_group_message, "Ini pesannya ");
        NotificationCompat.InboxStyle notifStyle = new NotificationCompat.InboxStyle();
        String notifTitle = "TEsting";

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this, "123")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))
                .setCustomContentView(remoteViews)
                .setAutoCancel(true)
                .setColor(getResources().getColor(R.color.colorAccent));


        NotificationTarget notificationTarget = new NotificationTarget(
                this,
                R.id.remoteview_notification_icon,
                remoteViews,
                notifBuilder.build(),
                NOTIFICATION_ID);

        new Handler(Looper.getMainLooper()).post(() -> {
           Glide
                   .with(this)
                   .asBitmap()
                   .load(new GlideUrl(url))
                   .diskCacheStrategy(DiskCacheStrategy.ALL)
                   .into(notificationTarget);
        });
        notificationManager.notify(NOTIFICATION_ID, notifBuilder.build());


    }

    public int getImage(String imageName) {

        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());

        return drawableResourceId;
    }

}
