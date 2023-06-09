package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager SensorManager;
    private Sensor Light, Temperature, Humidity, Pressure;
    EditText lightValue, temperatureValue, humidityValue, pressureValue;
    Button getGPSBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lightValue = findViewById(R.id.lightValue);
        temperatureValue = findViewById(R.id.temperatureValue);
        humidityValue = findViewById(R.id.humidityValue);
        pressureValue = findViewById(R.id.pressureValue);

        SensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        Light = SensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Temperature = SensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        Humidity = SensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        Pressure = SensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        getGPSBtn = (Button) findViewById(R.id.getGPSBtn);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        getGPSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSTracker g = new GPSTracker(getApplicationContext());
                Location l = g.getLocation();
                if(l!=null)
                {
                    double lat = l.getLatitude();
                    double longi = l.getLongitude();
                    Toast.makeText(getApplicationContext(), "LAT: "+lat + "LONG: " + longi, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        SensorManager.registerListener(this, Light, SensorManager.SENSOR_DELAY_NORMAL);
        SensorManager.registerListener(this, Temperature, SensorManager.SENSOR_DELAY_NORMAL);
        SensorManager.registerListener(this, Humidity, SensorManager.SENSOR_DELAY_NORMAL);
        SensorManager.registerListener(this, Pressure, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        SensorManager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_LIGHT) {
            lightValue.setText(String.valueOf(event.values[0]));
        }
        if(event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            temperatureValue.setText(String.valueOf(event.values[0]));
        }
        if(event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
            humidityValue.setText(String.valueOf(event.values[0]));
        }
        if(event.sensor.getType() == Sensor.TYPE_PRESSURE) {
            pressureValue.setText(String.valueOf(event.values[0]));
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
    }
}