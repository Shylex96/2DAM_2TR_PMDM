package es.studium.libreriaokhttp;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BorradoRemoto
{
    OkHttpClient client = new OkHttpClient();
    public BorradoRemoto(int id)
    {
        Request request = new Request.Builder()
                .url("http://192.168.56.1/ApiRest/contactos.php?idContacto="+id)
                .delete()
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