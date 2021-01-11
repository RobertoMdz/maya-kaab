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
import com.coopera.mayakaab.adapters.ProductorListAdapter;
import com.coopera.mayakaab.models.ProductorModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

        for (int i = 0; i < 10; i++){
            productoresList.add(new ProductorModel("Pablo Chan Chan", "Señor", "Miel entera", "Miel falt", "9831823749"));
            productoresList.add(new ProductorModel("Jesus May Chan", "Morelos", "Miel entera", "Miel ", "9831843346"));
        }

        ProductorListAdapter myadapter = new ProductorListAdapter(ProductoresActivity.this,productoresList);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductoresActivity.this));
        recyclerView.setAdapter(myadapter);

        fltBtnAgregarProductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductoresActivity.this, AgregarProductorActivity.class);
                startActivity(intent);
            }
        });

    }

    private void obtenerProductores() {
        String urlGetProductores = "http://10.0.2.2/mayakaab/save-productor.php";
        onBackPressed();

        StringRequest request = new StringRequest(Request.Method.GET, urlGetProductores, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                String respuestaLogin = response;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductoresActivity.this, "Compruba tu conexión a internet", Toast.LENGTH_SHORT).show();
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