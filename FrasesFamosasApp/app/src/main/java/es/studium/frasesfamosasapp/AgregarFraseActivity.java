package es.studium.frasesfamosasapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import es.studium.frasesfamosasapp.controllers.FrasesController;
import es.studium.frasesfamosasapp.modelos.FraseFamosa;
public class AgregarFraseActivity extends AppCompatActivity
{
    private Button btnAgregarFrase, btnCancelarNuevaFrase;
    private EditText etTexto, etAutor;
    private FrasesController frasesController;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_frase);
// Instanciar vistas
        etTexto = findViewById(R.id.txtTexto);
        etAutor = findViewById(R.id.txtAutor);
        btnAgregarFrase = findViewById(R.id.btnAgregarFrase);
        btnCancelarNuevaFrase = findViewById(R.id.btnCancelarNuevaFrase);
        // Crear el controlador
        frasesController = new FrasesController(AgregarFraseActivity.this);
        // Agregar listener del botón de guardar
        btnAgregarFrase.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Resetear errores a ambos
                etTexto.setError(null);
                etAutor.setError(null);
                String texto = etTexto.getText().toString(),
                        autor = etAutor.getText().toString();
                if ("".equals(texto))
                {
                    etTexto.setError("Escribe el texto de la frase");
                    etTexto.requestFocus();
                    return;
                }
                if ("".equals(autor))
                {
                    etAutor.setError("Escribe el autor de la frase");
                    etAutor.requestFocus();
                    return;
                }
                // Ya pasó la validación
                FraseFamosa nuevaFrase = new FraseFamosa(texto, autor);
                long id = frasesController.nuevaFrase(nuevaFrase);
                if (id == -1)
                {
                    // De alguna manera ocurrió un error
                    Toast.makeText(AgregarFraseActivity.this,
                            "Error al guardar. Intenta de nuevo", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Terminar
                    finish();
                }
            }
        });
        // El de cancelar simplemente cierra la actividad
        btnCancelarNuevaFrase.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
