package es.studium.libreriaokhttp;

import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ConsultaRemota
{
    JSONArray result;
    JSONObject jsonobject;
    String idContacto = "";
    String nombreContacto = "";
    String apellidosContacto = "";
    String telefonoContacto = "";
    // Crear una instancia de OkHttpClient
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
            .url("http://192.168.0.80/ApiRest/contactos.php")
            .build();
    ConsultaRemota(TextView mTextView)
    {
        try
        {
            // Realizar la solicitud
            Response response = client.newCall(request).execute();
            // Procesar la respuesta
            if (response.isSuccessful())
            {
                result = new JSONArray(response.body().string());
                jsonobject = result.getJSONObject(0);
                idContacto = jsonobject.getString("idContacto");
                nombreContacto = jsonobject.getString("nombreContacto");
                apellidosContacto = jsonobject.getString("apellidosContacto");
                telefonoContacto = jsonobject.getString("telefonoContacto");
                mTextView.setText("Nombre:" + nombreContacto + ", Apellidos:" + apellidosContacto);
            }
            else
            {
                Log.e("MainActivity", response.message());
            }
        }
        catch (IOException e)
        {
            Log.e("MainActivity", e.getMessage());
        }
        catch (JSONException e)
        {
            throw new RuntimeException(e);
        }
    }
}