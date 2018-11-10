package com.example.trk.petmobile;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {

    private int Sleep_Timer = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_welcome);
        LogoLauncher logoLauncher = new LogoLauncher();
        logoLauncher.start();
    }
        private class LogoLauncher extends Thread {
            public void run() {
                try {
                    sleep(1000 * Sleep_Timer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(SplashActivity.this, SignActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }
    }

