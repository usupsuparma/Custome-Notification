package com.example.customnotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.NotificationTarget;
import com.bumptech.glide.request.target.Target;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 123;
    //    private NotificationTarget notificationTarget;
    private String url = "https://www.w3.org/People/mimasa/loadImage/imgformat/img/w3c_home.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage();
            }
        });
    }
    
    public int getImage(String imageName) {
        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());
        return drawableResourceId;
    }
    
    void loadImage(){
	    final RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.notif);
	
	    remoteViews.setImageViewResource(R.id.remoteview_notification_icon, R.mipmap.ic_launcher);
	
	    remoteViews.setTextViewText(R.id.notification_name_group, "Headline");
	    remoteViews.setTextViewText(R.id.notification_group_message, "Short Message");
	
	    remoteViews.setTextColor(R.id.notification_name_group, this.getResources().getColor( android.R.color.black));
	    remoteViews.setTextColor(R.id.notification_group_message, this.getResources().getColor(android.R.color.black));
	
	    // build notification
	    NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this, "123")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))
                .setCustomContentView(remoteViews)
                .setAutoCancel(true)
                .setColor(getResources().getColor(R.color.colorAccent));
	
	    final Notification notification = notifBuilder.build();
	
	    // set big content view for newer androids
	    if (android.os.Build.VERSION.SDK_INT >= 16) {
		    notification.bigContentView = remoteViews;
	    }
	
	    NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
	    mNotificationManager.notify(NOTIFICATION_ID, notification);
	
	    NotificationTarget notificationTarget;
	    notificationTarget = new NotificationTarget(
			    this,
			    R.id.remoteview_notification_icon,
			    remoteViews,
			    notification,
			    NOTIFICATION_ID);
	
	    String url ="https://i3.wp.com/any.web.id/wp-content/uploads/2017/05/Pegunungan.jpg";
	    Glide
			    .with( this.getApplicationContext() ) // safer!
			    .asBitmap()
			    .load(url)
			    .listener(new RequestListener<Bitmap>() {
				    @Override
				    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
					    Log.d("TAG", "onLoadFailed: gagal load");
					    return false;
				    }
				
				    @Override
				    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
					    Log.d("TAG", "onResourceReady: sukses load");
					    return false;
				    }
			    })
			    .into( notificationTarget );
    }

}
