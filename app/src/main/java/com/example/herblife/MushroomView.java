package com.example.herblife;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MushroomView extends AppCompatActivity {
   ImageView back;
   TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_mushroom);
        back = findViewById(R.id.buttonback);
        back.setOnClickListener(view -> {
            Intent i = new Intent(this, MushroomList.class);
            startActivity(i);
            finish();
        });

        String name = getIntent().getStringExtra("Name");
        String sciname = getIntent().getStringExtra("Scientific Name");
        String descr = getIntent().getStringExtra("Mushroom Desc");
        int image = getIntent().getIntExtra("Image",0);

        TextView nameTV = findViewById(R.id.M_Name);
        TextView scientificTV = findViewById(R.id.M_Scient);
        TextView descTV = findViewById(R.id.M_Description);
        ImageView mushIM = findViewById(R.id.M_Image);

        nameTV.setText(name);
        scientificTV.setText(sciname);
        descTV.setText(descr);
        mushIM.setImageResource(image);

        textView = findViewById(R.id.M_Description);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }
}
