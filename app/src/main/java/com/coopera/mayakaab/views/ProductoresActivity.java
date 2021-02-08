package com.coopera.mayakaab.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coopera.mayakaab.R;
import com.coopera.mayakaab.controllers.ProductorListAdapter;
import com.coopera.mayakaab.models.Constants;
import com.coopera.mayakaab.models.ProductorModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductoresActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fltBtnAgregarProductor;
    ArrayList<ProductorModel> productoresList = new ArrayList<>();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productores);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyler_view_productores);
        fltBtnAgregarProductor = findViewById(R.id.flt_agregar_productor);
        progressBar = findViewById(R.id.progressBar);

        fltBtnAgregarProductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductoresActivity.this, AgregarProductorActivity.class);
                startActivity(intent);
            }
        });

    }

    private void obtenerProductores() {
        progressBar.setVisibility(View.VISIBLE);
        String urlGetProductores = Constants.URL_BASE+"productores.php?action=1";
        //onBackPressed();

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
                            }


                            ProductorListAdapter myadapter = new ProductorListAdapter(ProductoresActivity.this,productoresList);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ProductoresActivity.this));
                            recyclerView.setAdapter(myadapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressBar.setVisibility(View.INVISIBLE);


                    }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ProductoresActivity.this, "Comprueba tu conexión a internet", Toast.LENGTH_SHORT).show();
            }

        });

        Volley.newRequestQueue(this).add(request);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        productoresList.clear();
        obtenerProductores();
    }


}