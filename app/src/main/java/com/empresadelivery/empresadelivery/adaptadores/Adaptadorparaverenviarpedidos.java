package com.empresadelivery.empresadelivery.adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.empresadelivery.empresadelivery.R;
import com.empresadelivery.empresadelivery.modelos.DetallepedidoRealmFirebase;

import java.util.ArrayList;
import java.util.List;


public class Adaptadorparaverenviarpedidos extends RecyclerView.Adapter<Adaptadorparaverenviarpedidos.AdaptadorViewHolder> {
    private Context mainContext;
    String foto;
    SharedPreferences prefs;
    String FileName ="myfile";
    private List<DetallepedidoRealmFirebase> items;
    public Adaptadorparaverenviarpedidos(ArrayList<DetallepedidoRealmFirebase> items, Context contexto){
        this.mainContext=contexto;
        this.items=items;


    }
    static class AdaptadorViewHolder extends RecyclerView.ViewHolder{
        protected TextView producto;
        protected TextView precio;
        protected TextView cremas;
        protected TextView adicional;
        protected TextView subtotal;
        protected TextView comentario;
        protected TextView cantidad;



        public AdaptadorViewHolder(View v){
            super(v);
            this.producto=(TextView) v.findViewById(R.id.producto);
            this.precio=(TextView) v.findViewById(R.id.precio);
            this.cremas=(TextView) v.findViewById(R.id.cremas);
            this.adicional=(TextView) v.findViewById(R.id.adicional);
            this.subtotal=(TextView) v.findViewById(R.id.subtotal);
            this.comentario=(TextView) v.findViewById(R.id.comentario);
            this.cantidad=(TextView) v.findViewById(R.id.cantidad);

        }
    }


    @Override
    public Adaptadorparaverenviarpedidos.AdaptadorViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tarjetitatarjetapedidoenviado,viewGroup,false);
        return new Adaptadorparaverenviarpedidos.AdaptadorViewHolder(v);
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final Adaptadorparaverenviarpedidos.AdaptadorViewHolder viewHolder, final int position) {
        final DetallepedidoRealmFirebase item = items.get(position);
        viewHolder.itemView.setTag(item);



        viewHolder.producto.setText(String.valueOf(item.getNombreproductorealm()));
        viewHolder.precio.setText(String.valueOf(item.getPrecventa()));
        viewHolder.cremas.setText(String.valueOf(item.getCremas()));
        viewHolder.adicional.setText(String.valueOf(item.getImagenreal()));
        viewHolder.subtotal.setText(String.valueOf(item.getSubtotal()));
        viewHolder.comentario.setText(String.valueOf(item.getComentariococina()));
        viewHolder.cantidad.setText(String.valueOf(item.getCantidad()));







    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

