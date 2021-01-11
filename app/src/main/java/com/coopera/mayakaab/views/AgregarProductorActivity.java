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

import java.util.HashMap;
import java.util.Map;

public class AgregarProductorActivity extends AppCompatActivity {

    private  Button btnGuardar;
    private EditText edtxtProductorNombre,
             edtxtProductorComunidad,
             edtxtProductorMielEntrada,
             edtxtProductorMielFalt,
             edtxtProductorTelefono;

    private String productorNombre,
                   productorComunidad,
                   productorMielEntrada,
                   productorMielFalt,
                   productorTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_productor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnGuardar = findViewById(R.id.btn_guardar_productor);
        edtxtProductorNombre = findViewById(R.id.edtxt_productor_nombre);
        edtxtProductorComunidad = findViewById(R.id.edtxt_productor_comunidad);
        edtxtProductorMielEntrada = findViewById(R.id.edtxt_productor_miel_entrada);
        edtxtProductorMielFalt = findViewById(R.id.edtxt_productor_miel_falt);
        edtxtProductorTelefono = findViewById(R.id.edtxt_productor_telefono);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarFormulario();
            }
        });
    }

    private void validarFormulario() {
        productorNombre = edtxtProductorNombre.getText().toString().trim();
        productorComunidad = edtxtProductorComunidad.getText().toString().trim();
        productorMielEntrada = edtxtProductorMielEntrada.getText().toString().trim();
        productorMielFalt = edtxtProductorMielFalt.getText().toString().trim();
        productorTelefono = edtxtProductorTelefono.getText().toString().trim();

        if (productorNombre.equals("") || productorComunidad.equals("") || productorMielEntrada.equals("") || productorMielFalt.equals("") || productorTelefono.equals("")) {
            // Lanzar mensaje
            Toast.makeText(AgregarProductorActivity.this, "Todos los campos son requeridos", Toast.LENGTH_LONG).show();
        } else {
            guardarDatosFormulario();
        }
    }

    private void guardarDatosFormulario() {
        String url = "http://172.10.0.9/mayakaab/save-productor.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String respuesta = response;
                Toast.makeText(AgregarProductorActivity.this,"Productor registrado" , Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AgregarProductorActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map <String,String> dataProductor= new HashMap<>();

                dataProductor.put("nombre",productorNombre);
                dataProductor.put("comunidad",productorComunidad);
                dataProductor.put("miel_entrada",productorMielEntrada);
                dataProductor.put("miel_faltante",productorMielFalt);
                dataProductor.put("telefono",productorTelefono);
                return dataProductor;
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