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
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.coopera.mayakaab.R;
import com.coopera.mayakaab.controllers.EnviosTamboresListAdapter;
import com.coopera.mayakaab.controllers.GaleriaListAdapter;
import com.coopera.mayakaab.models.Constants;
import com.coopera.mayakaab.models.GaleriaImagenModel;
import com.coopera.mayakaab.models.TipoDeMielModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GaleriaActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<GaleriaImagenModel> itemsList = new ArrayList<>();
    FloatingActionButton fltbtnSubirImagen;

    String idUsuario;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefsLogin",MODE_PRIVATE);
        idUsuario = pref.getString("id","");


        recyclerView = findViewById(R.id.recycler_galeria);
        fltbtnSubirImagen = findViewById(R.id.fltnbtn_subir_imagen);
        progressBar = findViewById(R.id.progressBarGaleria);

        fltbtnSubirImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();

            }
        });

        obtenerImagenes();
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
                String imagen = converImageBitmapToString(image);
                uploadImageToServer(imagen);
            }
        }
    }

    private void uploadImageToServer(String imagen) {
        final ProgressDialog dialog = ProgressDialog.show(this, "Guardando imagen...", "Espere porfavor");
        String url = Constants.URL_BASE+"img.php?action=save";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                itemsList.clear();
                obtenerImagenes();
                Toast.makeText(GaleriaActivity.this, response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(GaleriaActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR",error.toString());
            }

        }) {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                Map <String,String> dataEnvio= new HashMap<>();

                dataEnvio.put("id_usuario", idUsuario);
                dataEnvio.put("image", imagen);
                return dataEnvio;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(15000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

    private String converImageBitmapToString(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 85, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void obtenerImagenes() {
        progressBar.setVisibility(View.VISIBLE);
        String url = Constants.URL_BASE+"img.php?action=show";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.INVISIBLE);

                try {
                    JSONObject respuesta = new JSONObject(response);
                    JSONArray productores = respuesta.getJSONArray("galeria");

                    for (int i = 0; i < productores.length(); i++) {
                        JSONObject galeria = productores.getJSONObject(i);

                        String id = galeria.getString("id_imagen");
                        String nombre = galeria.getString("nombre");
                        String imgPath = "http://mayakaab-app.herokuapp.com/img/galeria/" + nombre;

                        itemsList.add(new GaleriaImagenModel(id, imgPath));
                    }
                    GaleriaListAdapter adapter = new GaleriaListAdapter(GaleriaActivity.this, itemsList);
                    recyclerView.setLayoutManager(new GridLayoutManager(GaleriaActivity.this, 2));
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(GaleriaActivity.this, error.toString()+"Compruba tu conexión a internet", Toast.LENGTH_SHORT).show();
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