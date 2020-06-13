package com.empresadelivery.empresadelivery.adaptadores;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.empresadelivery.empresadelivery.R;
import com.empresadelivery.empresadelivery.modelos.Adicional;
import com.empresadelivery.empresadelivery.modelos.Descuentos;
import com.empresadelivery.empresadelivery.modelos.Tiposdeservicio;

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

public class Adaptadoradicionales  extends RecyclerView.Adapter<Adaptadoradicionales.AdaptadorViewHolder> {
    public Context mainContext;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    ArrayList<String> dataList;
    String foto;
    SharedPreferences prefs;
    String FileName ="myfile";
    private List<Adicional> items;
    public Spinner vale;
public String valordeestado;
    ArrayList<Descuentos> listaalmacen = new ArrayList<Descuentos>();

    Realm realm = Realm.getDefaultInstance();

    public Adaptadoradicionales(List<Adicional> items, Context contexto){
        this.mainContext=contexto;
        this.items=items;
        prefs = mainContext.getSharedPreferences(FileName, Context.MODE_PRIVATE);


    }
    static class AdaptadorViewHolder extends RecyclerView.ViewHolder{
        protected TextView nombreadiconalempresa;
        protected TextView idadionalempresa;

        protected TextView precioadionalempresa;
protected Switch chek;
        protected Button eliminaradiconalempresa;
protected Switch check;


        public AdaptadorViewHolder(View v){
            super(v);
            this.idadionalempresa=(TextView) v.findViewById(R.id.idacionalempresa);
            this.nombreadiconalempresa=(TextView) v.findViewById(R.id.nombreadicionalempresa);
            this.precioadionalempresa=(TextView) v.findViewById(R.id.precioadicionalempressa);
            this.eliminaradiconalempresa=(Button)v.findViewById(R.id.eliminaradicionalempresa);
            this.check=(Switch)v.findViewById(R.id.switchadicionalempresal2);
        }
    }

    @Override
    public Adaptadoradicionales.AdaptadorViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tarjetitaadicionales,viewGroup,false);
        return new Adaptadoradicionales.AdaptadorViewHolder(v);
    }



    @Override
    public void onBindViewHolder(final Adaptadoradicionales.AdaptadorViewHolder viewHolder, final int position) {
        final Adicional item = items.get(position);
        viewHolder.itemView.setTag(item);
        viewHolder.idadionalempresa.setText(String.valueOf(item.getIdadicional()));
        viewHolder.nombreadiconalempresa.setText(item.getNombreadicional());
        viewHolder.precioadionalempresa.setText(" S/. "+String.valueOf(item.getPrecioadicional()));

if(item.getEstadoadicional().toString().equals("1"))
{
    viewHolder.check.setChecked(true);


}else {
    viewHolder.check.setChecked(false);
}


        viewHolder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefs = mainContext.getSharedPreferences(FileName, Context.MODE_PRIVATE);

                String idempresa = prefs.getString("idempresa", "");

                if (isChecked) {

                    new actualizarestado().execute(viewHolder.idadionalempresa.getText().toString(),idempresa,"1");
                } else {
                    // muestra otro activity
                    new actualizarestado().execute(viewHolder.idadionalempresa.getText().toString(),idempresa,"0");
                }
            }
        });

        viewHolder.eliminaradiconalempresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs = mainContext.getSharedPreferences(FileName, Context.MODE_PRIVATE);

                String idempresa = prefs.getString("idempresa", "");

                new eliminaradicionaempresa().execute(viewHolder.idadionalempresa.getText().toString(),idempresa);
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



    private class eliminaradicionaempresa extends AsyncTask<String, String, String> {
        ArrayList<Adicional> people=new ArrayList<>();
        private String[] strArrData = {"No Suggestions"};

        HttpURLConnection conne;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                url = new URL("https://sodapop.pe/sugest/apieliminaradicionalporempresa.php");
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

                        .appendQueryParameter("idadicional", params[0])
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



    private class actualizarestado extends AsyncTask<String, String, String> {
        ArrayList<Adicional> people=new ArrayList<>();
        private String[] strArrData = {"No Suggestions"};

        HttpURLConnection conne;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                url = new URL("https://sodapop.pe/sugest/apiactualizarestadodeadicionalesporempresa.php");
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

                        .appendQueryParameter("idadicional", params[0])
                        .appendQueryParameter("idempresa", params[1])
                        .appendQueryParameter("estadoadicional", params[2])
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








