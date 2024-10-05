package com.example.herblife;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MushroomAdapter extends RecyclerView.Adapter<MushroomAdapter.MyViewHolder> {
    private final MushroomListInterface mushroomListInterface;
    Context context;
    ArrayList<MushroomModel> mushroomModels;
    public MushroomAdapter(Context context, ArrayList<MushroomModel> mushroomModels, MushroomListInterface mushroomListInterface){
        this.context =context;
        this.mushroomModels = mushroomModels;
        this.mushroomListInterface = mushroomListInterface;

    }
    @NonNull
    @Override
    public MushroomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.mushroom_viewrow,parent,false);
        return new MushroomAdapter.MyViewHolder(view, mushroomListInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MushroomAdapter.MyViewHolder holder, int position) {
        holder.tvName.setText(mushroomModels.get(position).getMushname());
        holder.tvSName.setText(mushroomModels.get(position).getMushsciname());
        holder.imageView.setImageResource(mushroomModels.get(position).getMushimage());

    }

    @Override
    public int getItemCount() {
        return mushroomModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tvName, tvSName;
        public MyViewHolder(@NonNull View itemView, MushroomListInterface mushroomListInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.mushimage);
            tvName = itemView.findViewById(R.id.mushName);
            tvSName = itemView.findViewById(R.id.mushSName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mushroomListInterface != null){
                        int pos = getAbsoluteAdapterPosition();

                        if(pos!= RecyclerView.NO_POSITION){
                            mushroomListInterface.onItemClick(pos);
                        }


                    }
                }
            });

        }
    }
}
