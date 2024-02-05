package es.studium.libreriaokhttp;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class AltaRemota
{
    OkHttpClient client = new OkHttpClient();

    public AltaRemota(String nombre, String apellidos, int telefono)
    {
        RequestBody formBody = new FormBody.Builder()
                .add("nombreContacto", nombre)
                .add("apellidosContacto", apellidos)
                .add("telefonoContacto", telefono+"")
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.56.1/ApiRest/contactos.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        try
        {
            Response response = call.execute();
            Log.i("MainActivity", String.valueOf(response));
        }
        catch (IOException e)
        {
            Log.e("MainActivity", e.getMessage());
        }
    }
}