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
import com.coopera.mayakaab.controllers.MovimientosListAdapter;
import com.coopera.mayakaab.models.ComprasMielModel;
import com.coopera.mayakaab.models.Constants;
import com.coopera.mayakaab.models.Movimientos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MovimientosFragment extends Fragment {

    public MovimientosFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<Movimientos> movimientosList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_movimientos, container, false);

        recyclerView = vista.findViewById(R.id.rvMovimientos);
        progressBar = vista.findViewById(R.id.progresBarMovimientos);


        obtenerMovimientos();
        return vista;
    }

    private void obtenerMovimientos() {
        progressBar.setVisibility(View.VISIBLE);
        String urlGetList =  Constants.URL_BASE + "caja.php?action=mov&miel_t=1";

        StringRequest request = new StringRequest(Request.Method.GET, urlGetList, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respuesta = new JSONObject(response);
                    JSONArray movimientoslist = respuesta.getJSONArray("movimientos");

                    for (int i = 0; i < movimientoslist.length(); i++) {
                        JSONObject movimiento = movimientoslist.getJSONObject(i);

                        String fecha = movimiento.getString("fecha_registro");
                        String gasto = movimiento.getString("total_pagar");
                        String mielKgs = movimiento.getString("total_kgs");

                        movimientosList.add(new Movimientos(fecha, gasto, mielKgs));
                    }

                    MovimientosListAdapter mielAdapter = new MovimientosListAdapter(getContext(),movimientosList);
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

}