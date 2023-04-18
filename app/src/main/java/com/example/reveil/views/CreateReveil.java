package com.example.reveil.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.reveil.R;
import com.example.reveil.managers.ReveilHandler;
import com.example.reveil.models.Reveil;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.List;

public class CreateReveil extends AppCompatActivity {
    boolean[] daysOfWeek = new boolean[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reveil);
    }

    public void onClickClose(View view) {
        finish();
    }

    public void setSelectedDay(View view) {
        switch (view.getId()) {
            case R.id.buttonM:
                daysOfWeek[0] = (daysOfWeek[0] == true) ? Boolean.FALSE : Boolean.TRUE;
                break;
            case R.id.buttonTue:
                daysOfWeek[1] = (daysOfWeek[1] == true) ? Boolean.FALSE : Boolean.TRUE;
                break;
            case R.id.buttonWed:
                daysOfWeek[2] = (daysOfWeek[2] == true) ? Boolean.FALSE : Boolean.TRUE;
                break;
            case R.id.buttonThu:
                daysOfWeek[3] = (daysOfWeek[3] == true) ? Boolean.FALSE : Boolean.TRUE;
                break;
            case R.id.buttonFri:
                daysOfWeek[4] = (daysOfWeek[4] == true) ? Boolean.FALSE : Boolean.TRUE;
                break;
            case R.id.buttonSat:
                daysOfWeek[5] = (daysOfWeek[5] == true) ? Boolean.FALSE : Boolean.TRUE;
                break;
            case R.id.buttonSun:
                daysOfWeek[6] = (daysOfWeek[6] == true) ? Boolean.FALSE : Boolean.TRUE;
                break;
            }
    }

    public void onClickCreation(View view) {
        TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
        String hour = String.valueOf(tp.getHour());
        String minute = String.valueOf(tp.getMinute());
        Reveil reveil=new Reveil();
        reveil.setHour(hour);
        reveil.setMinute(minute);
        reveil.setMonday(daysOfWeek[0]);
        reveil.setTuesday(daysOfWeek[1]);
        reveil.setWednesday(daysOfWeek[2]);
        reveil.setThursday(daysOfWeek[3]);
        reveil.setFriday(daysOfWeek[4]);
        reveil.setSaturday(daysOfWeek[5]);
        reveil.setSunday(daysOfWeek[6]);
        reveil.setStart(Boolean.TRUE);
        new ReveilHandler(this).addReveil(reveil);
        Toast.makeText(this, "Le réveil a bien été enregistré.", Toast.LENGTH_SHORT).show();
        finish();
    }
}