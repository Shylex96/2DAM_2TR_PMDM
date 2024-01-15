package es.studium.frasesfamosasapp;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import es.studium.frasesfamosasapp.controllers.FrasesController;
import es.studium.frasesfamosasapp.modelos.FraseFamosa;
public class EditarFraseActivity extends AppCompatActivity
{
    private EditText editarFrase, editarAutor;
    private Button btnGuardarCambios, btnCancelarEdicion;
    private FraseFamosa frase;//La frase que vamos a estar editando
    private FrasesController frasesController;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_frase);
        // Recuperar datos que enviaron
        Bundle extras = getIntent().getExtras();
        // Si no hay datos, salimos
        if (extras == null)
        {
            finish();
            return;
        }
        // Instanciar el controlador de las frases
        frasesController = new FrasesController(EditarFraseActivity.this);
        // Rearmar la frase
        // Nota: igualmente solamente podríamos mandar el id y recuperar la frase de la BD
        long idFrase = extras.getLong("idFrase");
        String textoFrase = extras.getString("textoFrase");
        String autorFrase = extras.getString("autorFrase");
        frase = new FraseFamosa(textoFrase, autorFrase, idFrase);
        // Ahora declaramos las vistas
        editarFrase = findViewById(R.id.etEditarFrase);
        editarAutor = findViewById(R.id.etEditarAutor);
        btnCancelarEdicion = findViewById(R.id.btnCancelarEdicionFrase);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambiosFrase);
        // Rellenar los EditText con los datos de la frase
        editarAutor.setText(frase.getAutor());
        editarFrase.setText(frase.getTexto());
        // Listener del click del botón para salir, simplemente cierra la actividad
        btnCancelarEdicion.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
// Listener del click del botón que guarda cambios
        btnGuardarCambios.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Remover previos errores si existen
                editarFrase.setError(null);
                editarAutor.setError(null);
                // Crear la frase con los nuevos cambios pero ponerle
                // el id de la anterior
                String nuevoTexto = editarFrase.getText().toString();
                String nuevoAutor = editarAutor.getText().toString();
                if (nuevoTexto.isEmpty())
                {
                    editarFrase.setError("Escribe el texto");
                    editarFrase.requestFocus();
                    return;
                }
                if (nuevoAutor.isEmpty())
                {
                    editarAutor.setError("Escribe el autor");
                    editarAutor.requestFocus();
                    return;
                }
                // Si llegamos hasta aquí es porque los datos ya están validados
                FraseFamosa fraseConNuevosCambios = new FraseFamosa(nuevoTexto,
                        nuevoAutor, frase.getId());
                int filasModificadas = frasesController.guardarCambios(fraseConNuevosCambios);
                if (filasModificadas != 1)
                {
                    // Ocurrió un error porque se debió modificar únicamente una fila
                    Toast.makeText(EditarFraseActivity.this,
                            "Error guardando cambios. Intente de nuevo.",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Si las cosas van bien, volvemos a la principal
                    // cerrando esta actividad
                    finish();
                }
            }
        });
    }
}
