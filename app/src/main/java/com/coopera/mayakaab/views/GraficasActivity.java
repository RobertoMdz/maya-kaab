package com.coopera.mayakaab.views;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coopera.mayakaab.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class GraficasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BarChart barChart = findViewById(R.id.barChart);
        ArrayList<BarEntry> visitors = new ArrayList<>();
        visitors.add(new BarEntry(1, 200));
        visitors.add(new BarEntry(2, 100));
        visitors.add(new BarEntry(3, 30));
        visitors.add(new BarEntry(4, 10));
        visitors.add(new BarEntry(5, 33));
        visitors.add(new BarEntry(6, 56));
        visitors.add(new BarEntry(7, 60));
        visitors.add(new BarEntry(8, 27));
        visitors.add(new BarEntry(9, 26));
        visitors.add(new BarEntry(10, 29));
        visitors.add(new BarEntry(11, 66));
        visitors.add(new BarEntry(12, 200));

        BarDataSet barDataSet = new BarDataSet(visitors, "Ventas");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Venta de miel por mes");
        barChart.animateX(2000);

    }

    private void obtenerVentas() {
        String urlGetVentas = "";
        onBackPressed();

        StringRequest request = new StringRequest(Request.Method.GET, urlGetVentas, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                String respuesta = response;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GraficasActivity.this, "Compruba tu conexión a internet", Toast.LENGTH_SHORT).show();
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