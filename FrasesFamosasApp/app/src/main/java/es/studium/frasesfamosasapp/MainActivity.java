package es.studium.frasesfamosasapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import es.studium.frasesfamosasapp.controllers.FrasesController;
import es.studium.frasesfamosasapp.modelos.FraseFamosa;
public class MainActivity extends AppCompatActivity
{
    private List<FraseFamosa> listaDeFrases;
    private RecyclerView recyclerView;
    private AdaptadorFrases adaptadorFrases;
    private FrasesController frasesController;
    private FloatingActionButton fabAgregarFrase;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Definir nuestro controlador
        frasesController = new FrasesController(MainActivity.this);
        // Instanciar vistas
        recyclerView = findViewById(R.id.recyclerViewFrases);
        fabAgregarFrase = findViewById(R.id.floatingActionButton);
        // Por defecto es una lista vacía,
        // se la ponemos al adaptador y configuramos el recyclerView
        listaDeFrases = new ArrayList<>();
        adaptadorFrases = new AdaptadorFrases(listaDeFrases);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adaptadorFrases);
        // Una vez que ya configuramos el RecyclerView le ponemos los datos de la BD
        refrescarListaDeFrases();
// Listener de los clicks en la lista, o sea el RecyclerView
        recyclerView.addOnItemTouchListener(new
                RecyclerTouchListener(getApplicationContext(),
                recyclerView, new RecyclerTouchListener.ClickListener()
        {
            @Override // Un toque sencillo
            public void onClick(View view, int position)
            {
                // Pasar a la actividad EditarFraseActivity.java
                FraseFamosa fraseSeleccionada = listaDeFrases.get(position);
                Intent intent = new Intent(MainActivity.this,
                        EditarFraseActivity.class);
                intent.putExtra("idFrase", fraseSeleccionada.getId());
                intent.putExtra("textoFrase", fraseSeleccionada.getTexto());
                intent.putExtra("autorFrase", fraseSeleccionada.getAutor());
                startActivity(intent);
            }
            @Override // Un toque largo
            public void onLongClick(View view, int position)
            {
                final FraseFamosa fraseParaEliminar = listaDeFrases.get(position);
                AlertDialog dialog = new AlertDialog
                        .Builder(MainActivity.this)
                        .setPositiveButton("Sí, eliminar",
                                new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {
                                        frasesController.eliminarFrase(fraseParaEliminar);
                                        refrescarListaDeFrases();
                                    }
                                })
                        .setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {
                                        dialog.dismiss();
                                    }
                                })
                        .setTitle("Confirmar")
                        .setMessage("¿Eliminar la frase " +
                                fraseParaEliminar.getTexto() + "?")
                        .create();
                dialog.show();
            }
        }));
        // Listener del FAB
        fabAgregarFrase.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Simplemente cambiamos de actividad
                Intent intent = new Intent(MainActivity.this,
                        AgregarFraseActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        refrescarListaDeFrases();
    }
    public void refrescarListaDeFrases()
    {
        // Obtenemos la lista de la BD y se la metemos al RecyclerView
        if (adaptadorFrases == null) return;
        listaDeFrases = frasesController.obtenerFrases();
        adaptadorFrases.setListaDeFrases(listaDeFrases);
        adaptadorFrases.notifyDataSetChanged();
    }
}