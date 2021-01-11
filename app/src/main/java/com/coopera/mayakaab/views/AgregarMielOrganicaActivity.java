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
import com.coopera.mayakaab.R;

import java.util.HashMap;
import java.util.Map;

public class AgregarMielOrganicaActivity extends AppCompatActivity {

    Button agregarMiel;
    EditText edtxCodigo, edtxFolio, edtxFecha, edtxProductor, edtxHumedad, edtxPrecioBruto,
            edtxTara, edtxPesoNeto, edxtPrecioUnitario, edtxTotal, edtxTambores;
    String varCodigo, varFolio, varFecha, varProductor, varHumedad, varPrecioBruto,
            varTara, varPesoNeto, varPrecioUnitario, varTotal, varTambores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_miel_organica);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        agregarMiel = findViewById(R.id.btn_guardar_miel_organica);
        edtxCodigo = findViewById(R.id.edtx_codigoo);
        edtxFolio = findViewById(R.id.edtx_numero_folioo);
        edtxFecha = findViewById(R.id.edtx_fechaa);
        edtxProductor = findViewById(R.id.edtx_nombre_productorr);
        edtxHumedad = findViewById(R.id.edtx_humedadd);
        edtxPrecioBruto = findViewById(R.id.edtx_peso_bruto);
        edtxTara = findViewById(R.id.edtx_taraa);
        edtxPesoNeto = findViewById(R.id.edtx_peso_netoo);
        edxtPrecioUnitario = findViewById(R.id.edtx_precio_unitarioo);
        edtxTotal = findViewById(R.id.edtx_totall);
        edtxTambores = findViewById(R.id.edtx_numero_tamboress);

        agregarMiel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void validarForm() {
        varCodigo = edtxCodigo.getText().toString().trim();
        varFolio = edtxFolio.getText().toString().trim();
        varFecha = edtxFecha.getText().toString().trim();
        varProductor = edtxProductor.getText().toString().trim();
        varHumedad = edtxHumedad.getText().toString().trim();
        varPrecioBruto = edtxPrecioBruto.getText().toString().trim();
        varTara = edtxTara.getText().toString().trim();
        varPesoNeto = edtxPesoNeto.getText().toString().trim();
        varPrecioUnitario = edxtPrecioUnitario.getText().toString().trim();
        varTotal = edtxTotal.getText().toString().trim();
        varTambores = edtxTambores.getText().toString().trim();

        if (varCodigo.isEmpty() || varFolio.isEmpty() || varFecha.isEmpty() || varProductor.isEmpty() || varHumedad.isEmpty() || varPrecioBruto.isEmpty() || varTara.isEmpty() || varPesoNeto.isEmpty() || varPrecioUnitario.isEmpty()
            || varTotal.isEmpty() || varTambores.isEmpty() ) {
            Toast.makeText(AgregarMielOrganicaActivity.this, "Todos los campos son requeridos", Toast.LENGTH_LONG).show();
        } {
            guardarMielOrganica();
        }
    }

    private void guardarMielOrganica(){
        String urlGuardarEnvio = "";

        Intent intent = new Intent(AgregarMielOrganicaActivity.this, MielOrganicaFragment.class);
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
                Toast.makeText(AgregarMielOrganicaActivity.this, "Compruba tu conexión a internet", Toast.LENGTH_SHORT).show();
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