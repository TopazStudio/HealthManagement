package com.flycode.healthbloom.utils.BMI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class Calculate_BMI extends AppCompatActivity {
    //what you need to implement in the xml
    private EditText height;
    private EditText weight;
    private TextView result;
    private Spinner weigh_spinner;
    private Spinner  heigh_spinner;

    //from here everything is fine
    private float heightvalue = 0;
    private float weightvalue = 0;
    private static final String[]weight_type = {"Kilograms","Pounds"};
    private static final String[]height_type = {"Metres","Inches","Feet"};

    /*
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_weight);
        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        result = (TextView) findViewById(R.id.result);
        weigh_spinner = (Spinner)findViewById(R.id.weigh);
        heigh_spinner = (Spinner)findViewById(R.id.heigh);

        //show data in the spinners
        weigh_spinner = (Spinner)findViewById(R.id.weigh);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, weight_type);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weigh_spinner.setAdapter(dataAdapter);

        heigh_spinner = (Spinner)findViewById(R.id.heigh);
        ArrayAdapter<String>h_adapter = new ArrayAdapter<String>(test_weight.this,android.R.layout.simple_spinner_item,height_type);
        heigh_spinner.setAdapter(h_adapter);
    }

    //get the height from the dropdown send it to the calculateBMI method
    public void setHieght(){
        String heightStr = height.getText().toString();

        //check if height is in metres
        if(String.valueOf(heigh_spinner.getSelectedItem()).equals("Metres")){
            //no need of conversion
            heightvalue = Float.parseFloat(heightStr);

            //height is inches convert to metres
        }else if(String.valueOf(heigh_spinner.getSelectedItem()).equals("Inches")) {
            //call the conversion method
            Height_Weight_Converter new_height_value = new Height_Weight_Converter();
            heightvalue = new_height_value.convert_from_inches_to_metres(heightStr);

            //height is in feet convert to metres
        }else if(String.valueOf(heigh_spinner.getSelectedItem()).equals("Feet")) {
            //call the conversion method
            Height_Weight_Converter new_height_value = new Height_Weight_Converter();
            heightvalue = new_height_value.convert_from_feat_to_metres(heightStr);
        }
    }

    //get the weight from the dropdown
    public void setWeight(){
        String weightStr = weight.getText().toString();

        //check if wieght is in kgs
        if(String.valueOf(weigh_spinner.getSelectedItem()).equals("Kilograms")){
            //no need of conversion
            weightvalue = Float.parseFloat(weightStr);

            //height is inches convert to metres
        }else if(String.valueOf(weigh_spinner.getSelectedItem()).equals("Pounds")) {
            //call the conversion method
            Height_Weight_Converter new_weight_value = new Height_Weight_Converter();
            weightvalue = new_weight_value.convert_from_pounds_to_kilograms(weightStr);
        }

    }
    //calculate bmi
    public void calculateBMI(View view) {
        String heightStr = height.getText().toString();
        String weightStr = weight.getText().toString();


        if (heightStr != null && !"".equals(heightStr) &&
                weightStr != null && !"".equals(weightStr)) {

            //get the hieght and weight no need of parameters
            setHieght();
            setWeight();

            //calulate bmi and display
            float bmi = weightvalue / (heightvalue * heightvalue);

            //Toast.makeText(this, "\n "+weightvalue+" \n"+heightvalue, Toast.LENGTH_SHORT).show();
            displayBMI(bmi);
        }else{
            result.setText("Please fill in your height and weight");
        }

    }


    //display bmi
    public void displayBMI(float bmi) {
        String bmilabel = " ";
        if (Float.compare(bmi, 18.5f) <= 0) {
            bmilabel = getString(R.string.underweight);
        } else if (Float.compare(bmi, 18.5f) > 0 && Float.compare(bmi, 24.9f) <= 0) {
            bmilabel = getString(R.string.normal);
        } else if (Float.compare(bmi, 24.9f) > 0 && Float.compare(bmi, 29.9f) <= 0) {
            bmilabel = getString(R.string.overweight);
        } else {
            bmilabel = getString(R.string.obese);
        }

        bmilabel = bmi + "\n\n" + bmilabel;
        result.setText(bmilabel);

    }



*/
}

