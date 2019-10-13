package com.example.basededatospro.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.basededatospro.Pojos.Contactos;
import com.example.basededatospro.Sqlite.BaseDatosContacto.Tablas;
import com.example.basededatospro.Sqlite.MisContactos.ColumnasContacto;

public final class OperacionesBaseDatos {
    private static BaseDatosContacto baseDatos;
    private static OperacionesBaseDatos instancia = new OperacionesBaseDatos();

    private OperacionesBaseDatos() {
    }

    public static OperacionesBaseDatos obtenerInstancia(Context contexto) {
        if (baseDatos == null) {
            baseDatos = new BaseDatosContacto(contexto);
        }
        return instancia;
    }


    public boolean insertarContacto(Contactos contacto) {

        try {
            SQLiteDatabase db = baseDatos.getWritableDatabase();

            ContentValues valores = new ContentValues();
            valores.put(ColumnasContacto.USUARIO, contacto.getUsuario());
            valores.put(ColumnasContacto.EMAIL,contacto.getEmail());
            valores.put(ColumnasContacto.TELEFONO,contacto.getTel());
            valores.put(ColumnasContacto.FECHA_NACIMIENTO,contacto.getFecNac());

            db.insertOrThrow(Tablas.CONTACTO, null, valores);
            return true;
        }catch (SQLiteAbortException e){
            Log.d("Error", "No se pudo insertar");
            return false;
        }
    }


    public boolean actualizarContacto(Contactos contacto) {
        try {
            SQLiteDatabase db = baseDatos.getWritableDatabase();

            ContentValues valores = new ContentValues();
            valores.put(ColumnasContacto.USUARIO, contacto.getUsuario());
            valores.put(ColumnasContacto.EMAIL,contacto.getEmail());
            valores.put(ColumnasContacto.TELEFONO,contacto.getTel());
            valores.put(ColumnasContacto.FECHA_NACIMIENTO,contacto.getFecNac());

            String whereClause = String.format("%s = ?", ColumnasContacto.ID);
            String[] whereArgs = {(contacto.getId()+"")};

            int resultado = db.update(Tablas.CONTACTO, valores, whereClause, whereArgs);

            return resultado > 0;
        }catch (SQLiteAbortException e){
            Log.d("Error", "No se pudo actualizar");
            return false;
        }
    }


    public boolean eliminarContacto(String id) {
        try{
            SQLiteDatabase db = baseDatos.getWritableDatabase();

            String whereClause = ColumnasContacto.ID + "=?";
            String[] whereArgs = {id};

            int resultado = db.delete(Tablas.CONTACTO, whereClause, whereArgs);

            return resultado > 0;
        }catch (SQLiteAbortException e){
            Log.d("Error", "No se pudo eliminar");
            return false;
        }
    }


    public Cursor obtenerContacto() {
        try {
            SQLiteDatabase db = baseDatos.getReadableDatabase();

            String sql = String.format("SELECT * FROM %s", Tablas.CONTACTO);

            return db.rawQuery(sql, null);
        }catch (SQLiteAbortException e){
            Log.d("Error", "No se pudo obtener los registros");
            return null;
        }
    }

    public Cursor obtenerContacto(String id) {
        try {
            SQLiteDatabase db = baseDatos.getReadableDatabase();

            String sql = String.format("SELECT * FROM %s WHERE %s=?",
                    Tablas.CONTACTO, ColumnasContacto.ID);

            String[] selectionArgs = {id};

            return db.rawQuery(sql, selectionArgs);
        }catch (SQLiteAbortException e){
            Log.d("Error", "No se pudo obtener los registros");
            Toast.makeText(null,"No encontro nada",Toast.LENGTH_SHORT);
            return null;
        }
    }


    public SQLiteDatabase getDb() {
        return baseDatos.getWritableDatabase();
    }

}
