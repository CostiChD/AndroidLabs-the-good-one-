package com.example.lab2simple;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SensorsDisplayActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager = null;
    private List<Sensor> sensorsList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors_display);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorsList = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        LinearLayout dinamic = (LinearLayout) findViewById(R.id.dinamicLayout);

        for (Sensor sensor : sensorsList) {
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView textView = new TextView(this);
            textView.setLayoutParams(lparams);
            textView.setId(sensor.getName().length());
            textView.setText(sensor.getName().toString());
            dinamic.addView(textView);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSensorChanged(SensorEvent event) {
        String sensorName = event.sensor.getName();
        int id = event.sensor.getName().length();
        TextView textView = (TextView) findViewById(id);
        String toUpdate = textView.getText().toString();
        int iend = toUpdate.indexOf("[");

        if (iend != -1) {
            textView.setText(String.format("%s %s", toUpdate.substring(0, toUpdate.length() - 1)
                    .substring(0, iend), Arrays.toString(event.values)));
        } else {
            textView.setText(String.format("%s %s", toUpdate.substring(0, toUpdate.length() - 1),
                    Arrays.toString(event.values)));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onStart() {
        super.onStart();

        for (Sensor sensor : sensorsList) {
            mSensorManager.registerListener(this, sensor, 10000000, 10000000);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }
}
