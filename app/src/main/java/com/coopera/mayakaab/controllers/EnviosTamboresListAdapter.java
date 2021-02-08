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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coopera.mayakaab.R;
import com.coopera.mayakaab.models.EnvioTamboresModel;
import com.coopera.mayakaab.views.AgregarEnviosTamboresActivity;
import com.google.gson.Gson;

import java.util.ArrayList;

public class EnviosTamboresListAdapter extends RecyclerView.Adapter<EnviosTamboresListAdapter.ViewHolder> {

    Context mContext;
    ArrayList<EnvioTamboresModel> itemsList;
    Gson gson =  new Gson();

    public EnviosTamboresListAdapter(Context context, ArrayList<EnvioTamboresModel> itemsList) {
        this.mContext = context;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.tambores_list_item,parent,false);
        return new EnviosTamboresListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EnviosTamboresListAdapter.ViewHolder holder, int position) {
        final EnvioTamboresModel envioTambor = itemsList.get(position);

        holder.emision.setText("Emision: " + envioTambor.getFechaRegisro());
        holder.codigo.setText("Codigo: " + envioTambor.getCodigo());
        holder.folio.setText("Folio: " + envioTambor.getNumeroFolio());

        holder.btnEditarMielConvencional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String envioObj = gson.toJson(envioTambor);
                Intent intent = new Intent(mContext, AgregarEnviosTamboresActivity.class);
                intent.putExtra("isUpdate",true);
                intent.putExtra("envio",envioObj);
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

                        dialog.dismiss();
                        itemsList.remove(holder.getLayoutPosition());
                        notifyItemRemoved(holder.getLayoutPosition());

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
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView emision;
        TextView codigo;
        TextView folio;
        ImageButton btnEditarMielConvencional;
        ImageButton btnEliminarMielConvencional;

        public ViewHolder (View itemView) {
            super(itemView);
            emision    = itemView.findViewById(R.id.txt_emision);
            codigo     = itemView.findViewById(R.id.txt_codigo);
            folio      = itemView.findViewById(R.id.txt_folio);
            btnEditarMielConvencional   = itemView.findViewById(R.id.imgbtnEditarEnvioTambor);
            btnEliminarMielConvencional = itemView.findViewById(R.id.imgbtnEliminarEnvioTambor);
        }
    }
}
