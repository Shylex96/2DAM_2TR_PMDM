package es.studium.libreriaokhttp;

import android.util.Log;

import java.io.IOException;
import java.net.URI;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ModificacionRemota
{
    OkHttpClient client = new OkHttpClient();
    public ModificacionRemota(int id, String nombre, String apellidos, int telefono)
    {
        HttpUrl.Builder queryUrlBuilder = HttpUrl.get(URI.create("http://192.168.56.1/ApiRest/contactos.php")).newBuilder();
        queryUrlBuilder.addQueryParameter("idContacto", id+"");
        queryUrlBuilder.addQueryParameter("nombreContacto", nombre);
        queryUrlBuilder.addQueryParameter("apellidosContacto", apellidos);
        queryUrlBuilder.addQueryParameter("telefonoContacto", telefono+"");

        // Las peticiones PUT requieren BODY, aunque sea vacío
        RequestBody formBody = new FormBody.Builder()
                .build();
        Log.i("ModificacionRemota", formBody.toString());
        Request request = new Request.Builder()
                .url(queryUrlBuilder.build())
                .put(formBody)
                .build();
        Log.i("ModificacionRemota", String.valueOf(request));
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