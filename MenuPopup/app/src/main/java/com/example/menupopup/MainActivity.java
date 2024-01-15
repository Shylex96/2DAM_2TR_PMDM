package com.example.menupopup;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menupopup.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        PopupMenu.OnMenuItemClickListener
{
    TextView texto;
    PopupMenu popupMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        texto = findViewById(R.id.texto);
        texto.setOnClickListener(this);
        popupMenu = new PopupMenu(this, texto);
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
    }
    @Override
    public void onClick(View view)
    {
        popupMenu.show();
    }
    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        if(item.getItemId() == R.id.mFile)
        {
            Toast.makeText(this, R.string.mensajeFile, Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId() == R.id.mSettings)
        {
            Toast.makeText(this, R.string.mensajeSettings, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}