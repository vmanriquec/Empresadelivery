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
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.empresadelivery.empresadelivery.R;
import com.empresadelivery.empresadelivery.modelos.Descuentos;
import com.empresadelivery.empresadelivery.modelos.Tipodepagoempresa;
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
public class Adapadortipodeservicio extends RecyclerView.Adapter<Adapadortipodeservicio.AdaptadorViewHolder> {
    public Context mainContext;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    ArrayList<String> dataList;
    String foto;
    SharedPreferences prefs;
    String FileName ="myfile";
    private List<Tiposdeservicio> items;
    public Spinner vale;

    ArrayList<Descuentos> listaalmacen = new ArrayList<Descuentos>();

    Realm realm = Realm.getDefaultInstance();

    public Adapadortipodeservicio(List<Tiposdeservicio> items, Context contexto){
        this.mainContext=contexto;
        this.items=items;
        prefs = mainContext.getSharedPreferences(FileName, Context.MODE_PRIVATE);

        String idalmacenactiv = prefs.getString("idalmacenactivo", "");


    }
    static class AdaptadorViewHolder extends RecyclerView.ViewHolder{
        protected TextView nombretp;
        protected TextView idtp;


        protected Button eliminar;



        public AdaptadorViewHolder(View v){
            super(v);
            this.nombretp=(TextView) v.findViewById(R.id.nombretipodeservicio);
            this.idtp=(TextView) v.findViewById(R.id.idtipodeservicio);
            this.eliminar=(Button)v.findViewById(R.id.eliminartiposervicio);

        }
    }

    @Override
    public Adapadortipodeservicio.AdaptadorViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tarjetitatipodeservicio,viewGroup,false);
        return new Adapadortipodeservicio.AdaptadorViewHolder(v);
    }



    @Override
    public void onBindViewHolder(final Adapadortipodeservicio.AdaptadorViewHolder viewHolder, final int position) {
        final Tiposdeservicio item = items.get(position);
        viewHolder.itemView.setTag(item);
        viewHolder.idtp.setText(String.valueOf(item.getIdtipodeatencion()));
        viewHolder.nombretp.setText(item.getNombretipodeatencion());


        viewHolder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs = mainContext.getSharedPreferences(FileName, Context.MODE_PRIVATE);

                String idempresa = prefs.getString("idempresa", "");

                new eliminartipodesericioempresa().execute(viewHolder.idtp.getText().toString(),idempresa);
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



    private class eliminartipodesericioempresa extends AsyncTask<String, String, String> {
        ArrayList<Tiposdeservicio> people=new ArrayList<>();
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
                url = new URL("https://sodapop.pe/sugest/apieliminartipodeservicioempresa.php");
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

                        .appendQueryParameter("idtipodeatencion", params[0])
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







