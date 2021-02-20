package com.coopera.mayakaab.controllers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coopera.mayakaab.R;
import com.coopera.mayakaab.models.Constants;
import com.coopera.mayakaab.models.Movimientos;
import com.coopera.mayakaab.models.ProductorModel;
import com.coopera.mayakaab.views.AgregarProductorActivity;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MovimientosListAdapter extends RecyclerView.Adapter<MovimientosListAdapter.ViewHolder> implements Serializable {
    private Context mContext;
    private ArrayList<Movimientos> mData;

    public MovimientosListAdapter(Context mContext, ArrayList<Movimientos> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MovimientosListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.movimientos_list_item,parent,false);
        return new MovimientosListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovimientosListAdapter.ViewHolder holder, int position) {
        final Movimientos movimiento = mData.get(position);

        holder.nombre.setText(movimiento.getFecha().substring(0,10));
        holder.comunidad.setText("- $"+movimiento.getTotal());
        holder.txtMielKgs.setText(movimiento.getMielKgs()  + " kgs");

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView comunidad;
        TextView txtMielKgs;

        public ViewHolder (View itemView) {
            super(itemView);
            nombre    = itemView.findViewById(R.id.txtFechaMovimiento);
            comunidad = itemView.findViewById(R.id.txtGasto);
            txtMielKgs = itemView.findViewById(R.id.txtMielkgs);

        }
    }

}
