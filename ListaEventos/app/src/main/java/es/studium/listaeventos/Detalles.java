package es.studium.listaeventos;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Detalles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        TextView provincia = findViewById(R.id.textView2);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null)
        {
            provincia.setText(bundle.getString("provinciaElegida"));
        }
    }
}