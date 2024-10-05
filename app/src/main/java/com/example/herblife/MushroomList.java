package com.example.herblife;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MushroomList extends AppCompatActivity implements MushroomListInterface{
    ImageView back;
    ArrayList<MushroomModel> mushroomModels = new ArrayList<>();
    int[] mushroomImage = {R.drawable.amethyst_deceiver, R.drawable.beech, R.drawable.chanterelle, R.drawable.chicken_of_the_woods,
            R.drawable.cremini,R.drawable.enoki,R.drawable.ganoderma_lucidum,R.drawable.hedgehog,R.drawable.king_trumpet,
            R.drawable.lions_mane,R.drawable.maitake,R.drawable.matsutake,R.drawable.morel,R.drawable.osyter_mushrooms,R.drawable.porcini
            ,R.drawable.puffballs,R.drawable.shiitake};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mushroom_list);
        back = findViewById(R.id.buttonback1);
        back.setOnClickListener(view -> {
            Intent i = new Intent(this, MainActivity2.class);
            startActivity(i);
            finish();
        });

        RecyclerView recyclerView =findViewById(R.id.mushroomsview);
        setUpMushroomModels();

        MushroomAdapter adapter = new MushroomAdapter(this, mushroomModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void setUpMushroomModels(){
        String[] mushroomNames = getResources().getStringArray(R.array.mushroom_list_name);
        String[] mushroomSNames = getResources().getStringArray(R.array.mushroom_list_sname);
        String[] mushroomDesc = getResources().getStringArray(R.array.mushroom_list_desc);

        for(int i=0; i<mushroomNames.length;i++){
            mushroomModels.add(new MushroomModel(mushroomNames[i], mushroomSNames[i], mushroomDesc[i],
                    mushroomImage[i]));
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent i = new Intent(this, MushroomView.class);

        i.putExtra("Name", mushroomModels.get(position).getMushname());
        i.putExtra("Scientific Name", mushroomModels.get(position).getMushsciname());
        i.putExtra("Mushroom Desc", mushroomModels.get(position).getMushdesc());
        i.putExtra("Image", mushroomModels.get(position).getMushimage());

        startActivity(i);
        finish();

    }
}