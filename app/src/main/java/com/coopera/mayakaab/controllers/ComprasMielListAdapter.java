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
import com.coopera.mayakaab.models.ComprasMielModel;
import com.coopera.mayakaab.models.Constants;
import com.coopera.mayakaab.views.AgregarMielConvencionalActivity;
import com.coopera.mayakaab.views.AgregarMielOrganicaActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ComprasMielListAdapter extends RecyclerView.Adapter<ComprasMielListAdapter.ViewHolder>{

    Context mContext;
    ArrayList<ComprasMielModel> listItems;
    Gson gson = new Gson();

    public ComprasMielListAdapter(Context mContext, ArrayList<ComprasMielModel> listItems) {
        this.mContext = mContext;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public ComprasMielListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.mielconvencional_list_item,parent,false);
        return new ComprasMielListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComprasMielListAdapter.ViewHolder holder, int position) {
        final ComprasMielModel compraMiel = listItems.get(position);

        holder.fechaVenta.setText(compraMiel.getFechaRegistro());
        holder.vendedor.setText(compraMiel.getNombreProductor());

        holder.btnEditarMielConvencional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jsonCompra = gson.toJson(compraMiel);
                String tipoMiel = compraMiel.getIdRegistro();
                Intent intent;
                if (tipoMiel.equals("1")) {
                    intent = new Intent(mContext, AgregarMielOrganicaActivity.class);
                } else {
                    intent = new Intent(mContext, AgregarMielConvencionalActivity.class);
                }
                intent.putExtra("isUpdate", true);
                intent.putExtra("compra", jsonCompra);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

            }
        });

        holder.btnEliminarMielConvencional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mostrar un alert dialog personalizado //
                LayoutInflater mInflater = LayoutInflater.from(mContext);

                androidx.appcompat.app.AlertDialog.Builder mBuilder = new androidx.appcompat.app.AlertDialog.Builder(mContext);
                View mView = mInflater.inflate(R.layout.popup_eliminar,null);

                Button btn_cancelar = (Button) mView.findViewById(R.id.btnCancelarDelete);
                Button btnEliminar = (Button) mView.findViewById(R.id.btnEliminar);

                mBuilder.setView(mView);
                final androidx.appcompat.app.AlertDialog dialog = mBuilder.create();
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final String url = Constants.URL_BASE+"miel.php?action=delete";

                        StringRequest putRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response) {
                                        dialog.dismiss();
                                        listItems.remove(holder.getLayoutPosition());
                                        notifyItemRemoved(holder.getLayoutPosition());
                                    }

                                },
                                new Response.ErrorListener()
                                {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        dialog.dismiss();
                                        Toast.makeText(mContext, "No se pudo eliminar el elemento. Comprueba tu conexion a internet", Toast.LENGTH_SHORT).show();

                                    }
                                }
                        ) {

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError
                            {
                                Map<String, String>  params = new HashMap<String, String>();
                                params.put("id_miel_eliminar",compraMiel.getIdMiel());
                                return params;
                            }

                        };
                        Volley.newRequestQueue(mContext).add(putRequest);
                    }
                });

                btn_cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fechaVenta;
        TextView vendedor;
        ImageButton btnEditarMielConvencional;
        ImageButton btnEliminarMielConvencional;

        public ViewHolder (View itemView) {
            super(itemView);
            fechaVenta    = itemView.findViewById(R.id.txt_fecha_venta_miel_convencional);
            vendedor = itemView.findViewById(R.id.txt_vendedor_miel_convencional);
            btnEditarMielConvencional   = itemView.findViewById(R.id.imgbtnEditarMielConvencional);
            btnEliminarMielConvencional = itemView.findViewById(R.id.imgbtnEliminarMielConvencional);
        }
    }
}
