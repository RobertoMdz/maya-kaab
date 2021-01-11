package com.coopera.mayakaab.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coopera.mayakaab.R;
import com.coopera.mayakaab.adapters.EnviosTamboresListAdapter;
import com.coopera.mayakaab.models.EnvioTamboresModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class EnvioTamboresActivity extends AppCompatActivity {

    FloatingActionButton btnAgregarEnvio;
    RecyclerView recyclerView;
    ArrayList<EnvioTamboresModel> listItmes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio_tambores);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_tambores);
        btnAgregarEnvio = findViewById(R.id.fltbtn_agregar_envio_tambores);

        for (int i = 0; i < 10; i++) {
            listItmes.add(new EnvioTamboresModel("12/octubre/2020", "00012", "123","12", "100 kg", "tara", "120 kgs", "23"));
        }

        EnviosTamboresListAdapter listAdapter =  new EnviosTamboresListAdapter(EnvioTamboresActivity.this, listItmes);
        recyclerView.setLayoutManager(new LinearLayoutManager(EnvioTamboresActivity.this));
        recyclerView.setAdapter(listAdapter);


        btnAgregarEnvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnvioTamboresActivity.this, AgregarEnviosTamboresActivity.class);
                startActivity(intent);
            }
        });
    }

    private void obtenerEnvios() {
        String urlGetEnvios = "";
        onBackPressed();

        StringRequest request = new StringRequest(Request.Method.GET, urlGetEnvios, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                String respuestaLogin = response;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EnvioTamboresActivity.this, "Compruba tu conexión a internet", Toast.LENGTH_SHORT).show();
            }

        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}