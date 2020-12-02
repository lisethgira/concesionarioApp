package com.example.concesionarioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Listado_vehiculos extends AppCompatActivity {
        ListView listado;
        basedatossqlite administrador = new basedatossqlite(this,"concesionario", null,1);
        ArrayList<String> listaAutos;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_listado_vehiculos);
            listado = findViewById(R.id.lvplaca);
            cargarAutos();
        }

        private void cargarAutos(){
            listaAutos = listadoAutos();
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, listaAutos);
            listado.setAdapter(adaptador);
        }

        private ArrayList<String> listadoAutos() {
            ArrayList<String> datos = new ArrayList<String>();
            SQLiteDatabase db = administrador.getReadableDatabase();
            String query = "SELECT numeroPlaca, modelo, marca, valor FROM Autos";
            Cursor tabla = db.rawQuery(query, null);
            DecimalFormat decimales = new DecimalFormat("###,###,###.##");
            if (tabla.moveToFirst()){
                do {
                    datos.add(tabla.getString(0)+" "+ tabla.getString(1)+" "+tabla.getString(2)+" "+decimales.format(Double.parseDouble(tabla.getString(3))));

                }while (tabla.moveToNext());
            }

            return datos;
        }

    }
