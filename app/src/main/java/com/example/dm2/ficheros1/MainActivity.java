package com.example.dm2.ficheros1;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private TextView contenidoMostrar;
    private EditText contenidoEscrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contenidoMostrar = (TextView) findViewById(R.id.textViewContenido);
        contenidoEscrito = (EditText) findViewById(R.id.editTextContenido);
    }

    public void aniadirINT(View v) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("fichero_int.txt" , Context.MODE_APPEND));
            osw.write(contenidoEscrito.getText().toString() + "\n");
            osw.close();
        } catch
                (Exception e) {
            Log.e("Ficheros" , "ERROR!! al escribir fichero a memoria interna");
        }
    }

    public void leerINT(View v) {
        try {
            BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput("fichero_int.txt")));
            String texto = fin.readLine();
            contenidoMostrar.setText("");

            while (texto != null) {
                contenidoMostrar.setText(contenidoMostrar.getText().toString() + texto + "\n");
                texto = fin.readLine();
            }

            fin.close();


        } catch (Exception ex) {
            Log.e("Ficheros" , "Error al leer fichero desde memoria interna");
        }
    }

    public void borrarINT(View v) {
        File f = new File("data/data/com.example.dm2.ficheros1/files/fichero_int.txt");
        f.delete();
    }

    public void leerREC(View v) {
        try {
            InputStream fraw = getResources().openRawResource(R.raw.recurso);
            BufferedReader brin = new BufferedReader(new InputStreamReader(fraw));
            String linea = brin.readLine();
            contenidoMostrar.setText("");
            while (linea != null) {
                contenidoMostrar.setText(contenidoMostrar.getText().toString() +linea+"\n");
                linea = brin.readLine();
            }
            fraw.close();
        } catch (Exception ex) {
            Log.e("Ficheros" , "Error al leer fichero desde recurso raw");
        }

    }

    public void aniadirEXT(View v) {
        boolean sdDisponible = false;
        boolean sdAccesoEscritura = false;
//Comprobamos el estado de la memoria externa

        String estado = Environment.getExternalStorageState();
        Log.i("Memoria" , estado);

        if (estado.equals(Environment.MEDIA_MOUNTED)) {
            sdDisponible = true;
            sdAccesoEscritura = true;
        } else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            sdDisponible = true;
            sdAccesoEscritura = false;
        } else {
            sdDisponible = false;
            sdAccesoEscritura = false;
        }
        if (sdAccesoEscritura && sdDisponible) {
            try {
                File ruta_sd = Environment.getExternalStorageDirectory();
                File f = new File(ruta_sd.getAbsolutePath(), "fichero_sd.txt");
                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(f,true));
                osw.write(contenidoEscrito.getText().toString() + "\n");
                osw.close();
                Log.i("Ficheros" , "fichero escrito correctamente");
            } catch
                    (Exception ex) {
                Log.e("Ficheros" , "Error al escribir fichero en tarjeta SD");
            }
        }
    }

    public void leerEXT(View v) {
        boolean sdDisponible = false;
//Comprobamos el estado de la memoria externa

        String estado = Environment.getExternalStorageState();
        Log.i("Memoria" , estado);

        if (estado.equals(Environment.MEDIA_MOUNTED)) {
            sdDisponible = true;
        } else {
            sdDisponible = false;
        }
        if (sdDisponible) {
            try {
                File ruta_sd = Environment.getExternalStorageDirectory();
                File f = new File(ruta_sd.getAbsolutePath(), "fichero_sd.txt");
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                String linea = br.readLine();
                contenidoMostrar.setText("");
                while (linea != null) {
                    contenidoMostrar.setText(contenidoMostrar.getText().toString() +linea+"\n");
                    linea = br.readLine();
                }
                br.close();
                Log.i("Ficheros" , linea);
            } catch (Exception ex) {
                Log.e("Ficheros" , "ERROR!! en la lectura del fichero en SD");
            }

        }
    }
    public void borrarEXT(View v) {

        {
            boolean sdDisponible = false;
            boolean sdAccesoEscritura = false;
//Comprobamos el estado de la memoria externa

            String estado = Environment.getExternalStorageState();
            Log.i("Memoria", estado);

            if (estado.equals(Environment.MEDIA_MOUNTED)) {
                sdDisponible = true;
                sdAccesoEscritura = true;
            } else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
                sdDisponible = true;
                sdAccesoEscritura = false;
            } else {
                sdDisponible = false;
                sdAccesoEscritura = false;
            }
            //Toast.makeText(this,);
            if (sdAccesoEscritura && sdDisponible) {
                try {
                    File ruta_sd = Environment.getExternalStorageDirectory();
                    File f = new File(ruta_sd.getAbsolutePath(), "fichero_sd.txt");
                    f.delete();
                } catch
                        (Exception ex) {
                    Log.e("Ficheros", "Error al escribir fichero en tarjeta SD");
                }
            }
        }

    }

}
