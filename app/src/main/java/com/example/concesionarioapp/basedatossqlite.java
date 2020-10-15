package com.example.concesionarioapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class basedatossqlite extends SQLiteOpenHelper {

    String tblvehiculo = "Create table vehiculo (placa text primary key, marca text, modelo text, valor double)";

    public basedatossqlite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tblvehiculo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE vehiculo");
        db.execSQL(tblvehiculo);
    }
}