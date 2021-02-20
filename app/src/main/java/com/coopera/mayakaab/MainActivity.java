package com.coopera.mayakaab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coopera.mayakaab.models.ComprasModel;
import com.coopera.mayakaab.models.Constants;
import com.coopera.mayakaab.views.AgregarEnviosTamboresActivity;
import com.coopera.mayakaab.views.CajaActivity;
import com.coopera.mayakaab.views.EnvioTamboresActivity;
import com.coopera.mayakaab.views.GaleriaActivity;
import com.coopera.mayakaab.views.GraficasActivity;
import com.coopera.mayakaab.views.LoginActivity;
import com.coopera.mayakaab.views.ProductoresActivity;
import com.coopera.mayakaab.views.TiposDeMielActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView imgvProductores;
    ImageView imgvRegistros;
    ImageView imgvCaja;
    ImageView imgvEnvios;
    ImageView imgvGraficas;
    ImageView imgvGaleria;
    ImageView imgvCerrarSesion;

    TextView cargo;
    TextView nombre;

    ProgressBar progressBar;
    LinearLayout linearLayout;

    ArrayList<ComprasModel> comprasList = new ArrayList<>();
    Gson gson = new Gson();

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
         imgvCaja = findViewById(R.id.imgv_caja);
         cargo = findViewById(R.id.cargo);
         nombre = findViewById(R.id.nombre);
         progressBar = findViewById(R.id.mainProgress);
         linearLayout = findViewById(R.id.body);
         linearLayout.setVisibility(View.INVISIBLE);

         setWelcome();

         imgvProductores.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent productoresIntent = new Intent(MainActivity.this, ProductoresActivity.class);
                 startActivity(productoresIntent);
             }
         });

        imgvCaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cahasIntent = new Intent(MainActivity.this, CajaActivity.class);
                startActivity(cahasIntent);
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
                String comprasObj;
                if(!comprasList.isEmpty()) {
                    comprasObj = gson.toJson(comprasList);
                } else {
                    comprasObj = "";
                }
                Intent intent = new Intent(MainActivity.this, GraficasActivity.class);
                intent.putExtra("compras",comprasObj);
                startActivity(intent);
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

        obtenerCompras();
    }

    private void obtenerCompras() {
        String urlGetVentas = Constants.URL_BASE + "miel.php?action=grafica";
        StringRequest request = new StringRequest(Request.Method.GET, urlGetVentas, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
                try {
                    JSONObject responseObj = new JSONObject(response);
                    JSONArray compras = responseObj.getJSONArray("grafica");

                    for(int i = 0; i < compras.length(); i++) {
                        JSONObject compraObj = compras.getJSONObject(i);
                        ComprasModel compraModel = gson.fromJson(compraObj.toString(), ComprasModel.class);
                        comprasList.add(compraModel);
                    }
                } catch (JSONException e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Compruba tu conexión a internet", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }


    private void setWelcome() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefsLogin",MODE_PRIVATE);
        String userName = pref.getString("name","");
        String userRole = pref.getString("role","");

        if(userRole.equals("1")){
            cargo.setText("Presidente");
        } else if (userRole.equals("2")) {
            cargo.setText("Secretario");
        } else {
            cargo.setText("Tesorero");
        }

        nombre.setText(userName);

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