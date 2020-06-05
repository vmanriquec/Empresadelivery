package com.empresadelivery.empresadelivery.adaptadores;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.empresadelivery.empresadelivery.Mapa;
import com.empresadelivery.empresadelivery.R;
import com.empresadelivery.empresadelivery.modelos.Descuentos;
import com.empresadelivery.empresadelivery.modelos.DescuentosRealm;
import com.empresadelivery.empresadelivery.modelos.Productos;
import com.empresadelivery.empresadelivery.modelos.Tipodepagoempresa;
import com.empresadelivery.empresadelivery.modelos.Tiposdepago;
import com.empresadelivery.empresadelivery.modelos.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class Adaptadortipodepago extends RecyclerView.Adapter<Adaptadortipodepago.AdaptadorViewHolder> {
    public Context mainContext;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    ArrayList<String> dataList;
    String foto;
    SharedPreferences prefs;
    String FileName ="myfile";
    private List<Tiposdepago> items;
    public Spinner vale;

    ArrayList<Descuentos> listaalmacen = new ArrayList<Descuentos>();

    Realm realm = Realm.getDefaultInstance();

    public Adaptadortipodepago(List<Tiposdepago> items, Context contexto){
        this.mainContext=contexto;
        this.items=items;


    }
    static class AdaptadorViewHolder extends RecyclerView.ViewHolder{
        protected TextView nombretp;
        protected TextView idtp;


        protected Button eliminar;



        public AdaptadorViewHolder(View v){
            super(v);
            this.nombretp=(TextView) v.findViewById(R.id.nombretiposdepagot);
            this.idtp=(TextView) v.findViewById(R.id.idtiposdepagot);
            this.eliminar=(Button)v.findViewById(R.id.eliminart);

        }
    }

    @Override
    public Adaptadortipodepago.AdaptadorViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tarjetatipodepago,viewGroup,false);
        return new Adaptadortipodepago.AdaptadorViewHolder(v);
    }



    @Override
    public void onBindViewHolder(final Adaptadortipodepago.AdaptadorViewHolder viewHolder, final int position) {
        final Tiposdepago item = items.get(position);
        viewHolder.itemView.setTag(item);
        viewHolder.idtp.setText(String.valueOf(item.getIdtiposdepago()));
        viewHolder.nombretp.setText(item.getNombretiposdepago());


        viewHolder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs = mainContext.getSharedPreferences(FileName, Context.MODE_PRIVATE);

                String idempresa = prefs.getString("idempresa", "");

new eliminartipopagoempresa().execute(viewHolder.idtp.getText().toString(),idempresa);
                items.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());


            }
        });


    }
    @Override
    public int getItemCount() {
        return items.size();
    }



    private class eliminartipopagoempresa extends AsyncTask<String, String, String> {
        ArrayList<Tipodepagoempresa> people=new ArrayList<>();
        private String[] strArrData = {"No Suggestions"};

        HttpURLConnection conne;
        URL url = null;
        ArrayList<Tipodepagoempresa> listaalmaceno = new ArrayList<Tipodepagoempresa>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                url = new URL("https://sodapop.pe/sugest/apieliminartipodepagoempresa.php");
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {
                conne = (HttpURLConnection) url.openConnection();
                conne.setReadTimeout(READ_TIMEOUT);
                conne.setConnectTimeout(CONNECTION_TIMEOUT);
                conne.setRequestMethod("POST");
                conne.setDoInput(true);
                conne.setDoOutput(true);

                // Append parameters to URL



                Uri.Builder builder = new Uri.Builder()

                        .appendQueryParameter("idtiposdepago", params[0])
                        .appendQueryParameter("idempresa", params[1])
                        ;

                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conne.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conne.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }
            try {
                int response_code = conne.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK) {
                    InputStream input = conne.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);

                    }
                    return (

                            result.toString()


                    );

                } else {
                    return("Connection error");
                }
            } catch (IOException e) {
                e.printStackTrace()                ;

                return e.toString();
            } finally {
                conne.disconnect();
            }
        }


        @Override
        protected void onPostExecute(String result) {
            Log.d("paso",result.toString());

        }

    }




}







