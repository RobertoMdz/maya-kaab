package com.coopera.mayakaab.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coopera.mayakaab.R;
import com.coopera.mayakaab.models.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CajaFragment extends Fragment {

    public CajaFragment() {
        // Required empty public constructor
    }

    ProgressBar progressBar;
    ProgressBar cajaProgress;

    TextView txtSaldo, txtTotal, txtSaldoPorcentaje;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_caja, container, false);

        progressBar = vista.findViewById(R.id.progresBarCaja);
        cajaProgress = vista.findViewById(R.id.progress_bar);
        txtSaldo = vista.findViewById(R.id.text_view_saldo);
        txtTotal = vista.findViewById(R.id.text_view_total);
        txtSaldoPorcentaje = vista.findViewById(R.id.text_view_caja_porcentaje);

        obtenerSaldoCaja();
        return vista;
    }

    private void obtenerSaldoCaja() {
        progressBar.setVisibility(View.VISIBLE);
        String url = Constants.URL_BASE + "caja.php?action=show";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    progressBar.setVisibility(View.INVISIBLE);
                    JSONObject result = new JSONObject(response);
                    JSONArray listaEnvios = result.getJSONArray("caja");
                    JSONObject cajaObj = listaEnvios.getJSONObject(0);

                    String cajaInicial = cajaObj.getString("caja_inicial");
                    String cajaSaldo = cajaObj.getString("caja_final");

                    int cajaI = Integer.parseInt(cajaInicial);
                    int cajaF = Integer.parseInt(cajaSaldo);

                    txtSaldo.setText("$"+cajaF+"/");
                    txtTotal.setText("$"+cajaI);

                    float porcentajeSaldoo = (float)cajaF / cajaI * 100;

                    int porcentajeInt = Math.round(porcentajeSaldoo);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        cajaProgress.setProgress(porcentajeInt,true);
                    } else {
                        cajaProgress.setProgress(porcentajeInt);
                    }
                    String saldoTxt = String.valueOf(porcentajeSaldoo);
                    txtSaldoPorcentaje.setText(saldoTxt.substring(0,4) + "%");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

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