package com.example.concesionarioapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class basedatossqlite extends SQLiteOpenHelper {

    String tbAutos ="Create Table Autos (numeroPlaca text primary key, modelo integer, marca text, valor real )";
    public basedatossqlite( Context context,  String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tbAutos);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE vehiculo");
        db.execSQL(tbAutos);

    }
}

