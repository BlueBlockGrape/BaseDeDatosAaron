package com.example.basededatospro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.basededatospro.Pojos.Contactos;
import com.example.basededatospro.Sqlite.OperacionesBaseDatos;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    OperacionesBaseDatos datos;
    ListView lv;
    int z=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datos = OperacionesBaseDatos.obtenerInstancia(getBaseContext());


        Cursor c = datos.obtenerContacto();
        List<Contactos> lst = new ArrayList<Contactos>();
        if(c.moveToFirst() != false){
            do {
                lst.add(new Contactos(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4)));
            } while (c.moveToNext());
        }

        lv = findViewById(R.id.lv);

        SimpleCursorAdapter adp =
                new SimpleCursorAdapter(
                        this,
                        android.R.layout.simple_list_item_2,
                        c,
                        new String[]{"Usiario","Email"},
                        new int[]{android.R.id.text1, android.R.id.text2
                        },
                        SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE

                );
        lv.setAdapter(adp);

    }

    // Metodo para el boton agregar
    public void Agregar(View view){
        Intent i = new Intent(this, Formulario.class);
        startActivityForResult(i,z);
    }
}
