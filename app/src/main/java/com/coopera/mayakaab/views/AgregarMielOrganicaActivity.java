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
import com.coopera.mayakaab.models.ComprasMielModel;
import com.coopera.mayakaab.models.Constants;
import com.coopera.mayakaab.models.ProductorModel;
import com.coopera.mayakaab.models.TipoDeMielModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AgregarMielOrganicaActivity extends AppCompatActivity {

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

    Boolean isUpdate = false;
    ComprasMielModel compraMielModel;
    Gson gson = new Gson();


    ArrayList<String> productoresNombresList = new ArrayList<String>();
    ArrayList<ProductorModel> productoresList = new ArrayList<ProductorModel>();
    ProductorModel productorSelected;
    ArrayList<TipoDeMielModel> tiposDeMielModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_agregar_venta_miel);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        isUpdate = getIntent().getBooleanExtra("isUpdate",false);
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

        if(isUpdate) {
            btnAgregarMiel.setText("Actualizar");
            compraMielModel = gson.fromJson(getIntent().getStringExtra("compra"), ComprasMielModel.class);
            setInitialFormState(compraMielModel);
        }


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
        edtxPesoTara.addTextChangedListener(totalKgsTaraInputTextWatcher);

        obtenerProductores();
        obtenerTipoMiel();
    }

    private TextWatcher totalKgsTaraInputTextWatcher = new TextWatcher() {
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

                if (pTara >= pBruto) {
                    edtxPesoTara.setError("Tara no puede ser mayor o igual a peso bruto");
                }

            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void setInitialFormState(ComprasMielModel compraMiel) {
        edtxCodigo.setText(compraMiel.getCodigo());
        edtxFolio.setText(compraMiel.getNumeroFolio());
        edtxHumedad.setText(compraMiel.getHumedad());

        edtxPesoBruto.setText(compraMiel.getPesoBruto());
        edtxPesoTara.setText(compraMiel.getPesoTara());
        edtxPrecioCompra.setText(compraMiel.getPrecioCompra());
        edxtTotalKgs.setText(compraMiel.getTotalKgs());

        edxtTotalPagar.setText(compraMiel.getTotalPagar());
        edxtNumeroTambor.setText(compraMiel.getNumeroTambor());
        edxtMielEntrante.setText(compraMiel.getMielEntrante());
        edxtMielFaltante.setText(compraMiel.getMielFaltante());

        // Inicializar variables
        precioMiel = compraMiel.getPrecioCompra();
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

                            if(!isUpdate) {
                                edtxPrecioCompra.setText(tiposDeMielModelList.get(0).getPrecio());
                                precioMiel = tiposDeMielModelList.get(0).getPrecio();
                            }
                            idMiel = tiposDeMielModelList.get(0).getId();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AgregarMielOrganicaActivity.this, "Comprueba tu conexión a internet", Toast.LENGTH_SHORT).show();
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
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AgregarMielOrganicaActivity.this, R.layout.spinner_custom, productoresNombresList);
                            spinnerProductores.setAdapter(adapter);

                            if(isUpdate) {
                                spinnerProductores.setSelection(adapter.getPosition(compraMielModel.getNombreProductor()));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AgregarMielOrganicaActivity.this, "Comprueba tu conexión a internet", Toast.LENGTH_SHORT).show();
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

        if (!pesoBruto.isEmpty() && !pesoTara.isEmpty()) {
            double pBruto = Double.parseDouble(pesoBruto);
            double pTara = Double.parseDouble(pesoTara);

            if (pTara >= pBruto) {
                Toast.makeText(AgregarMielOrganicaActivity.this, "Hay errores en el formulario que debes correjir", Toast.LENGTH_LONG).show();
            } else {
                if ( productorSelected == null ||localidad.isEmpty() || codigo.isEmpty() || folio.isEmpty() || pesoBruto.isEmpty() || pesoTara.isEmpty() || precioCompra.isEmpty() || totalKgs.isEmpty() || totalPagar.isEmpty() || mielEntrante.isEmpty() || mielFaltante.isEmpty()) {
                    Toast.makeText(AgregarMielOrganicaActivity.this, "Los campos marcados con * son requeridos", Toast.LENGTH_LONG).show();
                } else {
                    guardarMielConvencional();
                }
            }
        }


    }

    private void guardarMielConvencional(){
        progressBar.setVisibility(View.VISIBLE);
        String url;

        if(isUpdate) {
            url = Constants.URL_BASE + "miel.php?action=update";
        } else {
            url = Constants.URL_BASE + "miel.php?action=save";
        }


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(AgregarMielOrganicaActivity.this, "Registro exitoso", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(AgregarMielOrganicaActivity.this, TiposDeMielActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AgregarMielOrganicaActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
                if(isUpdate) {
                    dataEnvio.put("id_miel", compraMielModel.getIdMiel());
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