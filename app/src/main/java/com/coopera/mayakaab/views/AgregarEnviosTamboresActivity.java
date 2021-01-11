package com.coopera.mayakaab.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coopera.mayakaab.MainActivity;
import com.coopera.mayakaab.R;
import com.coopera.mayakaab.models.EnvioTamboresModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AgregarEnviosTamboresActivity extends AppCompatActivity {

    Button btnAgregar;
    EditText edtxNumCosecha, edtxCodigo, edtxFolio, edtxNumTambor, edtxKgsBruto, edtxTara, edtxKgsNetos, edtxNumeroTamborTix;
    String cosecha, codigo, folio, tambor, kgsBrutos, tara, kgsNetos, numTamborTix;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_envios_tambores);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtxNumCosecha = findViewById(R.id.edtx_numero_cosecha);
        edtxCodigo = findViewById(R.id.edtx_codigo);
        edtxFolio = findViewById(R.id.edtx_numero_folio);
        edtxNumTambor = findViewById(R.id.edtx_numero_tambor);
        edtxKgsBruto = findViewById(R.id.edtx_kgs_bruto);
        edtxTara = findViewById(R.id.edtx_tara);
        edtxKgsNetos = findViewById(R.id.edtx_kgs_netos);
        edtxNumeroTamborTix = findViewById(R.id.edtx_numero_tambortix);

        btnAgregar = findViewById(R.id.btn_agregar_envio);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                validarInputsForm();
            }
        });

    }

    private void validarInputsForm() {
        cosecha = edtxNumCosecha.getText().toString().trim();
        codigo = edtxCodigo.getText().toString().trim();
        folio = edtxFolio.getText().toString().trim();
        tambor = edtxNumTambor.getText().toString().trim();
        kgsBrutos = edtxKgsBruto.getText().toString().trim();
        tara = edtxTara.getText().toString().trim();
        kgsNetos = edtxKgsNetos.getText().toString().trim();
        numTamborTix = edtxNumeroTamborTix.getText().toString().trim();

        if (cosecha.isEmpty() || codigo.isEmpty() || folio.isEmpty() || tambor.isEmpty() || kgsBrutos.isEmpty() || tara.isEmpty() || kgsNetos.isEmpty() || numTamborTix.isEmpty()) {
            Toast.makeText(AgregarEnviosTamboresActivity.this, "Todos los campos son requeridos", Toast.LENGTH_LONG).show();
        } {
            guaradarEnvio();
        }
    }

    private void guaradarEnvio(){
        String urlGuardarEnvio = "";

        Intent intent = new Intent(AgregarEnviosTamboresActivity.this, EnvioTamboresActivity.class);
        startActivity(intent);
        finish();

        StringRequest request = new StringRequest(Request.Method.POST, urlGuardarEnvio, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                String respuestaLogin = response;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AgregarEnviosTamboresActivity.this, "Compruba tu conexión a internet", Toast.LENGTH_SHORT).show();
            }

        }) {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                Map <String,String> dataEnvio= new HashMap<>();

                /*dataLogin.put("username",correo);
                dataLogin.put("password",pass);*/
                return dataEnvio;
            }
        };

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