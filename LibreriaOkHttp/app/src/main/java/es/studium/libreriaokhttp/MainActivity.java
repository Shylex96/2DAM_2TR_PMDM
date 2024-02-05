package es.studium.libreriaokhttp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.text_view);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        new ConsultaRemota(mTextView);
        //new AltaRemota("Nombre", "Apellidos", 954954954);
        //new BorradoRemoto(22);
        //new ModificacionRemota(25, "Nuevo2", "Nuevo2", 654654654);
    }
}