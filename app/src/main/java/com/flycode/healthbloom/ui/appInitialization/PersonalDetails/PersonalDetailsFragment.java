package com.flycode.healthbloom.ui.appInitialization.PersonalDetails;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.models.User;

import java.util.Calendar;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalDetailsFragment extends Fragment {

    @Inject
    User user;

    @BindView(R.id.txt_full_name)
    EditText txt_full_name;
    @BindView(R.id.gender_radio_group)
    RadioGroup gender_radio_group;
    @BindView(R.id.dt_day)
    EditText dt_day;
    @BindView(R.id.dt_month)
    EditText dt_month;
    @BindView(R.id.dt_year)
    EditText dt_year;


    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Unbinder unbinder;

    public PersonalDetailsFragment() {
        // Required empty public constructor
    }

    public static PersonalDetailsFragment newInstance() {
        PersonalDetailsFragment fragment = new PersonalDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_details, container, false);
        unbinder = ButterKnife.bind(this,view);
        init();
        return view;
    }

    /**
     * Initialize DateSetListener to populate Date EditTexts
     * when user chooses a date
     *
     * */
    public void init(){
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dt_day.setText(dayOfMonth);
                dt_month.setText(month);
                dt_year.setText(year);
            }
        };
    }

    /**
     * Get gender selection
     * */
    public String getGender(){
        int id = gender_radio_group.getCheckedRadioButtonId();
        return id == R.id.male_radio_btn ? "Male" : "Female";
    }

    /**
     * Helper method for getting age from date specified by user
     * */
    private int getAge(Calendar dob){
        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }
        return age;
    }

    /**
     * Create and show DatePicker on selection of any Date EditText
     * */
    @OnClick({R.id.dt_year,R.id.dt_month,R.id.dt_day})
    public void onDateSelection(){
        Calendar calendar = Calendar.getInstance();
        int yr = calendar.get(Calendar.YEAR);
        int mon = calendar.get(Calendar.MONTH);
        int dy = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePickerDialog = new DatePickerDialog(
                Objects.requireNonNull(getActivity()),
                android.R.style.Theme_Material_Dialog_MinWidth,
                mDateSetListener,
                yr, mon, dy);
        Objects.requireNonNull(mDatePickerDialog.getWindow())
                .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDatePickerDialog.show();
    }


    public void commitChanges(){
        int day = Integer.parseInt(dt_year.getText().toString());
        int month = Integer.parseInt(dt_year.getText().toString());
        int year = Integer.parseInt(dt_year.getText().toString());
        Calendar cal = Calendar.getInstance();
        cal.set(year,month,day);

        user.setFullname(txt_full_name.getText().toString());
        user.setDOB(cal);
        user.setAge(getAge(cal));
        user.setGender(getGender());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
