package com.example.mycardview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>
{
    private List<Anime> items;
    public static class AnimeViewHolder extends RecyclerView.ViewHolder
    {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView visitas;
        public AnimeViewHolder(View v)
        {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagen);
            nombre = (TextView) v.findViewById(R.id.nombre);
            visitas = (TextView) v.findViewById(R.id.visitas);
        }
    }
    public AnimeAdapter(List<Anime> items)
    {
        this.items = items;
    }
    @Override
    public int getItemCount()
    {
        return items.size();
    }
    @Override
    public AnimeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.anime_card, viewGroup, false);
        return new AnimeViewHolder(v);
    }
    @Override
    public void onBindViewHolder(AnimeViewHolder viewHolder, int i)
    {
        viewHolder.imagen.setImageResource(items.get(i).getImagen());
        viewHolder.nombre.setText(items.get(i).getNombre());
        viewHolder.visitas.setText("Visitas:"+String.valueOf(items.get(i).getVisitas()));
    }
}