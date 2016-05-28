package com.gck.looperdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    protected static final String tag = MainActivity.class.getSimpleName();
    private MyThread myThread;
    private MyRunnable myRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myThread = new MyThread();
        myThread.setName("BG Thread");
        myThread.start();
    }

    private class MyRunnable implements Runnable {

        private final String id;

        public MyRunnable(String id) {
            this.id = id;
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            for (int i = 0; i < 10; i++) {
                Log.d(tag, "Chandu run " + name + " " + i+" id = "+id);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onClickButton(View view) {

        if(myRunnable!=null){
            myThread.handler.removeCallbacks(myRunnable);
            myRunnable=null;
        }

        myRunnable=new MyRunnable("Hi");
        myThread.handler.post(myRunnable);

    }

    private class MyThread extends Thread {
        Handler handler;

        @Override
        public void run() {
            Log.d(tag, "Chandu Start");
            Looper.prepare();
            Log.d(tag, "Chandu in loop");
            handler = new Handler();
            Looper.loop();
            Log.d(tag, "Chandu End");
        }

    }

    @Override
    protected void onStop() {
        myThread.handler.getLooper().quit();
        super.onStop();
    }
}
