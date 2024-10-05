package com.example.herblife;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
       private static int splash = 8000;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            new Handler().postDelayed(new Runnable(){


                @Override
                public void run() {
                    Intent Homeintent = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(Homeintent);
                    finish();
                }
            },splash);

    }
}