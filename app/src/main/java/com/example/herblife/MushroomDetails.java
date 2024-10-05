package com.example.herblife;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class MushroomDetails extends Dialog {
    private String name, sciname, descr;
    TextView textView, textView1;

    public MushroomDetails(@NonNull Context context, String name, String sciname, String descr) {
        super(context);
        this.name = name;
        this.sciname = sciname;
        this.descr = descr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_details);
        textView = findViewById(R.id.muDescription);
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView1 = findViewById(R.id.muInformation);
        textView1.setMovementMethod(new ScrollingMovementMethod());

        TextView nameTV = findViewById(R.id.muName);
        TextView scientificTV = findViewById(R.id.muDescription);
        TextView descTV = findViewById(R.id.muInformation);

        nameTV.setText(name);
        scientificTV.setText(sciname);
        descTV.setText(descr);

        Button done = findViewById(R.id.okay);
        done.setOnClickListener(view -> dismiss());

    }
}
