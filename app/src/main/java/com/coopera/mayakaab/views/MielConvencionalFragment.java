package com.coopera.mayakaab.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coopera.mayakaab.R;
import com.coopera.mayakaab.adapters.MielConvencionalListAdapter;
import com.coopera.mayakaab.models.MielConvencionalModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MielConvencionalFragment extends Fragment {

    public MielConvencionalFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    FloatingActionButton btnAgregar;
    ArrayList<MielConvencionalModel> mielConvencionalList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_miel_convencional, container, false);

        recyclerView = vista.findViewById(R.id.recycler_miel_convencional);
        btnAgregar = vista.findViewById(R.id.fltbtn_agregar_miel_convencional);

        for (int i = 0; i < 10; i++){
            mielConvencionalList.add(new MielConvencionalModel("12/Diciembre/2020","Juan Pedro","Señor", "$100","pt","pn","pc","ti","La miel esta buena","$120.50", "$100","Juan Perez"));
        }

        MielConvencionalListAdapter mielAdapter = new MielConvencionalListAdapter(getContext(),mielConvencionalList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mielAdapter);


        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getContext(), AgregarMielConvencionalActivity.class);
                startActivity(newIntent);
            }
        });
        return vista;

    }

    private void obtenerMielConvencional() {
        String urlGetList = "";

        StringRequest request = new StringRequest(Request.Method.GET, urlGetList, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                String respuestaLogin = response;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Compruba tu conexión a internet", Toast.LENGTH_SHORT).show();
            }

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        request.setRetryPolicy(new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }
}