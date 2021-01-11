package com.coopera.mayakaab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.coopera.mayakaab.views.EnvioTamboresActivity;
import com.coopera.mayakaab.views.GaleriaActivity;
import com.coopera.mayakaab.views.GraficasActivity;
import com.coopera.mayakaab.views.LoginActivity;
import com.coopera.mayakaab.views.ProductoresActivity;
import com.coopera.mayakaab.views.TiposDeMielActivity;

public class MainActivity extends AppCompatActivity {

    ImageView imgvProductores;
    ImageView imgvRegistros;
    ImageView imgvCaja;
    ImageView imgvEnvios;
    ImageView imgvGraficas;
    ImageView imgvChat;
    ImageView imgvGaleria;
    ImageView imgvCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /// checar si el usuario ya tiene iniciado una sesion//
        if (checkUserLoggedBefore()){
        } else {
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_main);
         imgvProductores = findViewById(R.id.imgv_productores);
         imgvRegistros = findViewById(R.id.imgv_resgistros);
         imgvEnvios = findViewById(R.id.imgv_envios);
         imgvGaleria = findViewById(R.id.imgv_galeria);
         imgvCerrarSesion = findViewById(R.id.imgv_cerrar_sesion);
         imgvGraficas = findViewById(R.id.imgv_graficas);

         imgvProductores.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent productoresIntent = new Intent(MainActivity.this, ProductoresActivity.class);
                 startActivity(productoresIntent);
             }
         });

         imgvRegistros.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent mielIntent = new Intent(MainActivity.this, TiposDeMielActivity.class);
                 startActivity(mielIntent);
             }
         });

         imgvEnvios.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent enviosIntent = new Intent(MainActivity.this, EnvioTamboresActivity.class);
                 startActivity(enviosIntent);
             }
         });

        imgvGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galeriaIntent = new Intent(MainActivity.this, GaleriaActivity.class);
                startActivity(galeriaIntent);
            }
        });

        imgvGraficas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent graficasIntent = new Intent(MainActivity.this, GraficasActivity.class);
                startActivity(graficasIntent);
            }
        });

        imgvCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSessionVariables();
                Intent sesionIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(sesionIntent);
                finish();
            }
        });
    }
    // recuperar valor de inicio de sesion //
    private boolean checkUserLoggedBefore() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefsLogin",MODE_PRIVATE);
        Boolean userLoggedBefore = pref.getBoolean("userLogged",false);
        return  userLoggedBefore;
    }

    // LIMPIAR VARIABLE DE SESION //
    private void clearSessionVariables(){
        SharedPreferences preferences =getSharedPreferences("myPrefsLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}