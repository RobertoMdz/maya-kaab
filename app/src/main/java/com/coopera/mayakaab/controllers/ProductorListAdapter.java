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
import com.coopera.mayakaab.models.ProductorModel;
import com.coopera.mayakaab.views.AgregarProductorActivity;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductorListAdapter extends RecyclerView.Adapter<ProductorListAdapter.ViewHolder> implements Serializable {
    private Context mContext;
    private ArrayList<ProductorModel> mData;
    Gson gson = new Gson();

    public ProductorListAdapter(Context mContext, ArrayList<ProductorModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ProductorListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.productores_list_item,parent,false);
        return new ProductorListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductorListAdapter.ViewHolder holder, int position) {
        final ProductorModel productor = mData.get(position);

        holder.nombre.setText(productor.getNombre());
        holder.comunidad.setText(productor.getComunidad());

        holder.btnEditarProductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jsonProductor = gson.toJson(productor);

                Intent intent = new Intent(mContext, AgregarProductorActivity.class);
                intent.putExtra("isUpdate", true);
                intent.putExtra("productor", jsonProductor);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

            }
        });

        holder.btnEliminarProductor.setOnClickListener(new View.OnClickListener() {
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

                        final String url = Constants.URL_BASE+"productores.php?action=delete";
                        StringRequest putRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response) {
                                        dialog.dismiss();
                                        mData.remove(holder.getLayoutPosition());
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
                                params.put("id_productor_eliminar",productor.getId());
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
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView comunidad;
        ImageButton btnEditarProductor;
        ImageButton btnEliminarProductor;

        public ViewHolder (View itemView) {
            super(itemView);
            nombre    = itemView.findViewById(R.id.nombre_productor);
            comunidad = itemView.findViewById(R.id.comunidad_productor);
            btnEditarProductor   = itemView.findViewById(R.id.imgbtnEditarProductor);
            btnEliminarProductor = itemView.findViewById(R.id.imgbtnEliminarProductor);
        }
    }

}
