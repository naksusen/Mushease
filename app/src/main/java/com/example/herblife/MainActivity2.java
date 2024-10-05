package com.example.herblife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button button = (Button) findViewById(R.id.menubut);
        Button button2 = (Button) findViewById(R.id.menubut2);
        Button button3 = (Button) findViewById(R.id.menubut3);
        Button exit1 = findViewById(R.id.exit);


        button.setOnClickListener(view -> {
            Intent i = new Intent(this, scanmushrooms.class);
            startActivity(i);
            finish();
        });

        button2.setOnClickListener(view -> {
            Intent i = new Intent(this, MushroomList.class);
            startActivity(i);
            finish();
        });
        button3.setOnClickListener(view -> {
            Intent i = new Intent(this, information.class);
            startActivity(i);
            finish();
        });

        exit1.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
                System.exit(0);
            }
        });
    }

    public void taptoAnimate(View view) {
        Button button = (Button)findViewById(R.id.button);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        button.startAnimation(animation);
    }
}