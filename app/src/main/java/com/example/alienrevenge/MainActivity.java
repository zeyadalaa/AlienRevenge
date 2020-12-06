package com.example.alienrevenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private SensorEventListener accelerometerListener;
    private int[] location = new int[2];

    private ImageView img_UFO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener((SensorEventListener) this, accelerometerSensor , sensorManager.SENSOR_DELAY_NORMAL);
        img_UFO =  findViewById(R.id.img_ufo);

        if (accelerometerSensor == null) {
            Toast.makeText(this, "The device has no Accelerometer", Toast.LENGTH_SHORT).show();
            finish();
        }

        accelerometerListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                img_UFO.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];
                Log.v("the content:", " and the img location = " +  x + ", " + y  );
                img_UFO.setTranslationX( x + (int) Math.pow(event.values[1], 2));
                img_UFO.setTranslationX( y + (int) Math.pow(event.values[1], 2));
                img_UFO.getLocationOnScreen(location);
                 x = location[0];
                 y = location[1];
                Log.v("the content:", event.values[0] + " and y = " + event.values[1] + " and the img location = " +  x + ", " + y  );

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }
        @Override
        protected void onResume() {
            super.onResume();
            sensorManager.registerListener(accelerometerListener, accelerometerSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }

        @Override
        protected void onPause() {
            super.onPause();
        }

    }
