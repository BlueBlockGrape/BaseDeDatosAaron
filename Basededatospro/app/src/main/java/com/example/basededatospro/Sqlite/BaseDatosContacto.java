package com.example.basededatospro.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

public class BaseDatosContacto extends SQLiteOpenHelper {
    //Nombre de la base de datos
    private static final String NOMBRE_BASE_DATOS = "Contactos.db";
    //Version de la base de datos
    private static final int VERSION_ACTUAL = 1;
    //contexto de la aplicacion para el acceso a los datos
    private final Context contexto;
    //interface con los nombres de las tablas
    interface  Tablas {
        String CONTACTO = "contacto";
    }

    public BaseDatosContacto(Context context){
        super(context, NOMBRE_BASE_DATOS, null, VERSION_ACTUAL);
        this.contexto = context;
    }
    //Sobre escribimls el metodo open para activar las referencias a las llaves foraneas
    @Override
    public  void onOpen(SQLiteDatabase db){
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }
    //Sobre escribimos el metodo oncreate para crear las tablas de la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT NOT NULL,%s TEXT NOT NULL,%s TEXT NOT NULL,%s DATETIME NOT NULL)",
                Tablas.CONTACTO, MisContactos.ColumnasContacto.ID,MisContactos.ColumnasContacto.USUARIO,
                MisContactos.ColumnasContacto.EMAIL,MisContactos.ColumnasContacto.TELEFONO,
                MisContactos.ColumnasContacto.FECHA_NACIMIENTO));
    }
    //Sobre escribimos el metodo onupgrate para modificaciones de la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.CONTACTO);

        onCreate(db);
    }
}