package com.coopera.mayakaab.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.coopera.mayakaab.models.ComprasMielModel;
import com.coopera.mayakaab.models.Constants;
import com.coopera.mayakaab.models.EnvioTamboresModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AgregarEnviosTamboresActivity extends AppCompatActivity {

    Button btnAgregar;
    EditText edtxNumCosecha, edtxCodigo, edtxFolio, edtxNumTambor, edtxKgsBruto, edtxTara, edtxKgsNetos, edtxNumeroTamborTix;
    ProgressBar progressBar;

    String cosecha, codigo, folio, tambor, kgsBrutos, tara, kgsNetos, numTamborTix;

    String idUsuario;
    Boolean isUpdate = false;
    EnvioTamboresModel envioTamboresModel;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_envios_tambores);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        isUpdate = getIntent().getBooleanExtra("isUpdate",false);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefsLogin",MODE_PRIVATE);
        idUsuario = pref.getString("id","");

        edtxNumCosecha = findViewById(R.id.edtx_numero_cosecha);
        edtxCodigo = findViewById(R.id.edtx_codigo);
        edtxFolio = findViewById(R.id.edtx_numero_folio);
        edtxNumTambor = findViewById(R.id.edtx_numero_tambor);
        edtxKgsBruto = findViewById(R.id.edtx_kgs_bruto);
        edtxTara = findViewById(R.id.edtx_tara);
        edtxKgsNetos = findViewById(R.id.edtx_kgs_netos);
        edtxNumeroTamborTix = findViewById(R.id.edtx_numero_tambortix);
        progressBar = findViewById(R.id.progressBarAddTambo);
        btnAgregar = findViewById(R.id.btn_agregar_envio);

        if(isUpdate) {
            btnAgregar.setText("Actualizar");
            envioTamboresModel = gson.fromJson(getIntent().getStringExtra("envio"), EnvioTamboresModel.class);
            setInitialFormState(envioTamboresModel);
        }

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarInputsForm();
            }
        });

        edtxTara.addTextChangedListener(totalKgsTaraInputTextWatcher);
        edtxKgsBruto.addTextChangedListener(inputTextWatcher);
        edtxTara.addTextChangedListener(inputTextWatcher);

    }

    private TextWatcher inputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String pesoBruto = edtxKgsBruto.getText().toString().trim();
            String pesoTara = edtxTara.getText().toString().trim();

            if(!pesoBruto.isEmpty() && !pesoTara.isEmpty()) {
                double pBruto = Double.parseDouble(pesoBruto);
                double pTara = Double.parseDouble(pesoTara);
                double resultado = pBruto - pTara;
                edtxKgsNetos.setText(String.valueOf(resultado));
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private TextWatcher totalKgsTaraInputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String pesoBruto = edtxKgsBruto.getText().toString().trim();
            String pesoTara = edtxTara.getText().toString().trim();

            if(!pesoBruto.isEmpty() && !pesoTara.isEmpty()) {
                double pBruto = Double.parseDouble(pesoBruto);
                double pTara = Double.parseDouble(pesoTara);

                if (pTara >= pBruto) {
                    edtxTara.setError("Tara no puede ser mayor o igual a peso bruto");
                }
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void setInitialFormState(EnvioTamboresModel envio) {
        edtxNumCosecha.setText(envio.getNumCosecha());
        edtxCodigo.setText(envio.getCodigo());
        edtxFolio.setText(envio.getNumeroFolio());
        edtxNumTambor.setText(envio.getNumeroTamborSubcentro());
        edtxKgsBruto.setText(envio.getKgsBruto());
        edtxTara.setText(envio.getTara());
        edtxKgsNetos.setText(envio.getKgsNeto());
        edtxNumeroTamborTix.setText(envio.getNumTamborTix());
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

        if (!kgsBrutos.isEmpty() && !tara.isEmpty()) {
            double pBruto = Double.parseDouble(kgsBrutos);
            double pTara = Double.parseDouble(tara);

            if (pTara >= pBruto) {
                Toast.makeText(AgregarEnviosTamboresActivity.this, "Hay errores en el formulario que debes corregir", Toast.LENGTH_LONG).show();
            } else {
                if (cosecha.isEmpty() || codigo.isEmpty() || folio.isEmpty() || tambor.isEmpty() || kgsBrutos.isEmpty() || tara.isEmpty() || kgsNetos.isEmpty() || numTamborTix.isEmpty()) {
                    Toast.makeText(AgregarEnviosTamboresActivity.this, "Todos los campos marcados con * son requeridos", Toast.LENGTH_LONG).show();
                } {
                    guardarEnvio();
                }
            }
        }
    }

    private void guardarEnvio(){
        String url;
        if (isUpdate) {
            url = Constants.URL_BASE + "envios.php?action=update";
        } else {
            url = Constants.URL_BASE + "envios.php?action=save";
        }

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(AgregarEnviosTamboresActivity.this,"Registro exitoso", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AgregarEnviosTamboresActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }

        }) {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                Map <String,String> dataEnvio= new HashMap<>();

                dataEnvio.put("numeroCosecha",cosecha);
                dataEnvio.put("codigoCosecha",codigo);
                dataEnvio.put("numeroFolioCosecha",folio);
                dataEnvio.put("numeroTamborSub",tambor);
                dataEnvio.put("pesoBruto",kgsBrutos);
                dataEnvio.put("pesoTara",tara);
                dataEnvio.put("totalPesoKgs",kgsNetos);
                dataEnvio.put("numTamboresTix",numTamborTix);
                dataEnvio.put("id_usuario",idUsuario);
                if (isUpdate) {
                    dataEnvio.put("id_envio",envioTamboresModel.getIdEnvio());
                }
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