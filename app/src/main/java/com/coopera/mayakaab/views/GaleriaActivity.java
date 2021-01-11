package com.coopera.mayakaab.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.coopera.mayakaab.adapters.GaleriaListAdapter;
import com.coopera.mayakaab.adapters.ProductorListAdapter;
import com.coopera.mayakaab.models.GaleriaImagenModel;
import com.coopera.mayakaab.models.ProductorModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class GaleriaActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<GaleriaImagenModel> itemsList = new ArrayList<>();
    FloatingActionButton fltbtnSubirImagen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_galeria);
        fltbtnSubirImagen = findViewById(R.id.fltnbtn_subir_imagen);

        for (int i = 0; i < 10; i++){
            itemsList.add(new GaleriaImagenModel("1", R.drawable.abeja_picture));
        }

        GaleriaListAdapter myadapter = new GaleriaListAdapter(GaleriaActivity.this,itemsList);
        recyclerView.setLayoutManager(new GridLayoutManager(GaleriaActivity.this, 2));
        recyclerView.setAdapter(myadapter);

        fltbtnSubirImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();

            }
        });
    }

    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 101);
        } else {
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();

            } else {
                Toast.makeText(this, "Para acceder a la camara es necesario otorgar los permisos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera() {
        Intent camara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camara, 102);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102) {
            if (data != null) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                // setImagetoImageView
            }


        }
    }

    private void obtenerImagenes() {
        String urlGetImagenes = "";
        onBackPressed();

        StringRequest request = new StringRequest(Request.Method.GET, urlGetImagenes, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                String respuesta = response;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GaleriaActivity.this, "Compruba tu conexión a internet", Toast.LENGTH_SHORT).show();
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