package es.studium.accesoapi;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtId;
    TextView txtNombre;
    TextView txtApellidos;
    TextView txtTelefono;
    ConsultaRemota acceso;
    AltaRemota alta;
    BajaRemota baja;
    ModificacionRemota modifica;
    JSONArray result;
    JSONObject jsonobject;
    int posicion;
    String idContacto = "";
    String nombreContacto = "";
    String apellidosContacto = "";
    String telefonoContacto = "";
    int queAccion; // 0 Alta, 1 Modificar
    Button btnAnterior;
    Button btnSiguiente;
    Button btnNuevo;
    Button btnAceptar;
    Button btnEliminar;
    Button btnModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        acceso = new ConsultaRemota();
        acceso.execute();
        btnAnterior = findViewById(R.id.btnAnterior);
        btnAnterior.setOnClickListener(this);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnSiguiente.setOnClickListener(this);
        btnNuevo = findViewById(R.id.btnNuevo);
        btnNuevo.setOnClickListener(this);
        btnAceptar = findViewById(R.id.btnAceptar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(this);
        btnModificar = findViewById(R.id.btnModificar);
        btnModificar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAnterior) {
            // Anterior
            if (posicion > 0) {
                posicion--;
                try {
                    jsonobject = result.getJSONObject(posicion);
                    //Sacamos dato a dato obtenido
                    idContacto = jsonobject.getString("idContacto");
                    nombreContacto = jsonobject.getString("nombreContacto");
                    apellidosContacto = jsonobject.getString("apellidosContacto");
                    telefonoContacto = jsonobject.getString("telefonoContacto");
                    // Actualizamos los cuadros de texto
                    txtId.setText(idContacto);
                    txtNombre.setText(nombreContacto);
                    txtApellidos.setText(apellidosContacto);
                    txtTelefono.setText(telefonoContacto);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        // Siguiente
        else if (v.getId() == R.id.btnSiguiente) {
            if (posicion < result.length() - 1) {
                posicion++;
                try {
                    jsonobject = result.getJSONObject(posicion);
                    //Sacamos dato a dato obtenido
                    idContacto = jsonobject.getString("idContacto");
                    nombreContacto = jsonobject.getString("nombreContacto");
                    apellidosContacto = jsonobject.getString("apellidosContacto");
                    telefonoContacto = jsonobject.getString("telefonoContacto");
                    // Actualizamos los cuadros de texto
                    txtId.setText(idContacto);
                    txtNombre.setText(nombreContacto);
                    txtApellidos.setText(apellidosContacto);
                    txtTelefono.setText(telefonoContacto);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        // Nuevo
        else if (v.getId() == R.id.btnNuevo) {
            queAccion = 0;
            txtId = findViewById(R.id.editTextId);
            txtId.setText("");
            txtNombre = findViewById(R.id.editTextNombre);
            txtNombre.setText("");
            txtNombre.setFocusable(true);
            txtNombre.requestFocus();
            txtApellidos = findViewById(R.id.editTextApellidos);
            txtApellidos.setText("");
            txtApellidos.setFocusable(true);
            txtTelefono = findViewById(R.id.editTextTelefono);
            txtTelefono.setText("");
            txtTelefono.setFocusable(true);
            btnAceptar.setEnabled(true);
            btnAceptar.setOnClickListener(this);
        }
        // Modificar
        else if (v.getId() == R.id.btnModificar) {
            queAccion = 1;
            txtNombre = findViewById(R.id.editTextNombre);
            txtNombre.setFocusable(true);
            txtNombre.requestFocus();
            txtApellidos = findViewById(R.id.editTextApellidos);
            txtApellidos.setFocusable(true);
            txtTelefono = findViewById(R.id.editTextTelefono);
            txtTelefono.setFocusable(true);
            btnAceptar.setEnabled(true);
            btnAceptar.setOnClickListener(this);
        }
        // Eliminar
        else if (v.getId() == R.id.btnEliminar) {
            Toast.makeText(MainActivity.this, "Eliminando datos...",
                    Toast.LENGTH_SHORT).show();
            txtId = findViewById(R.id.editTextId);
            baja = new BajaRemota(txtId.getText().toString());
            baja.execute();
            acceso = new ConsultaRemota();
            acceso.execute();
        }
        // Aceptar
        else if (v.getId() == R.id.btnAceptar) {
            switch (queAccion) {
                case 0: // Alta
                    Toast.makeText(MainActivity.this, "Alta datos...",
                            Toast.LENGTH_SHORT).show();
                    txtNombre = findViewById(R.id.editTextNombre);
                    txtApellidos = findViewById(R.id.editTextApellidos);
                    txtTelefono = findViewById(R.id.editTextTelefono);
                    alta = new AltaRemota(txtNombre.getText().toString(),
                            txtApellidos.getText().toString(),
                            txtTelefono.getText().toString());
                    alta.execute();
                    acceso = new ConsultaRemota();
                    acceso.execute();
                    txtNombre.setFocusable(false);
                    txtApellidos.setFocusable(false);
                    txtTelefono.setFocusable(false);
                    btnAceptar.setEnabled(false);
                    break;
                case 1: // Modificación
                    txtId = findViewById(R.id.editTextId);
                    txtNombre = findViewById(R.id.editTextNombre);
                    txtApellidos = findViewById(R.id.editTextApellidos);
                    txtTelefono = findViewById(R.id.editTextTelefono);
                    modifica = new ModificacionRemota(txtId.getText().toString(),
                            txtNombre.getText().toString(),
                            txtApellidos.getText().toString(),
                            txtTelefono.getText().toString());
                    modifica.execute();
                    acceso = new ConsultaRemota();
                    acceso.execute();
                    txtNombre.setFocusable(false);
                    txtApellidos.setFocusable(false);
                    txtTelefono.setFocusable(false);
                    btnAceptar.setEnabled(false);
                    break;
            }
        }
    }

    private class ConsultaRemota extends AsyncTask<Void, Void, String> {
        // Inspectores
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this, "Obteniendo datos...",
                    Toast.LENGTH_SHORT).show();
        }

        protected String doInBackground(Void... argumentos) {
            try {
                // Crear la URL de conexión al API
                URL url = new URL("http://192.168.0.80/ApiRest/contactos.php");
                // Crear la conexión HTTP
                HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();
                // Establecer método de comunicación. Por defecto GET.
                myConnection.setRequestMethod("GET");
                if (myConnection.getResponseCode() == 200) {
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
                    while ((line = bR.readLine()) != null) {
                        responseStrBuilder.append(line);
                    }
                    // Parseamos respuesta en formato JSON
                    result = new JSONArray(responseStrBuilder.toString());
                    // Nos quedamos solamente con la primera
                    posicion = 0;
                    jsonobject = result.getJSONObject(posicion);
                    // Sacamos dato a dato obtenido
                    idContacto = jsonobject.getString("idContacto");
                    nombreContacto = jsonobject.getString("nombreContacto");
                    apellidosContacto = jsonobject.getString("apellidosContacto");
                    telefonoContacto = jsonobject.getString("telefonoContacto");
                    responseBody.close();
                    responseBodyReader.close();
                    myConnection.disconnect();
                } else {
                    // Error en la conexión
                    Log.println(Log.ERROR, "Error", "¡Conexión fallida!");
                }
            } catch (Exception e) {
                Log.println(Log.ERROR, "Error", "¡Conexión fallida!");
            }
            return (null);
        }

        protected void onPostExecute(String mensaje) {
            // Actualizamos los cuadros de texto
            txtId = findViewById(R.id.editTextId);
            txtId.setText(idContacto);
            txtNombre = findViewById(R.id.editTextNombre);
            txtNombre.setText(nombreContacto);
            txtApellidos = findViewById(R.id.editTextApellidos);
            txtApellidos.setText(apellidosContacto);
            txtTelefono = findViewById(R.id.editTextTelefono);
            txtTelefono.setText(telefonoContacto);
        }
    }

    private class AltaRemota extends AsyncTask<Void, Void, String> {
        // Atributos
        String nombreContacto;
        String apellidosContacto;
        String telefonoContacto;

        // Constructor
        public AltaRemota(String nombre, String apellidos, String telefono) {
            this.nombreContacto = nombre;
            this.apellidosContacto = apellidos;
            this.telefonoContacto = telefono;
        }

        // Inspectores
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this, "Alta..." + this.nombreContacto,
                    Toast.LENGTH_SHORT).show();
        }

        protected String doInBackground(Void... argumentos) {
            try {
                // Crear la URL de conexión al API
                URL url = new URL("http://192.168.0.80/ApiRest/contactos.php");
                // Crear la conexión HTTP
                HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();
                // Establecer método de comunicación.
                myConnection.setRequestMethod("POST");
                // Conexión exitosa
                HashMap<String, String> postDataParams = new HashMap<>();
                postDataParams.put("nombreContacto", this.nombreContacto);
                postDataParams.put("apellidosContacto", this.apellidosContacto);
                postDataParams.put("telefonoContacto", this.telefonoContacto);
                myConnection.setDoInput(true);
                myConnection.setDoOutput(true);
                OutputStream os = myConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,
                        StandardCharsets.UTF_8));
                writer.write(getPostDataString(postDataParams));
                writer.flush();
                writer.close();
                os.close();
                myConnection.getResponseCode();
                if (myConnection.getResponseCode() == 200) {
                    // Success
                    myConnection.disconnect();
                } else {
                    // Error handling code goes here
                    Log.println(Log.ASSERT, "Error", "Error");
                }
            } catch (Exception e) {
                Log.println(Log.ASSERT, "Excepción", e.getMessage());
            }
            return (null);
        }

        protected void onPostExecute(String mensaje) {
            // Actualizamos los cuadros de texto
            txtId = findViewById(R.id.editTextId);
            txtId.setText(idContacto);
            txtNombre = findViewById(R.id.editTextNombre);
            txtNombre.setText(nombreContacto);
            txtApellidos = findViewById(R.id.editTextApellidos);
            txtApellidos.setText(apellidosContacto);
            txtTelefono = findViewById(R.id.editTextTelefono);
            txtTelefono.setText(telefonoContacto);
            Toast.makeText(MainActivity.this, "Alta Correcta...",
                    Toast.LENGTH_SHORT).show();
        }

        private String getPostDataString(HashMap<String, String> params)
                throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first) {
                    first = false;
                } else {
                    result.append("&");
                }
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            return result.toString();
        }
    }

    private class BajaRemota extends AsyncTask<Void, Void, String> {
        // Atributos
        String idContacto;

        // Constructor
        public BajaRemota(String id) {
            this.idContacto = id;
        }

        // Inspectores
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this, "Eliminando...",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                // Crear la URL de conexión al API
                URI baseUri = new URI("http://192.168.0.80/ApiRest/contactos.php");
                String[] parametros = {"idContacto", this.idContacto};
                URI uri = applyParameters(baseUri, parametros);
                // Create connection
                HttpURLConnection myConnection = (HttpURLConnection)
                        uri.toURL().openConnection();
                // Establecer método. Por defecto GET.
                myConnection.setRequestMethod("DELETE");
                if (myConnection.getResponseCode() == 200) {
                    // Success
                    Log.println(Log.ASSERT, "Resultado", "Registro borrado");
                    myConnection.disconnect();
                } else {
                    // Error handling code goes here
                    Log.println(Log.ASSERT, "Error", "Error");
                }
            } catch (Exception e) {
                Log.println(Log.ASSERT, "Excepción", e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(String mensaje) {
            Toast.makeText(MainActivity.this, "Actualizando datos...",
                    Toast.LENGTH_SHORT).show();
        }

        URI applyParameters(URI uri, String[] urlParameters) {
            StringBuilder query = new StringBuilder();
            boolean first = true;
            for (int i = 0; i < urlParameters.length; i += 2) {
                if (first) {
                    first = false;
                } else {
                    query.append("&");
                }
                try {
                    query.append(urlParameters[i]).append("=")
                            .append(URLEncoder.encode(urlParameters[i + 1], "UTF-8"));
                } catch (UnsupportedEncodingException ex) {
                    /* As URLEncoder are always correct, this exception
                     * should never be thrown. */
                    throw new RuntimeException(ex);
                }
            }
            try {
                return new URI(uri.getScheme(), uri.getAuthority(),
                        uri.getPath(), query.toString(), null);
            } catch (Exception ex) {
                /* As baseUri and query are correct, this exception
                 * should never be thrown. */
                throw new RuntimeException(ex);
            }
        }
    }

    private class ModificacionRemota extends AsyncTask<Void, Void, String> {
        // Atributos
        String idContacto;
        String nombreContacto;
        String apellidosContacto;
        String telefonoContacto;

        // Constructor
        public ModificacionRemota(String id, String nombre, String apellidos, String telefono) {
            this.idContacto = id;
            this.nombreContacto = nombre;
            this.apellidosContacto = apellidos;
            this.telefonoContacto = telefono;
        }

        // Inspectores
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this, "Modificando...",
                    Toast.LENGTH_SHORT).show();
        }

        protected String doInBackground(Void... voids) {
            try {
                StringBuilder response = new StringBuilder();
                Uri uri = new Uri.Builder()
                        .scheme("http")
                        .authority("192.168.0.80")
                        .path("/ApiRest/contactos.php")
                        .appendQueryParameter("idContacto", this.idContacto)
                        .appendQueryParameter("nombreContacto", this.nombreContacto)
                        .appendQueryParameter("apellidosContacto", this.apellidosContacto)
                        .appendQueryParameter("telefonoContacto", this.telefonoContacto)
                        .build();
                // Create connection
                URL url = new URL(uri.toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("PUT");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new
                            InputStreamReader(connection.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                } else {
                    response = new StringBuilder();
                }
                connection.getResponseCode();
                if (connection.getResponseCode() == 200) {
                    // Success
                    Log.println(Log.ASSERT, "Resultado", "Registro modificado:" + response);
                    connection.disconnect();
                } else {
                    // Error handling code goes here
                    Log.println(Log.ASSERT, "Error", "Error");
                }
            } catch (Exception e) {
                Log.println(Log.ASSERT, "Excepción", e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(String mensaje) {
            Toast.makeText(MainActivity.this, "Actualizando datos...",
                    Toast.LENGTH_SHORT).show();
        }
    }
}