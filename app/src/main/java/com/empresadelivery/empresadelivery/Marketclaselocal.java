package com.empresadelivery.empresadelivery;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class Marketclaselocal implements GoogleMap.InfoWindowAdapter {

private static final String TAG = "CustomInfoWindowAdapter";
private LayoutInflater inflater;

public Marketclaselocal(LayoutInflater inflater){
        this.inflater = inflater;
        }
@Override
public View getInfoContents(final Marker m) {
        //Carga layout personalizado.
        View v = inflater.inflate(R.layout.marketsodapop, null);
        String[] info = m.getTitle().split("&");
        String url = m.getSnippet();
        ((TextView)v.findViewById(R.id.nombrelocal)).setText("SodaPop");
        ((TextView)v.findViewById(R.id.sloganlocal)).setText("Frescas Buenisimas y SEGURAS");
        ((TextView)v.findViewById(R.id.direccionlocal)).setText("Galeria del Solar");
        return v;
        }

@Override
public View getInfoWindow(Marker m) {
        return null;
        }


}