package com.flycode.healthbloom.ui.weight.weightEntry;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.flycode.healthbloom.R;
import com.flycode.healthbloom.data.models.Note;
import com.flycode.healthbloom.data.models.Tag;
import com.flycode.healthbloom.data.models.User;
import com.flycode.healthbloom.data.models.WeightMeasurement;
import com.flycode.healthbloom.databinding.CustomHeightDialogBinding;
import com.flycode.healthbloom.databinding.WeightEntryBinding;
import com.flycode.healthbloom.ui.base.BaseView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

//TODO: catch the back button and warn the user of losing data.
public class WeightEntryActivity
        extends BaseView
        implements WeightEntryContract.WeightEntryView  {

    @Inject
    WeightEntryContract.WeightEntryPresenter<WeightEntryContract.WeightEntryView> presenter;
    @Inject
    WeightMeasurement weightMeasurement;
    @Inject
    List<Tag> tags;
    @Inject
    Note note;
    @Inject
    Calendar entryCalendar;
    @Inject
    @Named("default_user")
    User user;

    private static final String TAG = WeightEntryActivity.class.getSimpleName();

    private static final int SELECT_PHOTO = 1;
    private static final int CAPTURE_PHOTO = 2;
    private static final int PERMISSION_REQUEST_CODE = 3;
    public static final String UPDATE_WEIGHT_MEASUREMENT = "w";
    private int[] weightLimits;
    private MaterialDialog customHeightDialog;

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;

    private WeightEntryBinding binding;
    private CustomHeightDialogBinding customHeightDialogBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //BIND CONTENT
        binding = DataBindingUtil.setContentView(this,R.layout.activity_weight_entry);
        binding.setWeightMeasurement(weightMeasurement);
        binding.setNote(note);

        //PRESENTER
        presenter.onAttach(this);

        //TOOLBAR
        setSupportActionBar((Toolbar) binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //INIT
        init();
    }

    /**
     * Initialize Activity
     * */
    private void init(){
        //FETCH DATA
        presenter.fetchTags();

        //Determine if its an update
        Intent i = getIntent();
        if(i.hasExtra(UPDATE_WEIGHT_MEASUREMENT)){
            mapWeightMeasurement(new Gson()
                    .fromJson(i.getStringExtra(UPDATE_WEIGHT_MEASUREMENT),WeightMeasurement.class));
            Picasso.get()
                    .load(weightMeasurement.PhotoLocation.get())
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder) //TODO: find error placeholder image
                    .into(binding.progressPhoto);
            mapNote(weightMeasurement.note);
            entryCalendar.setTime(weightMeasurement.Date.get());
        }

        //CONFIGURE UI
        setTime();
        setDate();
        setOnDateSetListener();
        setOnTimeSetListener();
        setupHeightDialog();
        setUpWeightUnitsSpinner();
        setUpHeightUnitsSpinner();
        binding.progressPhotoProgressBar.setProgress(0);
        binding.progressPhotoProgressBar.setMax(100);
    }

    private void mapWeightMeasurement(WeightMeasurement weightMeasurement){
        this.weightMeasurement.id = weightMeasurement.id;
        this.weightMeasurement.Height.set(weightMeasurement.Height.get());
        this.weightMeasurement.HeightUnits.set(weightMeasurement.HeightUnits.get());

        this.weightMeasurement.Weight.set(weightMeasurement.Weight.get());
        this.weightMeasurement.WeightUnits.set(weightMeasurement.WeightUnits.get());
//        binding.weightUnitsPicker.setSelection();

        this.weightMeasurement.BMI.set(weightMeasurement.BMI.get());
        this.weightMeasurement.Date.set(weightMeasurement.Date.get());
        this.weightMeasurement.PhotoLocation.set(weightMeasurement.PhotoLocation.get());
        this.weightMeasurement.note = weightMeasurement.note;
    }

    private void mapNote(Note note){
        this.note.id = note.id;
        this.note.Content.set(note.Content.get());
    }

    private void setupHeightDialog(){
        customHeightDialogBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.height_input_dialog  ,null,false);
        customHeightDialogBinding.setUser(user);

        customHeightDialog = new MaterialDialog.Builder(this)
                .customView(customHeightDialogBinding.getRoot(),true)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        weightMeasurement.Height.set(customHeightDialogBinding.getUser().InitHeight.get());
                        weightMeasurement.HeightUnits.set(customHeightDialogBinding.getUser().InitHeightUnits.get());
                    }
                })
                .build();
    }

    public void showHeightDialog(View view){
        customHeightDialog.show();
    }

    /**
     * Sets the TextView displaying the time with the current time
     * held by the Activity's Calendar instance
     *
     * */
    private void setTime() {
        binding.tvTime.setText(
                new SimpleDateFormat("HH:mm", Locale.US)
                        .format(entryCalendar.getTime())
        );
    }

    /**
     * Sets the TextView displaying the date with the current date
     * held by the Activity's Calendar instance
     *
     * */
    private void setDate(){
        binding.tvDate.setText(
                new SimpleDateFormat("dd-M-yyyy", Locale.US)
                        .format(entryCalendar.getTime())
        );
    }

    @SuppressWarnings("unchecked")
    private void setUpWeightUnitsSpinner(){
        List<String> data = Arrays.asList(getResources().getStringArray(R.array.weight_units));
        weightLimits = getResources().getIntArray(R.array.weight_units_limits);

        binding.weightUnitsPicker.setWheelAdapter(new ArrayWheelAdapter(this)); // text data source
        binding.weightUnitsPicker.setSkin(WheelView.Skin.None);
        binding.weightUnitsPicker.setLoop(false);
        binding.weightUnitsPicker.setWheelSize(data.size());
        binding.weightUnitsPicker.setSelection(1);
        binding.weightUnitsPicker.setWheelData(data);

        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextSize = 20;
        style.textSize = 12;
        style.selectedTextColor = Color.WHITE;
        style.textColor = Color.LTGRAY;
        style.holoBorderColor = Color.WHITE;
        style.backgroundColor = Color.TRANSPARENT;
        binding.weightUnitsPicker.setStyle(style);

        binding.weightUnitsPicker.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                weightMeasurement.WeightUnits.set((String) o);
                binding.weightScalePicker.setMaximumAcceptedSize(weightLimits[position]);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void setUpHeightUnitsSpinner(){
        List<String> data = Arrays.asList(getResources().getStringArray(R.array.height_units));
        final int[] heightLimits = getResources().getIntArray(R.array.height_units_limits);

        customHeightDialogBinding.heightUnitsPicker.setWheelAdapter(new ArrayWheelAdapter(this)); // text data source
        customHeightDialogBinding.heightUnitsPicker.setSkin(WheelView.Skin.None);
        customHeightDialogBinding.heightUnitsPicker.setLoop(false);
        customHeightDialogBinding.heightUnitsPicker.setWheelSize(data.size());
        customHeightDialogBinding.heightUnitsPicker.setSelection(0);
        customHeightDialogBinding.heightUnitsPicker.setWheelData(data);

        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextSize = 20;
        style.textSize = 12;
        style.selectedTextColor = Color.WHITE;
        style.textColor = Color.LTGRAY;
        style.backgroundColor = Color.TRANSPARENT;
        customHeightDialogBinding.heightUnitsPicker.setStyle(style);

        customHeightDialogBinding.heightUnitsPicker.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                user.InitHeightUnits.set((String) o);
                customHeightDialogBinding.heightScalePicker.setMaximumAcceptedSize(heightLimits[position]);

            }
        });
    }

    /**
     * Set the activity's OnDateSetListener
     * */
    private void setOnDateSetListener() {
         dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                //Store the picked date globally for next time when user wants to pick again
                entryCalendar.set(Calendar.YEAR, year);
                entryCalendar.set(Calendar.MONTH, monthOfYear);
                entryCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setDate();
            }

        };
    }

    /**
     * Set the activity's OnDateSetListener
     * */
    private void setOnTimeSetListener() {
        timeSetListener = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Store the picked time globally for next time when user wants to pick again
                entryCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                entryCalendar.set(Calendar.MINUTE, minute);
                setTime();
            }
        };
    }

    /**
     * Check if the application has been granted access to the camera.
     * If not hide the progress image card view and try requesting for it.
     *
     * */
    private void checkImagePermissions(){
        if(ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            binding.progressPhotoCardView.setVisibility(View.GONE);
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        }else{
            binding.progressPhotoCardView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Save the changes done on the models.
     * This includes either updating or creating new records
     *
     **/
    public void onSave(View view){
        tags = (List<Tag>) binding.chipsInput.getSelectedChipList();
        presenter.onSave();
    }

    /**
     * Show custom Chooser dialog allowing to choose method of adding a photo
     * or removing the current one.
     *
     * */
    public void onPickImage(View view){
        new MaterialDialog.Builder(this)
                .title("Add a progress photo")
                .items(R.array.uploadImage)
                .itemsIds(R.array.uploadImageItemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        switch (position){
                            case 0:
                                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                photoPickerIntent.setType("image/*");
                                startActivityForResult(photoPickerIntent,SELECT_PHOTO);
                                break;
                            case 1:
                                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(photoCaptureIntent,CAPTURE_PHOTO);
                                break;
                            case 2:
                                binding.progressPhoto.setImageResource(R.drawable.image_placeholder);
                                //Don't save image. The placeholder will be set by default
                                presenter.setDoImageSave(false);
                                break;
                        }
                    }
                })
                .show();
    }

    /**
     * Show a date picker.
     * */
    public void onPickDate(View view){
        new DatePickerDialog(this, dateSetListener,
                entryCalendar.get(Calendar.YEAR),
                entryCalendar.get(Calendar.MONTH),
                entryCalendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    /**
     * Show a time picker
     * */
    public void onPickTime(View view){
        new TimePickerDialog(this,timeSetListener,
                entryCalendar.get(Calendar.HOUR_OF_DAY),
                entryCalendar.get(Calendar.MINUTE),
                false
        ).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_PHOTO){
            if(resultCode == RESULT_OK){
                presenter.onPickerImageResult(data);
            }
        }else if(requestCode == CAPTURE_PHOTO){
            if(resultCode == RESULT_OK){
                presenter.onCaptureImageResult(data);
            }
        }
    }

    /**
     * Show the progress bar on the progress photo
     * to indicate the image is being loaded.
     *
     * */
    @Override
    public void showProgressBar(){
        binding.progressPhotoProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the progress bar on the progress photo
     * to indicate the image is done being loaded.
     *
     * */
    @Override
    public void hideProgressBar(){
        binding.progressPhotoProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setPhotoProgress(int progress){
        binding.progressPhotoProgressBar.setProgress(progress);
    }

    @Override
    public void setImageBitmap(Bitmap imageBitmap){
        binding.progressPhoto.setMaxWidth(200);
        binding.progressPhoto.setImageBitmap(imageBitmap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                binding.progressPhotoCardView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void setupTagsInput(){
        binding.chipsInput.setFilterableList(tags);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
