package com.example.herblife;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class information extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        ImageView button5 = (ImageView) findViewById(R.id.btnback);
        textView = findViewById(R.id.disclaimer);
        textView.setMovementMethod(new ScrollingMovementMethod());

        button5.setOnClickListener(view -> {
            Intent i = new Intent(information.this, MainActivity2.class);
            startActivity(i);
            finish();
        });

    }
}
