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

public class MainActivity extends AppCompatActivity {
    EditText placa, modelo, marca, valor;
    Button insertar, buscar, actualizar, eliminar, listar;
    basedatossqlite administrador = new basedatossqlite(this,"concesionario", null,1);
    String placaAnterior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        placa =findViewById(R.id.etplaca);
        modelo = findViewById(R.id.etmodelo);
        marca = findViewById(R.id.etmarca);
        valor = findViewById(R.id.etvalor);
        insertar = findViewById(R.id.btnagregar);
        buscar = findViewById(R.id.btnbuscaru);
        actualizar = findViewById(R.id.btnactualizaru);
        eliminar = findViewById(R.id.btneliminaru);
        listar = findViewById(R.id.btnlistar);

        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarAutos();

            }
        });
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarAutos();
            }
        });

        listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Listado_vehiculos.class));
            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarAutos();
            }


        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarAutos();
            }
        });



    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater refemenu = getMenuInflater();
        refemenu.inflate(R.menu.menu_crud,menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuagregar:
                insertarAutos();
                return true;
            case R.id.menubuscar:
                buscarAutos();
                return true;
            case R.id.menuactualizar:
                actualizarAutos();
                return true;
            case R.id.menueliminar:
                eliminarAutos();
                return true;
            case R.id.menulistar:
                startActivity(new Intent(getApplicationContext(), Listado_vehiculos.class));

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void eliminarAutos() {
        AlertDialog.Builder alertacuadro = new AlertDialog.Builder(MainActivity.this);
        alertacuadro.setMessage("Eliminar Auto");
        alertacuadro.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase bd1 = administrador.getWritableDatabase();
                bd1.execSQL("DELETE FROM Autos WHERE numeroPlaca = '"+placa.getText().toString().trim()+"'");

                placa.setText("");
                modelo.setText("");
                marca.setText("");
                valor.setText("");

                Toast.makeText(getApplicationContext(),"Auto eliminado Correctamente",Toast.LENGTH_SHORT).show();
            }
        });
        alertacuadro.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alertDialog = alertacuadro.create();
        alertDialog.show();
    }

    private boolean validarCampos(String mensaje, EditText campoValidar){
        if (campoValidar.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();
            return true;
        }else{
            return false;
        }


    }

    private void buscarAutos(){
        if (validarCampos("Debes ingresar el Número de Placa",placa)){
            return;
        }
        try {
            SQLiteDatabase db = administrador.getReadableDatabase();
            String query = "SELECT numeroPlaca, modelo, marca, valor FROM Autos WHERE numeroPlaca ='"+placa.getText().toString().trim()+"'";
            Cursor tabla = db.rawQuery(query,null);
            if (tabla.moveToFirst()){
                placa.setText(tabla.getString(0));
                placaAnterior= tabla.getString(0);
                modelo.setText(tabla.getString(1));
                marca.setText(tabla.getString(2));
                valor.setText(tabla.getString(3));

                Toast.makeText(getApplicationContext(), "Vehículo encontrado",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "No se encontró Vehículo", Toast.LENGTH_SHORT).show();

                marca.setText("");
                valor.setText("");
                modelo.setText("");
            }
            db.close();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error en: "+ e.getMessage(),Toast.LENGTH_SHORT).show();

        }

    }
    private void insertarAutos(){

        if (validarCampos("Debes ingresar el Número de Placa",placa)){
            return;
        }

        if (validarCampos("Debes ingresar la Marca del Auto",marca)){
            return;
        }
        if (validarCampos("Debes ingresar el Modelo del Auto",modelo)){
            return;
        }
        if (validarCampos("Debes ingresar el valor del Auto",valor)){
            return;
        }

        try {
            SQLiteDatabase db = administrador.getReadableDatabase();
            String query = "SELECT numeroPlaca FROM Autos WHERE numeroPlaca = '"+placa.getText().toString().trim()+"'";
            Cursor tabla = db.rawQuery(query, null);
            if (tabla.moveToFirst()){
                Toast.makeText(getApplicationContext(), "Número de Placa ya existe",Toast.LENGTH_SHORT).show();
            }else {
                db =administrador.getWritableDatabase();
                ContentValues contenidoAInsertar = new ContentValues();
                contenidoAInsertar.put("numeroPlaca",placa.getText().toString().trim());
                contenidoAInsertar.put("modelo",modelo.getText().toString().trim());
                contenidoAInsertar.put("marca", marca.getText().toString().trim());
                contenidoAInsertar.put("valor",valor.getText().toString().trim());
                db.insert("Autos",null,contenidoAInsertar);
                db.close();
                Toast.makeText(getApplicationContext(),"El Auto se ha agregado correctamente",Toast.LENGTH_SHORT).show();

                placa.setText("");
                modelo.setText("");
                marca.setText("");
                valor.setText("");
            }


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error en: "+ e.getMessage(),Toast.LENGTH_SHORT).show();

        }

    }
    private void actualizarAutos() {
        if (validarCampos("Debes digitar la placa",placa)){
            return;
        }
        if (validarCampos("Debes ingresar la marca",marca)){
            return;
        }
        if (validarCampos("Debes ingresar el modelo del Auto",modelo)){
            return;
        }
        if (validarCampos("Debes ingresar un valor",valor)){
            return;
        }
        try {
            if (!placa.getText().toString().trim().equals(placaAnterior)){
                SQLiteDatabase db = administrador.getWritableDatabase();
                String query ="SELECT numeroPlaca FROM Autos WHERE numeroPlaca ='" +placa.getText().toString().trim() +"'";
                Cursor contenidoRecibido = db.rawQuery(query,null);
                DecimalFormat decimales = new DecimalFormat("###,###,###.##");
                if(contenidoRecibido.moveToFirst()){
                    Toast.makeText(getApplicationContext(),"El Auto ya existe",Toast.LENGTH_SHORT).show();
                    db.close();
                    return;
                }
            }
            SQLiteDatabase db = administrador.getWritableDatabase();
            ContentValues aInsertar = new ContentValues();
            aInsertar.put("numeroPlaca",placa.getText().toString().trim());
            aInsertar.put("modelo",modelo.getText().toString().trim());
            aInsertar.put("marca",marca.getText().toString().trim());
            aInsertar.put("valor",valor.getText().toString().trim());

            db.update("Autos", aInsertar,"numeroPlaca= '"+ placaAnterior+"'",null);
            Toast.makeText(getApplicationContext(),"Auto Actualizado correctamente",Toast.LENGTH_SHORT).show();


            db.close();



        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error"+e.getMessage(),Toast.LENGTH_SHORT).show();

        }



    }
}