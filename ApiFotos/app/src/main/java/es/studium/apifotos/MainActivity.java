package es.studium.apifotos;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btnBuscar, btnSubir;
    ImageView iv;
    EditText et;
    Bitmap bitmap;
    String UPLOAD_URL = "http://192.168.0.80/ApiFotos/upload.php";
    String KEY_IMAGE = "foto";
    String KEY_NOMBRE = "nombre";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnSubir = findViewById(R.id.btnSubir);
        et = findViewById(R.id.editText);
        iv = findViewById(R.id.imageView);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        btnSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    public String getStringImagen(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public void uploadImage() {
        final ProgressDialog loading = ProgressDialog.show(this,
                "Subiendo...", "Espere por favor");
        StringRequest stringRequest = new
                StringRequest(Request.Method.POST, UPLOAD_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                loading.dismiss();
                                Toast.makeText(MainActivity.this, response,
                                        Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(MainActivity.this, error.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        String imagen = getStringImagen(bitmap);
                        String nombre = et.getText().toString().trim();
                        Map<String, String> params = new Hashtable<>();
                        params.put(KEY_IMAGE, imagen);
                        params.put(KEY_NOMBRE, nombre);
                        return params;
                    }
                };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launchSomeActivity.launch(intent);
    }

    ActivityResultLauncher<Intent> launchSomeActivity =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result ->
                    {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null && data.getData() != null) {
                                Uri selectedImageUri = data.getData();
                                try {
                                    bitmap =
                                            MediaStore.Images.Media.getBitmap(
                                                    this.getContentResolver(),
                                                    selectedImageUri);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                iv.setImageBitmap(bitmap);
                            }
                        }
                    });
}