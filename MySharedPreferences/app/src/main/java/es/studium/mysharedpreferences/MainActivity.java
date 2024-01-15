package es.studium.mysharedpreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    EditText ed1,ed2,ed3;
    Button b1;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Nombre = "nombreKey";
    public static final String Apellidos = "apellidosKey";
    public static final String Email = "emailKey";
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1 = findViewById(R.id.editText);
        ed2 = findViewById(R.id.editText2);
        ed3 = findViewById(R.id.editText3);
        b1 = findViewById(R.id.button);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        String isShared = sharedpreferences.getString(Nombre, "");
        if(isShared!=null)
        {
            // Obtener las credenciales y colocarlas en sus respectivos lugares
            ed1.setText(sharedpreferences.getString(Nombre, ""));
            ed2.setText(sharedpreferences.getString(Apellidos, ""));
            ed3.setText(sharedpreferences.getString(Email, ""));
        }

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String n = ed1.getText().toString();
                String ph = ed2.getText().toString();
                String e = ed3.getText().toString();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Nombre, n);
                editor.putString(Apellidos, ph);
                editor.putString(Email, e);
                editor.commit();
                Toast.makeText(MainActivity.this,"Datos salvados",Toast.LENGTH_LONG).show();
            }
        });
    }
}