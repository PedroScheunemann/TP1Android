package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    EditText editX, editY, editZ;

    float xAccel, yAccel, zAccel, xPrevAccel, yPrevAccel, zPrevAccel, accelThreshold = 2.5f;
    boolean firstMeasure = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        editX = findViewById(R.id.editX);
        editY = findViewById(R.id.editY);
        editZ = findViewById(R.id.editZ);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()== Sensor.TYPE_ACCELEROMETER) {
            editX.setText("X: "+ String.valueOf(event.values[0]));
            editY.setText("Y: "+ String.valueOf(event.values[1]));
            editZ.setText("Z: "+ String.valueOf(event.values[2]));
            updateAccelMeasures(event.values[0], event.values[1], event.values[2]);
            if(accelerationDetected()){
                Intent intent = new Intent(getApplicationContext(), SndActivity.class);
                startActivity(intent);
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
    }

    private void updateAccelMeasures(float x, float y, float z){
        if (firstMeasure){
            xPrevAccel = x;
            yPrevAccel = y;
            zPrevAccel = z;
            firstMeasure = false;
        }
        else{
            xPrevAccel = xAccel;
            yPrevAccel = yAccel;
            zPrevAccel = zAccel;
        }
        xAccel = x;
        yAccel = y;
        zAccel = z;
    }

    private boolean accelerationDetected(){
        float deltaX = Math.abs(xAccel - xPrevAccel);
        float deltaY = Math.abs(yAccel - yPrevAccel);
        float deltaZ = Math.abs(zAccel - zPrevAccel);
        return (deltaX > accelThreshold) || (deltaY > accelThreshold) || (deltaZ > accelThreshold);
    }
}