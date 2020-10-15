package com.example.concesionarioapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity
{
    EditText placau, marcau, modelou, valoru;
    Button agregaru, listaru, buscaru, actualizaru, eliminaru;
    String placanueva, placaanterior;


    basedatossqlite osql = new basedatossqlite(this, "bdvehiculos", null,1 );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        placau = findViewById(R.id.etplaca);
        marcau = findViewById(R.id.etmarca);
        modelou = findViewById(R.id.etmodelo);
        valoru = findViewById(R.id.etvalor);
        agregaru = findViewById(R.id.btnagregar);
        listaru = findViewById(R.id.btnlistar);
        buscaru = findViewById(R.id.btnbuscaru);
        actualizaru = findViewById(R.id.btnactualizaru);
        eliminaru = findViewById(R.id.btneliminaru);


        agregaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!placau.getText().toString().isEmpty() && !marcau.getText().toString().isEmpty() && !modelou.getText().toString().isEmpty() && !valoru.getText().toString().isEmpty()) {
                    SQLiteDatabase db = osql.getReadableDatabase();
                    String sql = "Select placa from vehiculo where placa = '" + placau.getText().toString() + "'";
                    Cursor cplaca = db.rawQuery(sql, null);
                    if (cplaca.moveToFirst()) {
                        Toast.makeText(getApplicationContext(), "Error! Ya existe la placa en la base de datos", Toast.LENGTH_SHORT).show();
                    } else {
                        SQLiteDatabase db1 = osql.getWritableDatabase();
                        try {

                            ContentValues contplaca = new ContentValues();
                            contplaca.put("placa", placau.getText().toString().trim());
                            contplaca.put("marca", marcau.getText().toString().trim());
                            contplaca.put("modelo", modelou.getText().toString().trim());
                            contplaca.put("valor", valoru.getText().toString().trim());
                            db1.insert("vehiculo", null, contplaca);
                            db1.close();
                            Toast.makeText(getApplicationContext(), "El vehiculo con placa " + placau.getText() + " a sido almacenado correctamente", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Por favor diligenciar todos los campos", Toast.LENGTH_SHORT).show();

                }
            }
        });

        listaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Listado_vehiculos.class));

            }

        });

        buscaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!placau.getText().toString().isEmpty()) {
                    buscarplaca(placau.getText().toString().trim());
                } else {
                    Toast.makeText(getApplicationContext(), "Por favor diligenciar el campo de la placa", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // boton actualizar
        actualizaru.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                if (!placau.getText().toString().isEmpty() && !marcau.getText().toString().isEmpty() && !modelou.getText().toString().isEmpty() && !valoru.getText().toString().isEmpty())
                {

                    actualizarplaca();  // se crea metodo
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Por favor diligenciar todos los campos", Toast.LENGTH_SHORT).show();


                }
            }
        });

        eliminaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!placau.getText().toString().isEmpty() && !marcau.getText().toString().isEmpty() && !modelou.getText().toString().isEmpty() && !valoru.getText().toString().isEmpty())
                {
                    metodoeliminar();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Por favor diligenciar todos los campos", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    // se crea metodo agregar placa

    private void agregarplaca ()
    {
        if (!placau.getText().toString().isEmpty() && !marcau.getText().toString().isEmpty() && !modelou.getText().toString().isEmpty() && !valoru.getText().toString().isEmpty()) {
            SQLiteDatabase db = osql.getReadableDatabase();
            String sql = "Select placa from vehiculo where placa = '" + placau.getText().toString() + "'";
            Cursor cplaca = db.rawQuery(sql, null);
            if (cplaca.moveToFirst()) {
                Toast.makeText(getApplicationContext(), "Error! Ya existe la placa en la base de datos", Toast.LENGTH_SHORT).show();
            } else {
                SQLiteDatabase db1 = osql.getWritableDatabase();
                try {

                    ContentValues contplaca = new ContentValues();
                    contplaca.put("placa", placau.getText().toString().trim());
                    contplaca.put("marca", marcau.getText().toString().trim());
                    contplaca.put("modelo", modelou.getText().toString().trim());
                    contplaca.put("valor", valoru.getText().toString().trim());
                    db1.insert("vehiculo", null, contplaca);
                    db1.close();
                    Toast.makeText(getApplicationContext(), "El vehiculo con placa " + placau.getText() + " a sido almacenado correctamente", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Por favor diligenciar todos los campos", Toast.LENGTH_SHORT).show();

        }

    }
    // se crea metodo buscarplaca
    private void buscarplaca (String placabuscar)
    {
        if (!placabuscar.isEmpty()) {
            SQLiteDatabase bd = osql.getWritableDatabase();
            String sql = "Select placa, marca, modelo, valor FROM vehiculo WHERE placa ='" + placabuscar + "'";
            Cursor cusuari = bd.rawQuery(sql, null);
            if (cusuari.moveToFirst()) {

                DecimalFormat nro = new DecimalFormat("###,###.##");
                marcau.setText(cusuari.getString(1));
                modelou.setText(cusuari.getString(2));
                valoru.setText(nro.format(cusuari.getInt(3)));
                placaanterior = cusuari.getString(0);

            } else {
                Toast.makeText(getApplicationContext(), "Error!, la placa " + placabuscar + " no existe", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Ingrese por favor una placa a buscar", Toast.LENGTH_SHORT).show();
        }
    }

    //metodo listar
    private void listar ()
    {
        startActivity(new Intent(getApplicationContext(), Listado_vehiculos.class));
    }

    // metodo actualizar
    private void actualizarplaca (){


        placanueva = placau.getText().toString().trim(); // se crea variable para guardar lo que se traiga de emailact que a su vez toma lo traido en emailu.getText().toString()

        // se llaman las bases de datos
        SQLiteDatabase obde = osql.getWritableDatabase();
        SQLiteDatabase bd = osql.getReadableDatabase();

        if (placanueva.equals(placaanterior.trim())) // se comprara email nuevo con email viejo
        {
            obde.execSQL("UPDATE vehiculo SET marca = '" + marcau.getText().toString().trim() + "', modelo = '" + modelou.getText().toString().trim() + "', valor = '" + valoru.getText().toString().trim() + "' Where placa = '" + placanueva + "'"); // si son iguales, cambia los datos sin el email

            Toast.makeText(getApplicationContext(), " Vehiculo actualizado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            // buscar email nuevo para verificar que no este asignado a otro usuario
            String sql = "Select placa, marca, modelo, valor From vehiculo Where placa = '" + placanueva + "'";  // si emailnuevo e email viejo son diferentes compra email nuevo
            Cursor cusuari = bd.rawQuery(sql, null); // guarda los datos en tabla cursor

            if (cusuari.moveToFirst()) // si encontro el emailnuevo lanza mensaje
            {
                Toast.makeText(getApplicationContext(), "la placa ha sido asignada a otro vehiculo", Toast.LENGTH_SHORT).show();
            } else {
                // este es el comando update para modificar los datos en la tabla incluyendo el email

                obde.execSQL("UPDATE vehiculo SET placa = '" + placanueva + "', marca = '" + marcau.getText().toString().trim() + "', modelo = '" + modelou.getText().toString().trim() + "', valor = '" + valoru.getText().toString().trim() + "' Where placa = '" + placaanterior + "'"); // si no encuenta emailnuevo  actualzia todo

                Toast.makeText(getApplicationContext(), "Vehiculo actualizado correctamente", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //metodo eliminar
    private void metodoeliminar ()
    {
        AlertDialog.Builder alertacuadro = new AlertDialog.Builder(this);
        alertacuadro.setMessage("Eliminara el vehiculo");
        alertacuadro.setPositiveButton("Sí", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                SQLiteDatabase obde = osql.getWritableDatabase();

                obde.execSQL("DELETE FROM vehiculo WHERE placa = '" + placau.getText().toString() + "'");

                placau.setText("");
                marcau.setText("");
                modelou.setText("");
                valoru.setText("");

                Toast.makeText(getApplicationContext(), "Vehiculo Eliminado Correctamente", Toast.LENGTH_SHORT).show();
            }
        });

        alertacuadro.setNegativeButton("No",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertacuadro.create();
        alertDialog.show();
    }


    //pasos para el menu
    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        MenuInflater infcrud = getMenuInflater();
        infcrud.inflate(R.menu.menu_crud, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuagregar:
                agregarplaca();
                return true;
            case R.id.menubuscar:
                buscarplaca(placau.getText().toString().trim());
                return true;
            case R.id.menuactualizar:
                actualizarplaca();
                return true;
            case R.id.menueliminar:
                metodoeliminar();
                return true;
            case R.id.menulistar:
                listar();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}