package com.example.appmenus;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide bar with unnecessary system data
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==R.id.mOpen)
        {
            Toast.makeText(this,"Has seleccionado Abrir", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.mSave)
        {
            Toast.makeText(this,"Has seleccionado Guardar", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.mSettings)
        {
            Toast.makeText(this,"Has seleccionado Settings", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}