package com.coopera.mayakaab.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.coopera.mayakaab.models.Constants;
import com.coopera.mayakaab.models.ProductorModel;
import com.coopera.mayakaab.models.TipoDeMielModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AgregarMielConvencionalActivity extends AppCompatActivity {

    Button btnAgregarMiel;
    EditText edtxLocalidad, edtxCodigo, edtxFolio, edtxHumedad, edtxPesoBruto, edtxPesoTara, edtxPrecioCompra,
            edxtTotalKgs, edxtTotalPagar, edxtNumeroTambor, edxtMielEntrante, edxtMielFaltante;
    Spinner spinnerProductores;
    ProgressBar progressBar;

    String localidad, codigo, folio, humedad, pesoBruto, pesoTara, precioCompra, totalKgs,
            totalPagar , numeroTambor , mielEntrante , mielFaltante;
    String idUsuario;
    String idMiel;
    String precioMiel;

    ArrayList<String> productoresNombresList = new ArrayList<String>();
    ArrayList<ProductorModel> productoresList = new ArrayList<ProductorModel>();
    ProductorModel productorSelected;
    ArrayList<TipoDeMielModel> tiposDeMielModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_agregar_venta_miel);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefsLogin",MODE_PRIVATE);
        idUsuario = pref.getString("id","");

        btnAgregarMiel = findViewById(R.id.btn_guardar_miel_convencional);
        edtxLocalidad = findViewById(R.id.edtxtLocalidad);
        edtxCodigo = findViewById(R.id.edtxtCodigo);
        edtxFolio = findViewById(R.id.edtxtNumeroFolio);
        edtxHumedad = findViewById(R.id.edtxtHumedad);
        edtxPesoBruto = findViewById(R.id.edtxtPesoBruto);
        edtxPesoTara = findViewById(R.id.edtxtPesoTara);
        edtxPrecioCompra = findViewById(R.id.edtxtPrecioCompra);
        edxtTotalKgs = findViewById(R.id.edtxtTotalKgs);
        edxtTotalPagar = findViewById(R.id.edtxtTotalPagar);
        edxtNumeroTambor = findViewById(R.id.edtxtNumeroTambor);
        edxtMielEntrante = findViewById(R.id.edtxtMielEntrante);
        edxtMielFaltante = findViewById(R.id.edtxtMielFaltante);
        spinnerProductores = findViewById(R.id.spinnerProductores);
        progressBar = findViewById(R.id.progressBarAddMielConvencional);

        btnAgregarMiel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarForm();
            }
        });

        spinnerProductores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0 ) {
                    productorSelected = productoresList.get(position - 1);
                    edtxLocalidad.setText(productorSelected.getComunidad());
                } else {
                    edtxLocalidad.setText("");
                    productorSelected = null;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ///ESCUCHADOR DEL EDIT TEXTS
        edtxPesoBruto.addTextChangedListener(inputTextWatcher);
        edtxPesoTara.addTextChangedListener(inputTextWatcher);
        edxtTotalKgs.addTextChangedListener(totalKgsInputTextWatcher);

        obtenerProductores();
        obtenerTipoMiel();
    }

    private TextWatcher inputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String pesoBruto = edtxPesoBruto.getText().toString().trim();
            String pesoTara = edtxPesoTara.getText().toString().trim();

            if(!pesoBruto.isEmpty() && !pesoTara.isEmpty()) {
                double pBruto = Double.parseDouble(pesoBruto);
                double pTara = Double.parseDouble(pesoTara);
                double resultado = pBruto - pTara;
                edxtTotalKgs.setText(String.valueOf(resultado));
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private TextWatcher totalKgsInputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String totalKgs = edxtTotalKgs.getText().toString().trim();

            if(!totalKgs.isEmpty()) {
                double total = Double.parseDouble(totalKgs);
                double precio = Double.parseDouble(precioMiel);
                double resultado = total * precio;
                edxtTotalPagar.setText(String.valueOf(resultado));
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void obtenerTipoMiel() {
        progressBar.setVisibility(View.VISIBLE);
        String url = Constants.URL_BASE+"registros.php?action=1";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject respuesta = new JSONObject(response);
                            JSONArray productores = respuesta.getJSONArray("registros");

                            for (int i = 0; i < productores.length(); i++) {
                                JSONObject tipoMiel = productores.getJSONObject(i);

                                String id = tipoMiel.getString("id_registro");
                                String nombre = tipoMiel.getString("nombre_registro");
                                String descripcion = tipoMiel.getString("descripcion");
                                String precio = tipoMiel.getString("precio");
                                String fechaRegistro = tipoMiel.getString("fecha_registro");

                                tiposDeMielModelList.add(new TipoDeMielModel(id, nombre, descripcion, precio, fechaRegistro));
                            }
                            edtxPrecioCompra.setText(tiposDeMielModelList.get(1).getPrecio());
                            idMiel = tiposDeMielModelList.get(1).getId();
                            precioMiel = tiposDeMielModelList.get(1).getPrecio();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AgregarMielConvencionalActivity.this, "Comprueba tu conexión a internet", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(request);
    }

    private void obtenerProductores() {
        progressBar.setVisibility(View.VISIBLE);
        String urlGetProductores = Constants.URL_BASE+"productores.php?action=1";

        StringRequest request = new StringRequest(Request.Method.GET, urlGetProductores,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject respuesta = new JSONObject(response);
                            JSONArray productores = respuesta.getJSONArray("productores");

                            for (int i = 0; i < productores.length(); i++) {
                                JSONObject productor = productores.getJSONObject(i);
                                String idProductor = productor.getString("id_productor");
                                String nombreProductor = productor.getString("nombre_productor");
                                String localidadProductor = productor.getString("localidad_productor");
                                String telefonoProductor = productor.getString("telefono");
                                String referenciaProductor = productor.getString("referencia");

                                productoresList.add(new ProductorModel(idProductor, nombreProductor, localidadProductor, telefonoProductor, referenciaProductor));
                                productoresNombresList.add(nombreProductor);
                            }
                            productoresNombresList.add(0, "Selecciona un productor");

                            // AGREGANDO CIUDADES AL SPINNER //
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AgregarMielConvencionalActivity.this, R.layout.spinner_custom, productoresNombresList);
                            spinnerProductores.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AgregarMielConvencionalActivity.this, "Comprueba tu conexión a internet", Toast.LENGTH_SHORT).show();
            }

        });

        Volley.newRequestQueue(this).add(request);
    }


    private void validarForm() {

        localidad = edtxLocalidad.getText().toString().trim();
        codigo = edtxCodigo.getText().toString().trim();
        folio = edtxFolio.getText().toString().trim();
        humedad = edtxHumedad.getText().toString().trim();
        pesoBruto = edtxPesoBruto.getText().toString().trim();
        pesoTara = edtxPesoTara.getText().toString().trim();
        precioCompra = edtxPrecioCompra.getText().toString().trim();
        totalKgs = edxtTotalKgs.getText().toString().trim();
        totalPagar = edxtTotalPagar.getText().toString().trim();
        numeroTambor = edxtNumeroTambor.getText().toString().trim();
        mielEntrante = edxtMielEntrante.getText().toString().trim();
        mielFaltante = edxtMielFaltante.getText().toString().trim();

        if ( productorSelected == null ||localidad.isEmpty() || codigo.isEmpty() || folio.isEmpty() || pesoBruto.isEmpty() || pesoTara.isEmpty() || precioCompra.isEmpty() || totalKgs.isEmpty() || totalPagar.isEmpty() || mielEntrante.isEmpty() || mielFaltante.isEmpty()) {
            Toast.makeText(AgregarMielConvencionalActivity.this, "Los campos marcados con * son requeridos", Toast.LENGTH_LONG).show();
        } else {
            guardarMielConvencional();
        }
    }

    private void guardarMielConvencional(){
        progressBar.setVisibility(View.VISIBLE);
        String url = Constants.URL_BASE + "miel.php?action=save";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(AgregarMielConvencionalActivity.this, "Registro exitoso", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(AgregarMielConvencionalActivity.this, TiposDeMielActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AgregarMielConvencionalActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR",error.toString());
            }

        }) {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                Map <String,String> dataEnvio= new HashMap<>();

                dataEnvio.put("nombreProductor", productorSelected.getNombre());
                dataEnvio.put("localidad", productorSelected.getComunidad());
                dataEnvio.put("codigo", codigo);
                dataEnvio.put("numeroFolio", folio);
                dataEnvio.put("humedad", humedad);
                dataEnvio.put("pesoBruto", pesoBruto);
                dataEnvio.put("pesoTara", pesoTara);
                dataEnvio.put("precioCompra", precioCompra);
                dataEnvio.put("totalKg", totalKgs);
                dataEnvio.put("totalPagar", totalPagar);
                dataEnvio.put("numeroTambor", numeroTambor);
                dataEnvio.put("mielEntrante", mielEntrante);
                dataEnvio.put("mielFaltante", mielFaltante);
                dataEnvio.put("id_productor", productorSelected.getId());
                dataEnvio.put("id_registror", idMiel);
                dataEnvio.put("id_usuario", idUsuario);

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