package com.coopera.mayakaab.views;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
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
import com.coopera.mayakaab.models.ComprasModel;
import com.coopera.mayakaab.models.Constants;
import com.coopera.mayakaab.models.EnvioTamboresModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GraficasActivity extends AppCompatActivity {

    ArrayList<ComprasModel> comprasList = new ArrayList<>();
    Gson gson = new Gson();
    ArrayList<String> arrayMeses = new ArrayList<>();
    ArrayList<BarEntry> barEntries = new ArrayList<>();
    String meses[];
    BarChart barChart;
    BarDataSet barDataSet;
    BarData barData;
    String comprasJsonList;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        barChart = findViewById(R.id.barChart);
        textView = findViewById(R.id.textTitle);

        comprasJsonList = getIntent().getStringExtra("compras");
        if(comprasJsonList.length() > 0 ) {
            Type listType = new TypeToken<List<ComprasModel>>(){}.getType();
            comprasList = gson.fromJson(comprasJsonList, listType);
        }


        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setDrawGridBackground(true);

        int barEntryNumber = 0;

        if(!comprasList.isEmpty()) {
            for(int i = 0; i < comprasList.size(); i++) {
                String stringEntryData = comprasList.get(i).getCantidad_compra_mes();
                float floatEntryData = Float.parseFloat(stringEntryData);

                barEntries.add(new BarEntry(barEntryNumber, floatEntryData));
                barEntryNumber++;
            }

            barDataSet = new BarDataSet(barEntries, "Compras");
            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            barData = new BarData(barDataSet);
            barData.setBarWidth(0.7f);

            barChart.setData(barData);

            for (int i = 0; i < comprasList.size(); i++) {
                arrayMeses.add(getMonthName(comprasList.get(i).getMes()));
            }

            String mesInicio = arrayMeses.get(0);
            String mesFinal = "- "+arrayMeses.get(arrayMeses.size() - 1);

            textView.setText("Compras de " + mesInicio + mesFinal);

            meses = new String[arrayMeses.size()];
            for (int i = 0; i < arrayMeses.size(); i++) {
                meses[i] = arrayMeses.get(i);
            }

            XAxis xAxis = barChart.getXAxis();
            xAxis.setValueFormatter(new MyAxisValueFormatter(meses));
            xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
            xAxis.setCenterAxisLabels(true);

        }


    }

    public class MyAxisValueFormatter implements IAxisValueFormatter {
        private String[] mMeses;
        public MyAxisValueFormatter(String[] meses) {
            this.mMeses = meses;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mMeses[(int)value];
        }
    }

    public String getMonthName(String month) {
        String mMonth;
        switch (month) {
            case "January":
                mMonth = "Ene";
                break;
            case "February":
                mMonth = "Feb";
                 break;
            case "March":
                mMonth = "Mar";
                break;
            case "April":
                mMonth = "Abr";
                break;
            case "May":
                mMonth = "May";
                break;
            case "June":
                mMonth = "Jun";
                break;
            case "July":
                mMonth = "Jul";
                break;
            case "August":
                mMonth = "Ago";
                break;
            case "September":
                mMonth = "Sep";
                break;
            case "October":
                mMonth = "Oct";
                break;
            case "November":
                mMonth = "Nov";
                break;
            case "December":
                mMonth = "Dec";
            break;
            default:
                mMonth = "Null";
        }
        return mMonth;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}