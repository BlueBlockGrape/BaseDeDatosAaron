package com.example.basededatospro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.basededatospro.Pojos.Contactos;
import com.example.basededatospro.Sqlite.OperacionesBaseDatos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formulario extends AppCompatActivity {
    private EditText txtID;
    private EditText txtUsuario;
    private EditText txtEmail;
    private EditText txtTelefono;
    private EditText txtFecha;

    OperacionesBaseDatos datos;

    int z=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        txtID = (EditText)findViewById(R.id.txt_id);
        txtUsuario = (EditText)findViewById(R.id.txt_usuario);
        txtEmail = (EditText)findViewById(R.id.txt_email);
        txtTelefono = (EditText)findViewById(R.id.txt_telefono);
        txtFecha = (EditText)findViewById(R.id.txt_Fecha);

        datos = OperacionesBaseDatos.obtenerInstancia(getBaseContext());

    }


    // Metodo para el boton agregar
    public void Agregar1(View view){

        datos.insertarContacto(new Contactos(0,txtUsuario.getText().toString(),
                txtEmail.getText().toString(),txtTelefono.getText().toString(),
                txtFecha.getText().toString()));

        limpiar();
    }

    public void limpiar(){
        txtID.setText("");
        txtUsuario.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtFecha.setText("");
    }

    // Metodo para el boton Buscar
    public void Buscar(View view){

        Cursor c = datos.obtenerContacto(txtID.getText().toString());
        c.moveToFirst();
        if (c != null) {
            txtID.setText(c.getString(0));
            txtUsuario.setText(c.getString(1));
            txtEmail.setText(c.getString(2));
            txtTelefono.setText(c.getString(3));
            txtFecha.setText(c.getString(4));
        }else{
            txtUsuario.setText("No hay nada");
        }


    }

    public void Eliminar(View view){
        datos.eliminarContacto(txtID.getText().toString());
    }





    // Metodo para el boton agregar
    public void Regresar(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivityForResult(i,z);
    }


    // Metodo para el boton modificar
    public void Modificar(View view){

        datos.actualizarContacto(new Contactos(Integer.parseInt(txtID.getText().toString()),txtUsuario.getText().toString(),
                txtEmail.getText().toString(),txtTelefono.getText().toString(),
                txtFecha.getText().toString()));

        limpiar();

    }


}
