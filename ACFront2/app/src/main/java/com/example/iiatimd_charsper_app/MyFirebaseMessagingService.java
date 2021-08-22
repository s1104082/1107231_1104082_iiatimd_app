package com.example.animalcrossingfront;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.ContentValues.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
//    @Casper for Code info

    @Override
    public void onNewToken(String token){
        super.onNewToken(token);
        Log.d("taken", "onNewToken: " + token);

    }

    @Override
    public void onMessageReceived(RemoteMessage message){
        sendNotification(message.getData().get("leerdoel"));
    }

    private void sendNotification(String messageBody){
        Intent intent = new Intent(this, TrackerActivity.class); //locatie waar pushnotificatie naartoe wijst
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent.putExtra("leerdoel", messageBody);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_ONE_SHOT);

        String channelID = "1";
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelID)
                .setContentTitle("Reminder!") //titel wanneer app actief is
                .setContentText(messageBody) //message wanneer app actief is
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(channelID, "test notification", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);
        notificationManager.notify(0, notificationBuilder.build());
    }

}
