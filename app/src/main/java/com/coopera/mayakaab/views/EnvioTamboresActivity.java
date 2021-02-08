package com.coopera.mayakaab.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coopera.mayakaab.R;
import com.coopera.mayakaab.controllers.ComprasMielListAdapter;
import com.coopera.mayakaab.controllers.EnviosTamboresListAdapter;
import com.coopera.mayakaab.models.ComprasMielModel;
import com.coopera.mayakaab.models.Constants;
import com.coopera.mayakaab.models.EnvioTamboresModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EnvioTamboresActivity extends AppCompatActivity {

    FloatingActionButton btnAgregarEnvio;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<EnvioTamboresModel> enviosList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio_tambores);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_tambores);
        btnAgregarEnvio = findViewById(R.id.fltbtn_agregar_envio_tambores);
        progressBar = findViewById(R.id.progressBarEnvios);

        btnAgregarEnvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnvioTamboresActivity.this, AgregarEnviosTamboresActivity.class);
                startActivity(intent);
            }
        });
    }

    private void obtenerEnvios() {
        progressBar.setVisibility(View.VISIBLE);
        String url = Constants.URL_BASE + "envios.php?action=show";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    progressBar.setVisibility(View.INVISIBLE);
                    JSONObject result = new JSONObject(response);
                    JSONArray listaEnvios = result.getJSONArray("miel-envios");

                    for (int i = 0; i < listaEnvios.length(); i++) {
                        JSONObject envioObj = listaEnvios.getJSONObject(i);

                        EnvioTamboresModel envio = convertJsonObToModel(envioObj);
                        enviosList.add(envio);
                    }

                    EnviosTamboresListAdapter mielAdapter = new EnviosTamboresListAdapter(EnvioTamboresActivity.this, enviosList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(EnvioTamboresActivity.this));
                    recyclerView.setAdapter(mielAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(EnvioTamboresActivity.this, "Compruba tu conexión a internet", Toast.LENGTH_SHORT).show();
            }

        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }

    private EnvioTamboresModel convertJsonObToModel(JSONObject envioObj) {
        EnvioTamboresModel envioModel;

        try {
            String idEnvio = envioObj.getString("id_envio");
            String numeroCosecha = envioObj.getString("numero_cosecha");
            String codigo = envioObj.getString("codigo_cosecha");
            String folio = envioObj.getString("num_folio_cosecha");
            String numeroTamboSubCentro = envioObj.getString("num_tambor_sub");
            String kgsBrutos = envioObj.getString("peso_bruto");
            String tara = envioObj.getString("peso_tara");
            String kgsNetos = envioObj.getString("peso_neto");
            String tamboTix = envioObj.getString("num_tambores_tix");
            String idUsuario = envioObj.getString("id_usuario");
            String fechaRegistro = envioObj.getString("fecha_registro");

            envioModel = new EnvioTamboresModel(idEnvio, numeroCosecha, codigo, folio, numeroTamboSubCentro, kgsBrutos, tara, kgsNetos, tamboTix, idUsuario, fechaRegistro);
            return envioModel;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        enviosList.clear();
        obtenerEnvios();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}