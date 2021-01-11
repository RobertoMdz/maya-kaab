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

public class AgregarMielConvencionalActivity extends AppCompatActivity {

    Button btnAgregarMiel;
    EditText edtxFecha, edtxProductoro, edtxComunidad, edtxPrecioBruto, edtxPrecioTara, edtxPesoNeto, edtxPrecioCompra, edtxTotal, edxtSaldo;
    String fecha, productor, comunidad, pBruto, pTara, pesoNeto, pCompra, total, saldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_miel_convencional);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnAgregarMiel = findViewById(R.id.btn_guardar_miel_convencional);
        edtxFecha = findViewById(R.id.edtx_fecha);
        edtxProductoro = findViewById(R.id.edtxt_productor_nombre);
        edtxComunidad = findViewById(R.id.edtx_comunidad);
        edtxPrecioBruto = findViewById(R.id.edtx_precio_bruto);
        edtxPrecioTara = findViewById(R.id.edtx_tara);
        edtxPesoNeto = findViewById(R.id.edtx_precio_neto);
        edtxPrecioCompra = findViewById(R.id.edtx_precio_compra);
        edtxTotal = findViewById(R.id.edtx_total);
        edxtSaldo = findViewById(R.id.edtx_saldo_por_pagar);

        btnAgregarMiel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarForm();
                onBackPressed();
            }
        });
    }

    private void validarForm() {
        fecha = edtxFecha.getText().toString().trim();
        productor = edtxProductoro.getText().toString().trim();
        comunidad = edtxComunidad.getText().toString().trim();
        pBruto = edtxPrecioBruto.getText().toString().trim();
        pTara = edtxPrecioTara.getText().toString().trim();
        pesoNeto = edtxPesoNeto.getText().toString().trim();
        pCompra = edtxPrecioCompra.getText().toString().trim();
        total = edtxTotal.getText().toString().trim();
        saldo = edxtSaldo.getText().toString().trim();

        if (fecha.isEmpty() || productor.isEmpty() || comunidad.isEmpty() || pBruto.isEmpty() || pTara.isEmpty() || pesoNeto.isEmpty() || pCompra.isEmpty() || total.isEmpty() || saldo.isEmpty()) {
            Toast.makeText(AgregarMielConvencionalActivity.this, "Todos los campos son requeridos", Toast.LENGTH_LONG).show();
        } {
            guardarMielConvencional();
        }
    }

    private void guardarMielConvencional(){
        String urlGuardarEnvio = "hht/maya-kaab/create_miel.php";

        Intent intent = new Intent(AgregarMielConvencionalActivity.this, MielConvencionalFragment.class);
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
                Toast.makeText(AgregarMielConvencionalActivity.this, "Compruba tu conexión a internet", Toast.LENGTH_SHORT).show();
            }

        }) {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                Map <String,String> dataEnvio= new HashMap<>();

                dataEnvio.put("username",fecha);
                dataEnvio.put("password",pCompra);
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