package com.coopera.mayakaab.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.coopera.mayakaab.controllers.ComprasMielListAdapter;
import com.coopera.mayakaab.models.Constants;
import com.coopera.mayakaab.models.ComprasMielModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MielConvencionalFragment extends Fragment {

    public MielConvencionalFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    FloatingActionButton btnAgregar;
    ArrayList<ComprasMielModel> mielConvencionalList = new ArrayList<>();
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_miel_convencional, container, false);

        recyclerView = vista.findViewById(R.id.recycler_miel_convencional);
        btnAgregar = vista.findViewById(R.id.fltbtn_agregar_miel_convencional);
        progressBar = vista.findViewById(R.id.progressBarConvencional);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getContext(), AgregarMielConvencionalActivity.class);
                startActivity(newIntent);
            }
        });

        obtenerMielConvencional();
        return vista;

    }

    private void obtenerMielConvencional() {
        progressBar.setVisibility(View.VISIBLE);
        String urlGetList =  Constants.URL_BASE + "miel.php?action=tipo-miel&tm=2";

        StringRequest request = new StringRequest(Request.Method.GET, urlGetList, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respuesta = new JSONObject(response);
                    JSONArray mielConvencionalJsonList = respuesta.getJSONArray("miel-convencional");

                    for (int i = 0; i < mielConvencionalJsonList.length(); i++) {
                        JSONObject mielConvencional = mielConvencionalJsonList.getJSONObject(i);

                        ComprasMielModel mielConvencionalItem = convertJsonObToModel(mielConvencional);
                        mielConvencionalList.add(mielConvencionalItem);
                    }

                    ComprasMielListAdapter mielAdapter = new ComprasMielListAdapter(getContext(),mielConvencionalList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(mielAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Compruba tu conexión a internet", Toast.LENGTH_SHORT).show();
            }

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        request.setRetryPolicy(new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }

    private ComprasMielModel convertJsonObToModel(JSONObject miel) {
        ComprasMielModel comprasMielModel;

        try {
            String idMiel = miel.getString("id_miel");
            String nombreProductor = miel.getString("nombre_productor");
            String localidad = miel.getString("localidad");
            String codigo = miel.getString("codigo");
            String numeroFolio = miel.getString("numero_folio");
            String humedad = miel.getString("humedad");
            String pesoBruto = miel.getString("peso_bruto");
            String pesoTara = miel.getString("peso_tara");
            String precioCompra = miel.getString("precio_compra");
            String totalKgs = miel.getString("total_kgs");
            String totalPagar = miel.getString("total_pagar");
            String numeroTambor = miel.getString("numero_tambor");
            String mielEntrante = miel.getString("miel_entrante");
            String mielFaltante = miel.getString("miel_faltante");
            String idProductor = miel.getString("id_productor");
            String idRegistro = miel.getString("id_registro");
            String idUsuario = miel.getString("id_usuario");
            String fechaRegistro = miel.getString("fecha_registro");
            String tipoMiel = miel.getString("tipo_miel");

            comprasMielModel = new ComprasMielModel(
                    idMiel, nombreProductor, localidad, codigo, numeroFolio, humedad,
                    pesoBruto, pesoTara, precioCompra, totalKgs, totalPagar, numeroTambor,
                    mielEntrante, mielFaltante, idProductor, idRegistro, idUsuario, fechaRegistro, tipoMiel);
            return comprasMielModel;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}