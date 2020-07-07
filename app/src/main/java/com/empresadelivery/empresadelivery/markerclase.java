package com.empresadelivery.empresadelivery;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class markerclase implements GoogleMap.InfoWindowAdapter {

    private static final String TAG = "CustomInfoWindowAdapter";
    private LayoutInflater inflater;

    public markerclase(LayoutInflater inflater){
        this.inflater = inflater;
    }

    @Override
    public View getInfoContents(final Marker m) {
        //Carga layout personalizado.
        View v = inflater.inflate(R.layout.markerlayout, null);
        String[] info = m.getTitle().split("&");
        String url = m.getSnippet();
        ((TextView)v.findViewById(R.id.info_window_nombre)).setText("Hola");
        ((TextView)v.findViewById(R.id.info_window_placas)).setText("Esta sera tu direccion");
        ((TextView)v.findViewById(R.id.info_window_estado)).setText("Estado: Habilitado");
        return v;
    }

    @Override
    public View getInfoWindow(Marker m) {
        return null;
    }

}