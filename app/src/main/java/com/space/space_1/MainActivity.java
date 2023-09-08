package com.space.space_1;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private SensorEventListener sensorEventListener;
    private BallView ballView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ballView = findViewById(R.id.ballView);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);//来获取传感器管理器的实例
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//获取系统服务，其中包括传感器服务

        /*private SensorEventListener sensorEventListener;是一个声明了SensorEventListener类型的成员变量。SensorEventListener是一个接口，用于监听传感器事件的回调。

        通过将SensorEventListener作为成员变量，可以在类中的各个方法中使用和操作该监听器。常见的操作包括注册监听器、取消注册监听器、处理传感器数据等。

        在实际使用中，你可以创建一个SensorEventListener的实现类，并实现其中的回调方法，例如onSensorChanged()用于处理传感器数据变化的事件。然后将这个实现类的实例赋值给sensorEventListener成员变量，以便在合适的地方进行注册和取消注册。

        使用成员变量来持有SensorEventListener的实例，可以方便地在不同的方法和生命周期中使用同一个监听器实例，从而实现对传感器事件的实时监听和处理。*/
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];

                // 根据x和y的值计算小球的新位置，并更新UI
                updateBallPosition(x, y);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // 这里可以留空
            }
        };

        ballView.setX(getScreenWidth()/8);
        ballView.setY(getScreenHeight()/8);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        //registerListener()方法用于注册一个传感器监听器，并指定要监听的传感器（accelerometer）和数据更新的频率（SensorManager.SENSOR_DELAY_NORMAL表示普通的更新频率）。
       /* 最后一个参数SensorManager.SENSOR_DELAY_NORMAL是指定传感器数据更新的频率。它是一个整数常量，表示不同的更新速率。在这里，SENSOR_DELAY_NORMAL表示普通的更新速率，即相对较快的速度。

        除了SENSOR_DELAY_NORMAL，还有其他几个预定义的更新频率常量可供选择，包括：

        SENSOR_DELAY_FASTEST：最快的更新速率，一般用于需要非常精确和实时反馈的应用。
        SENSOR_DELAY_GAME：适用于游戏等需要高频更新的应用场景。
        SENSOR_DELAY_UI：适用于用户界面操作，提供相对较快的更新速率。
        SENSOR_DELAY_NORMAL：适用于大多数一般应用场景的普通更新速率。
        根据不同的应用需求，可以选择适当的更新速率来平衡数据的准确性和能耗。*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }

    /*private void updateBallPosition(float x, float y) {
        int screenWidth = getScreenWidth();
        int screenHeight = getScreenHeight();
        int ballRadius = 50;

        int ballX = (int) (screenWidth * (-x / 9.8)) + screenWidth / 2;
        int ballY = (int) (screenHeight * (y / 9.8)) + screenHeight / 2;



        // 更新小球的位置
        ballView.setX(ballX - ballRadius);
        ballView.setY(ballY - ballRadius);
    }*/

    private void updateBallPosition(float x, float y) {
        int screenWidth = getScreenWidth();
        int screenHeight = getScreenHeight();
        int ballRadius = 50;



        int ballX = (int) (screenWidth * (-x / 9.8)) + screenWidth / 2;
        int ballY = (int) (screenHeight * (y / 9.8)) + screenHeight / 2;

        // 取消之前的动画
        ballView.animate().cancel();

        // 创建属性动画，设置小球移动的属性为translationX和translationY，即X、Y方向上的偏移量
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(ballView, "translationX", ballView.getX(), ballX - ballRadius);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(ballView, "translationY", ballView.getY(), ballY - ballRadius);

        // 设置动画持续时间为200毫秒
        animatorX.setDuration(200);
        animatorY.setDuration(200);

        // 设置插值器，使动画在开始和结束时加速和减速
        animatorX.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorY.setInterpolator(new AccelerateDecelerateInterpolator());

        // 启动动画
        animatorX.start();
        animatorY.start();
    }

    private int getScreenWidth() {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    private int getScreenHeight() {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

}