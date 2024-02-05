package es.studium.apisimple;
import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    Button btnConectar;
    TextView txtUserId;
    TextView txtId;
    TextView txtTitle;
    TextView txtCompleted;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnConectar = findViewById(R.id.button);
        btnConectar.setOnClickListener(this);
    }
    @Override
    public void onClick(View v)
    {
        AccesoRemoto acceso = new AccesoRemoto();
        acceso.execute();
    }
    private class AccesoRemoto extends AsyncTask<Void, Void, String>
    {
        String userId = "";
        String id = "";
        String title = "";
        String completed = "";
        protected void onPreExecute()
        {
            Toast.makeText(MainActivity.this, "Obteniendo datos...",
                    Toast.LENGTH_SHORT).show();
        }
        protected String doInBackground(Void... argumentos)
        {
            try
            {
                // Crear la URL de conexión al API
                URL url = new URL("https://jsonplaceholder.typicode.com/todos/1");
                // Crear la conexión HTTP
                HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();
                // Establecer método de comunicación. Por defecto GET.
                myConnection.setRequestMethod("GET");
                if (myConnection.getResponseCode() == 200)
                {
                    // Conexión exitosa
                    // Creamos Stream para la lectura de datos desde el servidor
                    InputStream responseBody = myConnection.getInputStream();
                    InputStreamReader responseBodyReader =
                            new InputStreamReader(responseBody, StandardCharsets.UTF_8);
                    // Creamos Buffer de lectura
                    BufferedReader bR = new BufferedReader(responseBodyReader);
                    String line;
                    StringBuilder responseStrBuilder = new StringBuilder();
                    // Leemos el flujo de entrada
                    while (null != (line = bR.readLine()))
                    {
                        responseStrBuilder.append(line);
                    }
                    // Parseamos respuesta en formato JSON
                    JSONObject jsonobject = new JSONObject(responseStrBuilder.toString());
                    // Sacamos dato a dato obtenido
                    userId = jsonobject.getString("userId");
                    id = jsonobject.getString("id");
                    title = jsonobject.getString("title");
                    completed = jsonobject.getString("completed");
                    responseBody.close();
                    responseBodyReader.close();
                    myConnection.disconnect();
                }
                else
                {
                    // Error en la conexión
                    Toast.makeText(MainActivity.this, "¡Conexión fallida!",
                            Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                Toast.makeText(MainActivity.this, "¡Conexión fallida!",
                        Toast.LENGTH_SHORT).show();
            }
            return (null);
        }
        protected void onPostExecute(String mensaje)
        {
            // Actualizamos los cuadros de texto
            txtUserId = findViewById(R.id.editTextUserId);
            txtUserId.setText(userId);
            txtId = findViewById(R.id.editTextId);
            txtId.setText(id);
            txtTitle = findViewById(R.id.editTextTitle);
            txtTitle.setText(title);
            txtCompleted = findViewById(R.id.editTextCompleted);
            txtCompleted.setText(completed);
        }
    }
}