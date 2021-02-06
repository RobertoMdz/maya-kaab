package com.coopera.mayakaab.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coopera.mayakaab.R;
import com.coopera.mayakaab.models.GaleriaImagenModel;

import java.util.ArrayList;

public class GaleriaListAdapter extends RecyclerView.Adapter<GaleriaListAdapter.ViewHolder> {

    Context mContext;
    ArrayList<GaleriaImagenModel> listItems;

    public GaleriaListAdapter(Context mContext, ArrayList<GaleriaImagenModel> listItems) {
        this.mContext = mContext;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public GaleriaListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.galeria_list_item,parent,false);
        return new GaleriaListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GaleriaListAdapter.ViewHolder holder, int position) {
        final GaleriaImagenModel galeriaImagenModel = listItems.get(position);

        holder.imagen.setImageResource(galeriaImagenModel.getImagen());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imagen;

        public ViewHolder (View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagen_galeria);
        }
    }
}
