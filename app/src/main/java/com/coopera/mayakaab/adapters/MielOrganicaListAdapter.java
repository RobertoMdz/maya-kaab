package com.coopera.mayakaab.adapters;

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
import com.coopera.mayakaab.models.MielConvencionalModel;
import com.coopera.mayakaab.models.MielOrganicaModel;
import com.coopera.mayakaab.views.AgregarMielConvencionalActivity;
import com.coopera.mayakaab.views.AgregarMielOrganicaActivity;

import java.util.ArrayList;

public class MielOrganicaListAdapter extends RecyclerView.Adapter<MielOrganicaListAdapter.ViewHolder> {

    Context mContext;
    ArrayList<MielOrganicaModel> itemsList;

    public MielOrganicaListAdapter(Context mContext, ArrayList<MielOrganicaModel> itemsList) {
        this.mContext = mContext;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public MielOrganicaListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.mielorganica_list_item,parent,false);
        return new MielOrganicaListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MielOrganicaListAdapter.ViewHolder holder, int position) {
        final MielOrganicaModel mielOrganica = itemsList.get(position);

        holder.fechaVenta.setText(mielOrganica.getFecha());
        holder.vendedor.setText(mielOrganica.getNombre());

        holder.btnEditarMielOrganica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AgregarMielOrganicaActivity.class);
                //intent.putExtra("update","1");
                //intent.putExtra("id_arbol",productor.getId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

            }
        });

        holder.btnEliminarMielOrganica.setOnClickListener(new View.OnClickListener() {
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
        TextView fechaVenta;
        TextView vendedor;
        ImageButton btnEditarMielOrganica;
        ImageButton btnEliminarMielOrganica;

        public ViewHolder (View itemView) {
            super(itemView);
            fechaVenta    = itemView.findViewById(R.id.txt_fecha_venta_miel_organica);
            vendedor = itemView.findViewById(R.id.txt_vendedor_miel_organica);
            btnEditarMielOrganica   = itemView.findViewById(R.id.imgbtnEditarMielOrganica);
            btnEliminarMielOrganica = itemView.findViewById(R.id.imgbtnEliminarMielOrganica);
        }
    }
}
