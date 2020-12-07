package com.example.alienrevenge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private SensorEventListener accelerometerListener;
    private int[] location = new int[2];
    private int width;
    private int height;
    private ImageView img_UFO;
    private double[] gravity = new double[3];
    private double[] linear_acceleration = new double[3];
    private double lastTimer = 0;
    // Initialize Class
    private Handler handler = new Handler();
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**image view initialization**/
        img_UFO =  findViewById(R.id.img_ufo);

        /**helping variables to get the width and height of the screen**/


        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        width = size.x;
        height = size.y;
        int UFO_width = img_UFO.getLayoutParams().width;
        int UFO_height = img_UFO.getLayoutParams().height;

        // Move to out of screen.
        img_UFO.setX((width / 2) - (UFO_width / 2 ));
        img_UFO.setY(height -  2 * UFO_height);
//        Log.v("the content:", " screen " + width + ", " + height + " elsora data" + UFO_width + ", "+ UFO_width );
//        Log.v("the content:", " elsora " + img_UFO.getX() + ", " + img_UFO.getY());




        /** sensor data initialization **/
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        /**check if the sensor is avaliable in the device**/
        if (accelerometerSensor == null) {
            Toast.makeText(this, "The device has no Accelerometer", Toast.LENGTH_SHORT).show();
            finish();
        }
        sensorManager.registerListener(accelerometerListener, accelerometerSensor, SensorManager.SENSOR_DELAY_FASTEST);

        /**Creating Accelerometer event listener**/
        accelerometerListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                // alpha is calculated as t / (t + dT)
                // with t, the low-pass filter's time-constant
                // and dT, the event delivery rate
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                changePos(event);
                            }
                        });
                    }
                }, 2, 0);


//                if( y <= height-200 && y >= 200){
//                    img_UFO.setY((float)(y + linear_acceleration[1]));
//                    img_UFO.getLocationInWindow(location);
//                    y = location[1];
//                }
//                Log.v("the content:", linear_acceleration[0] + " and y = " + linear_acceleration[1] + " and the img location = " + x + ", " + y);
//
//                            Log.v("the content:", " and the img location = " +  (x) + ", " + y + "width = " + width);


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

        public void changePos(SensorEvent event) {


            linear_acceleration[0] = event.values[0] ;
            linear_acceleration[1] = event.values[1] ;
            linear_acceleration[2] = event.values[2] ;

            img_UFO.getLocationInWindow(location);
            int x = location[0];
            int y = location[1];
            if (Math.abs(linear_acceleration[0]) > 0.5) {
                Log.v("the acceleration", linear_acceleration[0] + ", " + linear_acceleration[1]);
                if (x <= width - 370 && x >= 100 /*&& Math.abs(System.currentTimeMillis() - lastTimer) > 5e-3*/) {

                    Log.v("the content:", " and the img location = " + x + ", " + y);
                    img_UFO.setX((float) (x + linear_acceleration[0] * -3));
                    img_UFO.getLocationInWindow(location);
                    x = location[0];
                    lastTimer = System.currentTimeMillis();

                } else if (x > width - 370) {
                    img_UFO.setX(width - 380);
                } else if (x < 100) {
                    img_UFO.setX(110);
                }
            }
        }
}




//    public void moveRight(View view){
//        distance  -= 10;
//        img_UFO.getLocationInWindow(location);
//        int x = location[0];
//        int y = location[1];
//        if(x <= width - 370 && x >= 100){
//        img_UFO.setX(x + distance);
//            Log.v("the content:", " and the img location = " +  (x) + ", " + y + "width = " + width);
//
//        }

//                img_UFO.setTranslationX( x + (int) Math.pow(event.values[1], 2));
//                img_UFO.setTranslationX( y + (int) Math.pow(event.values[1], 2));
//                img_UFO.getLocationInWindow(location);

//       //
//    float ALPHA = 0.15f;
//    private float[] lowPass(float input[], float output[]) {
//
//        for (int i = 0; i < input.length;i++) {
//            output[i] = output[i] + ALPHA * (input[i] - output[i]);
//        }
//        return output;
//    }
//    }
