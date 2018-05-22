package com.flycode.healthbloom.services.stepCounter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.broadcastRecievers.PlayOrPauseReceiver;
import com.flycode.healthbloom.broadcastRecievers.ScreenLockReciever;
import com.flycode.healthbloom.data.models.Steps;
import com.flycode.healthbloom.data.network.googleApi.GoogleMapsService;
import com.flycode.healthbloom.trackers.DistanceTracker;
import com.flycode.healthbloom.trackers.DurationTracker;
import com.flycode.healthbloom.trackers.GpsTracker;
import com.flycode.healthbloom.trackers.StepTracker;
import com.flycode.healthbloom.trackers.TrackerComponentCollection;
import com.flycode.healthbloom.ui.base.BaseService;
import com.flycode.healthbloom.ui.exercise.exerciseEntry.ExerciseEntryActivity;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

import lombok.Getter;

import static android.support.v4.app.NotificationCompat.BADGE_ICON_SMALL;
import static android.support.v4.app.NotificationCompat.VISIBILITY_PUBLIC;

public class StepCounterService
        extends BaseService
        implements StepCounterContract.StepCounterService,
        StepTracker.StepListener,
        LocationListener,
        DistanceTracker.DistanceChangedListener,
        DurationTracker.OnDurationChangedListener,
        PlayOrPauseReceiver.OnPlayOrPauseListener{

    @Inject
    StepCounterPresenter<StepCounterContract.StepCounterService> presenter;
    @Inject
    TrackerComponentCollection components;
    @Inject
    GoogleMapsService googleMapsService;
    @Inject
    Steps steps;

    public static final String BROADCAST = "com.flycode.healthbloom.StepCounterService";
    public static final String PLAY_OR_PAUSE_BROADCAST = "com.flycode.healthbloom.StepCounterService.playOrPause";
    public static final int PLAY_OR_PAUSE_REQUEST_CODE = 1;

    private DistanceTracker distanceTracker;
    private StepTracker stepTracker;
    private GpsTracker gpsTracker;
    private DurationTracker durationTracker;

    private static final String TAG = StepCounterService.class.getSimpleName();
    private final IBinder mBinder = new StepCounterServiceBinder();
    private static StepCounterService instance = null;
    private StepCounterListener listener;
    private RemoteViews remoteViews;
    @Getter private boolean isPlaying;
    private NotificationManager notificationManager;
    private Notification notification;
    private int stepsTaken;
    private LatLng originLocation;

    private PlayOrPauseReceiver playOrPauseReceiver;
    private ScreenLockReciever screenLockReciever;


    /*############################### SERVICE CALLBACKS ################################*/

    public static boolean isInstanceCreated(){
        return instance != null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //SETUP THE TRACKERS
        distanceTracker = (DistanceTracker) components.addComponent(new DistanceTracker(this,googleMapsService));
        stepTracker = (StepTracker) components.addComponent(new StepTracker(this));
        gpsTracker = (GpsTracker) components.addComponent(new GpsTracker(this));
        durationTracker = (DurationTracker) components.addComponent(new DurationTracker(this));

        //INIT TRACKERS
        components.onInit();
        originLocation = mapLocation(gpsTracker.getLocation());
        if (originLocation != null){
            distanceTracker.setOrigin(originLocation);
        }
        
        //NOTIFICATION MANAGER
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        //NO SLEEP
        wakeLock(true);

        //INIT PLAY OR PAUSE RECEIVER
        playOrPauseReceiver = new PlayOrPauseReceiver();
        playOrPauseReceiver.setListener(this);
        registerReceiver(playOrPauseReceiver,new IntentFilter(StepCounterService.PLAY_OR_PAUSE_BROADCAST));

        //ON SCREEN LOCK RECEIVER
        screenLockReciever = new ScreenLockReciever();
        screenLockReciever.setListener(stepTracker);
        registerReceiver(screenLockReciever,new IntentFilter(Intent.ACTION_SCREEN_OFF));

        //ON START BROADCAST
        sendBroadcast(new Intent(BROADCAST));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)  {
        showNotification();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        wakeLock(false);

        notificationManager.cancel(R.string.step_counter_notification_id);
        unregisterReceiver(playOrPauseReceiver);
        unregisterReceiver(screenLockReciever);

        presenter.onDetach();

        listener = null;
        instance = null;
    }

    /*############################### SERVICE LOGIC ################################*/

    /**
     * Register the UI Binding to the service in order
     * for the service to update the UI automatically.
     *
     * @param listener - StepCounterListener activity that will be receiving ui update instructions
     *                from this service.
     * */
    public void onRegisterUIBinding(StepCounterListener listener){
        this.listener = listener;
        setServiceEventReceiver(listener);

        //Update listener
        if (isPlaying){
            listener.updateDuration(durationTracker.getDurationInString());
            listener.updateSteps(steps);
            listener.play();
        }
        else{
            listener.updateDuration(durationTracker.getDurationInString());
            listener.updateSteps(steps);
            listener.pause();
        }
    }

    /**
     * If the counter was not playing (!isPlaying returns true) then
     * play the counter. And change the ui of attachec listener and
     * remote views.
     * */
    public void play(){
        isPlaying = true;
        components.onPlay();
        if (listener != null){
            listener.play();
            listener.updateSteps(steps);
        }
        if(remoteViews != null){
            remoteViews.setTextViewText(R.id.tv_steps,String.valueOf(stepsTaken));
            remoteViews.setImageViewResource(R.id.play_or_pause_btn,R.drawable.ic_pause_circle_filled_green);
            notificationManager.notify(R.string.step_counter_notification_id, notification);
        }

    }

    /**
     * If the counter was playing (!isPlaying returns false) then
     * pause the counter. And change the ui of attached listeners and
     * remote views.
     * */
    public void pause(){
        isPlaying = false;
        components.onPause();
        if (listener != null){
            listener.pause();
            listener.updateSteps(steps);
        }
        if(remoteViews != null){
            remoteViews.setTextViewText(R.id.tv_steps,String.valueOf(stepsTaken));
            remoteViews.setImageViewResource(R.id.play_or_pause_btn,R.drawable.ic_play_circle_filled_green);
            notificationManager.notify(R.string.step_counter_notification_id, notification);
        }
    }

    /**
     * Change the state of the service. If playing then pause it if paused then play it.
     * */
    public void onPlayOrPause(){
        if (isPlaying) pause();
        else play();
    }

    /**
     * Save the current counted steps into the database
     *
     * */
    public void onFinish(){
        components.onFinish();
        //TODO:forced
        presenter.save(this);
    }

    /**
     * Show a notification while this service is running.
     */
    private void showNotification() {

        // The PendingIntent to launch  activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(
                this,
                0,
                new Intent(this, ExerciseEntryActivity.class),
                0
        );
        PendingIntent playOrPause = PendingIntent.getBroadcast(
                this,
                PLAY_OR_PAUSE_REQUEST_CODE,
                new Intent(PLAY_OR_PAUSE_BROADCAST),
                0
        );

        remoteViews = new RemoteViews(getPackageName(),R.layout.step_counter_notification_view);
        remoteViews.setOnClickPendingIntent(R.id.play_or_pause_btn,playOrPause);

        // Set the info for the views that show in the notification panel.
        notification = new NotificationCompat.Builder(this,getString(R.string.step_counter_notification_channel))
                .setContentIntent(contentIntent)  // The intent to send when the entry is clicked
                .setTicker(getString(R.string.step_counter_notification_hint))
                .setCustomContentView(remoteViews)
                .setSmallIcon(R.drawable.app_logo)
                .setBadgeIconType(BADGE_ICON_SMALL)
                .setAutoCancel(false)
                .setOngoing(true)
                .setVisibility(VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        startForeground(R.string.step_counter_notification_id, notification);

        // Send the notification.
        // We use a string id because it is a unique number.  We use it later to cancel.
//        notificationManager.notify(R.string.step_counter_notification_id, notification);
    }

    /**
     * Map a Location object into a LatLng object
     * */
    private LatLng mapLocation(Location location){
        if (location != null)
            return new LatLng(location.getLatitude(),location.getLongitude());
        else return null;
    }

    /*############################### STEP_LISTENER EVENTS ################################*/

    @Override
    public void step(long timeNs) {
        steps.Steps.set(++stepsTaken);
        if (listener != null)
            listener.updateSteps(steps);
        if(remoteViews != null){
            remoteViews.setTextViewText(R.id.tv_steps,String.valueOf(stepsTaken));
            notificationManager.notify(R.string.step_counter_notification_id, notification);
        }

        //After every 30 steps get location and calculate distance
        if(stepsTaken%30 == 0){
            if (mapLocation(gpsTracker.getLocation()) != null)
                distanceTracker.getDistance(mapLocation(gpsTracker.getLocation()));
        }
    }

    /*############################### DISTANCE LISTENER ################################*/

    @Override
    public void onDistanceChanged(int distance, List<LatLng> polyline) {
        //Calculate calories
        presenter.calculateCalories(distance);

        //Update UI
        if (listener != null){
            listener.updateMap(polyline);
            listener.updateSteps(steps);
        }
    }

    /*############################### LOCATION LISTENER ################################*/

    @Override
    public void onLocationChanged(Location location) {
        if (originLocation == null){
            distanceTracker.setOrigin(mapLocation(location));
        }else
        //GET DISTANCE
        distanceTracker.onLocationChange(mapLocation(location));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /*############################### Duration LISTENER ################################*/

    @Override
    public void onDurationChanged(String duration) {
        if (listener != null){
            listener.updateDuration(duration);
        }

        if(remoteViews != null){
            remoteViews.setTextViewText(R.id.tv_duration,duration);
            notificationManager.notify(R.string.step_counter_notification_id, notification);
        }
    }

    /*############################### BINDING ################################*/

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        listener = null;
        return super.onUnbind(intent);
    }

    public class StepCounterServiceBinder extends Binder {
        public StepCounterService getService(){
            return StepCounterService.this;
        }
    }

    public interface StepCounterListener extends ServiceEventReceiver{
        void updateSteps(Steps steps);
        void updateMap(List<LatLng> polyline);
        void updateDuration(String duration);
        void play();
        void pause();
    }
}