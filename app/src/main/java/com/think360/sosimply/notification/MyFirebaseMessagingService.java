package com.think360.sosimply.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.think360.sosimply.AppController;
import com.think360.sosimply.R;
import com.think360.sosimply.ui.activities.HomeActivity;

import java.util.Map;

/**
 * Created by Sukhjot on 6/14/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "FROM:" + remoteMessage.getFrom());

        //Check if the message contains data
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data: " + remoteMessage.getData());
            Map data = remoteMessage.getData();
            Object project = ((ArrayMap) data).valueAt(0);
            sendNotification(project.toString());
        }

        //Check if the message contains com.think360.cmg.notification
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Mesage body:" + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }
    }

    /**
     * Dispay the com.think360.cmg.notification
     *
     * @param body
     */
    private void sendNotification(String body) {
        Intent intent = new Intent(this, HomeActivity.class);
        if (body.contains("Sos")) {
            intent.putExtra("TO_OPEN", R.id.tab_friends1);
        }
        if (body.contains("availability")) {
            intent.putExtra("TO_OPEN", R.id.tab_favorites);
        }


        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0/*Request code*/, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //Set sound of com.think360.cmg.notification
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notifiBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("SoSimpply")
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);

        ((AppController) getApplication()).bus().send(new EventNotification(body));

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /*ID of com.think360.cmg.notification*/, notifiBuilder.build());
    }
}
