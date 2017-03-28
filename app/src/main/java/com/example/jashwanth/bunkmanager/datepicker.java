package com.example.jashwanth.bunkmanager;

import android.icu.util.Calendar;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.GregorianCalendar;

public class datepicker extends AppCompatActivity {

    DatePicker datePicker;

    public void attend(View view){
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        java.util.Calendar cal = new GregorianCalendar(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
        Toast.makeText(this, Integer.toString(cal.get(java.util.Calendar.DAY_OF_WEEK)), Toast.LENGTH_SHORT).show();
    }
    public void SetMyCustomFormat()
    {
        // Set the Format type and the CustomFormat string.
    }


    public void skip(View view){

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datepicker);


    }
}

