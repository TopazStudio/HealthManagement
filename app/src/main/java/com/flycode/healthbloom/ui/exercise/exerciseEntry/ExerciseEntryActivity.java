package com.flycode.healthbloom.ui.exercise.exerciseEntry;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.broadcastRecievers.ServiceStartedReceiver;
import com.flycode.healthbloom.data.models.Steps;
import com.flycode.healthbloom.databinding.ExerciseEntryBinding;
import com.flycode.healthbloom.services.stepCounter.StepCounterService;
import com.flycode.healthbloom.ui.base.BaseViewWithNav;
import com.flycode.healthbloom.ui.exercise.exerciseOverview.ExerciseOverviewActivity;
import com.flycode.healthbloom.ui.exercise.exerciseView.ExerciseViewActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import java.util.Objects;

public class ExerciseEntryActivity
        extends BaseViewWithNav
        implements OnMapReadyCallback,StepCounterService.StepCounterListener {

    private static final int PERMISSION_REQUEST_CODE = 0;
    private StepCounterService stepCounterService;
    private ServiceConnection stepCounterServiceConnection;
    private ExerciseEntryBinding binding;
    private ServiceStartedReceiver serviceStartedReceiver;
    private boolean permissionsGranted = false;
    private boolean isWaitingForBind = false;
    private boolean isBound = false;
    private GoogleMap mMap;
    private Polyline line;

    /*############################### ACTIVITY CALLBACKS ################################*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //BIND CONTENT
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exercise_entry);

        //TOOLBAR
        setSupportActionBar((Toolbar) binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // GOOGLE MAPS
        checkLocationPermissions();
        isGooglePlayServicesAvailable();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //INIT
        init();
    }

    @Override
    protected void onStart(){
        super.onStart();
        registerReceiver(serviceStartedReceiver,new IntentFilter(StepCounterService.BROADCAST));
    }

    @Override
    protected void onResume() {
        super.onResume();
        syncStepCounterService();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                permissionsGranted  = true;
            }
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (permissionsGranted){
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finishAndGoTo(ExerciseOverviewActivity.class);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unSyncStepCounterService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(serviceStartedReceiver);
    }

    /*############################### MAIN INIT ################################*/

    private void init() {
        serviceStartedReceiver = new ServiceStartedReceiver();
        serviceStartedReceiver.setListener(new ServiceStartedReceiver.OnServiceStartedListener() {
            @Override
            public void onServiceStarted() {
                syncStepCounterService();
            }
        });

        stepCounterServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                stepCounterService = ((StepCounterService.StepCounterServiceBinder) service).getService();
                stepCounterService.onRegisterUIBinding(ExerciseEntryActivity.this);

                isBound = true;

                if (isWaitingForBind) {
                    onPauseOrPlay(null);
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
//                pause();
            }
        };
    }

    /*############################### ACTIVITY LOGIC ################################*/

    /**
     * Checks if the StepCounterService is running and binds to the service.
     *
     * */
    private void syncStepCounterService() {
        if(StepCounterService.isInstanceCreated()) /*SERVICE IS RUNNING*/
            bindService(
                new Intent(this, StepCounterService.class),
                stepCounterServiceConnection,
                0
            );
    }

    /**
     * Unbinds the service bound to the activity
     *
     * */
    private void unSyncStepCounterService() {
        if (isBound)
            unbindService(stepCounterServiceConnection);
        stepCounterService = null;
    }

    /**
     * Start the step counter service
     *
     * */
    private boolean startStepCounterService() {

        return startService(new Intent(ExerciseEntryActivity.this, StepCounterService.class)) != null;
    }

    /**
     * Check if the application has been granted access to the camera.
     * If not hide the progress image card view and try requesting for it.
     *
     * */
    private void checkLocationPermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET},
                    PERMISSION_REQUEST_CODE);
        }else permissionsGranted = true;
    }

    /**
     * Checking if Google Play Services Available or not
     * */
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }


    /*############################### UI CALLBACKS ################################*/

    /**
     * Save the steps being tracked
     * */
    public void onFinish(View view){
        stepCounterService.onFinish();
    }

    /**
     * Plays or pauses the counter according to its previous state
     *
     * @param view - view that registered this function at its onClick attribute
     * */
    public void onPauseOrPlay(View view) {
        if (!isBound) {
            if (startStepCounterService()) { //If binding is possible
                //Wait for service connection.
                isWaitingForBind = true;
                binding.bindingProgressBar.setVisibility(View.VISIBLE);
                binding.playOrPauseBtn.setVisibility(View.GONE);
            }
            else showError("Sorry, Something went wrong. Please try again.");
        } else {
            isWaitingForBind = false;
            binding.bindingProgressBar.setVisibility(View.GONE);
            binding.playOrPauseBtn.setVisibility(View.VISIBLE);

            stepCounterService.onPlayOrPause();
        }
    }

    /*############################### SERVICE EVENTS ################################*/

    /**
     * If the counter was not playing (!isPlaying returns true) then
     * play the counter.
     *
     *
     * */
    @Override
    public void play() {
        binding.playOrPauseBtn.setImageResource(R.drawable.ic_pause_circle_filled);
        binding.finishBtnFrame.setVisibility(View.GONE);
    }

    /**
     * If the counter was playing (!isPlaying returns false) then
     * pause the counter.
     *
     *
     * */
    @Override
    public void pause() {
        binding.playOrPauseBtn.setImageResource(R.drawable.ic_play_circle_outline);
        binding.finishBtnFrame.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String error) {
        showError(error);
    }

    @Override
    public void onSuccess(String message) {
        showMessage(message);
    }

    @Override
    public void onFinish(boolean success,Bundle data) {
        if (success){
            openForResult(ExerciseViewActivity.class,
                    ExerciseOverviewActivity.VIEW_STEPS_REQUEST_CODE,data);
        }
    }

    @Override
    public void updateMap(List<LatLng> polyline) {
        //Remove previous line from map
        if (line != null) {
            line.remove();
        }
        // This loop will go through all the results and add marker on each location.

        line = mMap.addPolyline(new PolylineOptions()
                .addAll(polyline)
                .width(20)
                .color(Color.RED)
                .geodesic(true)
        );
    }

    @Override
    public void updateDuration(String duration){
        binding.tvDuration.setText(duration);
    }

    @Override
    public void updateSteps(Steps steps){
        binding.tvSteps.setText(String.valueOf(steps.Steps.get()));
        binding.tvDistance.setText(String.valueOf(steps.Distance.get()));
        binding.tvDistanceUnits.setText(steps.DistanceUnits.get());
        binding.tvCalories.setText(String.valueOf(steps.Calories.get()));
        binding.tvCaloriesUnits.setText(steps.CalorieUnits.get());
    }
}
