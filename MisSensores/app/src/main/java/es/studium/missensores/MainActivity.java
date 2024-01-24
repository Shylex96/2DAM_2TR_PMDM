package es.studium.missensores;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView textViewProximidad = null;
    private TextView textViewTemperatura = null;
    private TextView textViewLuz = null;
    private TextView textViewAceleracion = null;
    private TextView textViewOrientacion = null;
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewProximidad = findViewById(R.id.sensorProximidad);
        textViewProximidad.setTextSize(30);
        textViewTemperatura = findViewById(R.id.sensorTemperatura);
        textViewTemperatura.setTextSize(30);
        textViewLuz = findViewById(R.id.sensorLuz);
        textViewLuz.setTextSize(30);
        textViewAceleracion = findViewById(R.id.sensorAceleracion);
        textViewAceleracion.setTextSize(30);
        textViewOrientacion = findViewById(R.id.sensorOrientacion);
        textViewOrientacion.setTextSize(30);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            Sensor sensorDeProximidad =
                    sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            Sensor sensorDeTemperatura =
                    sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            Sensor sensorDeLuz =
                    sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            Sensor sensorAcelerometro =
                    sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            Sensor sensorDeOrientacion =
                    sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
            if (sensorAcelerometro == null) {
                Toast.makeText(getApplicationContext(),
                        "No hay sensor de movimiento", Toast.LENGTH_SHORT).show();
            } else {
                sensorManager.registerListener(this,
                        sensorAcelerometro, SensorManager.SENSOR_DELAY_NORMAL);
            }
            if (sensorDeLuz == null) {
                Toast.makeText(getApplicationContext(),
                        "No hay sensor de luz", Toast.LENGTH_SHORT).show();
            } else {
                sensorManager.registerListener(this,
                        sensorDeLuz, SensorManager.SENSOR_DELAY_NORMAL);
            }
            if (sensorDeProximidad == null) {
                Toast.makeText(getApplicationContext(),
                        "No hay sensor de proximidad", Toast.LENGTH_SHORT).show();
            } else {
                sensorManager.registerListener(this,
                        sensorDeProximidad, SensorManager.SENSOR_DELAY_NORMAL);
            }
            if (sensorDeOrientacion == null) {
                Toast.makeText(getApplicationContext(),
                        "No hay sensor de orientacion", Toast.LENGTH_SHORT).show();
            } else {
                sensorManager.registerListener(this,
                        sensorDeOrientacion, SensorManager.SENSOR_DELAY_NORMAL);
            }
            if (sensorDeTemperatura == null) {
                Toast.makeText(getApplicationContext(),
                        "No hay sensor de temperatura", Toast.LENGTH_SHORT).show();
            } else {
                sensorManager.registerListener(this,
                        sensorDeTemperatura, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized (this) {
            float[] masData;
            float x, y, z;
            switch (event.sensor.getType()) {
                case Sensor.TYPE_PROXIMITY:
                    masData = event.values;
                    textViewProximidad.setText(String.format("Proximidad:%s cm", masData[0]));
                    break;
                case Sensor.TYPE_AMBIENT_TEMPERATURE:
                    masData = event.values;
                    textViewTemperatura.setText(String.format("Temperatura:%s ºC", masData[0]));
                    break;
                case Sensor.TYPE_LIGHT:
                    masData = event.values;
                    textViewLuz.setText(String.format("Luz:%s luxes", masData[0]));
                    break;
                case Sensor.TYPE_ACCELEROMETER:
                    masData = event.values;
                    x = masData[0];
                    y = masData[1];
                    z = masData[2];
                    textViewAceleracion.setText(String.format("Aceleración:\nx: %sm/s2" +
                            "\ny: %sm/s2\nz: %sm/s2", x, y, z));
                    break;
                case Sensor.TYPE_ORIENTATION:
                    masData = event.values;
                    x = masData[0];
                    y = masData[1];
                    textViewOrientacion.setText(String.format("Orientación:\nx: %s\ny: %s", x, y));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        super.onStop();
    }
}