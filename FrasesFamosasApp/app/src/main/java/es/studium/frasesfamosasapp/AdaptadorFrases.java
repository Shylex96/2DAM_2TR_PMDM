package es.studium.frasesfamosasapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import es.studium.frasesfamosasapp.modelos.FraseFamosa;
class AdaptadorFrases extends RecyclerView.Adapter<AdaptadorFrases.MyViewHolder>
{
    private List<FraseFamosa> listaDeFrases;
    public void setListaDeFrases(List<FraseFamosa> listaDeFrases)
    {
        this.listaDeFrases = listaDeFrases;
    }
    public AdaptadorFrases(List<FraseFamosa> frases)
    {
        this.listaDeFrases = frases;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View filaFrase = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fila_frase,
                viewGroup, false);
        return new MyViewHolder(filaFrase);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {
        // Obtener la frase de nuestra lista gracias al índice i
        FraseFamosa frase = listaDeFrases.get(i);
        // Obtener los datos de la lista
        String textoFrase = frase.getTexto();
        String autorFrase = frase.getAutor();
        // Y poner a los TextView los datos con setText
        myViewHolder.texto.setText(textoFrase);
        myViewHolder.autor.setText(autorFrase);
    }
    @Override
    public int getItemCount()
    {
        return listaDeFrases.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView texto, autor;
        MyViewHolder(View itemView)
        {
            super(itemView);
            this.texto = itemView.findViewById(R.id.txtFrase);
            this.autor = itemView.findViewById(R.id.txtAutor);
        }
    }
}
