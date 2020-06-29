package com.empresadelivery.empresadelivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.empresadelivery.empresadelivery.adaptadores.Adaptadorfamilia;
import com.empresadelivery.empresadelivery.modelos.Crema;
import com.empresadelivery.empresadelivery.modelos.Familia;

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

public class Subirfamilia extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    String FileName = "myfile";
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dalogocremas);

        prefs = this.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        Button vadashboard=(Button) findViewById(R.id.vaadashboard);
        vadashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Subirfamilia.this, Dashboardempresa.class);
                startActivity(intent);





            }
        });
        String idempresa=prefs.getString("idempresa","");

             new Traertodaslasfamiliasendialogo().execute(idempresa);



        TextView nombrecrem=(TextView) findViewById(R.id.ingresanomberfamilia);
Button grabar=(Button)findViewById(R.id.grabarfamilia);
grabar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        if (nombrecrem.getText().toString().equals("")){



            BottomSheetFragment bottomSheetDialog = BottomSheetFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("test", "debes agregar un nombre");
            bundle.putString("nombreusuario", "");
            bundle.putString("imagen", getResources().getString(R.string.gifadmiracion));


            bottomSheetDialog.setArguments(bundle);
            bottomSheetDialog.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");


        }
        else {

            Familia adie = new Familia(1,Integer.parseInt(idempresa), nombrecrem.getText().toString(), "1" );
            new grabaradicionalempresa().execute(adie);
            nombrecrem.setText("");
        }

    }
});
    }
    public class Traertodaslasfamiliasendialogo extends AsyncTask<String, String, String> {

        HttpURLConnection conne;
        URL url = null;
        ProgressDialog pdLoading = new ProgressDialog(Subirfamilia.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tCargando familias");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                url = new URL("https://sodapop.pe/sugest/apitraerfamiliasporempresa.php");
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

                        .appendQueryParameter("idempresa", params[0]);
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
                Log.d("wiwo", e1.toString());
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
                    Log.d("waaaaaaa", result.toString());
                    return (result.toString());

                } else {

                    return ("Connection error");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conne.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            ArrayList<Familia> todoslospedidos = new ArrayList<>();

            todoslospedidos.clear();
            pdLoading.dismiss();
            try {
                JSONArray jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    Familia pedidofirebase = new Familia

                            (json_data.getInt("idfamilia"),

                                    json_data.getInt("idempresa") ,
                                    json_data.getString("nombrefamilia")
                                    , json_data.getString("estadofamilia"));
                    todoslospedidos.add(pedidofirebase);


                }

                final RecyclerView pedidose = findViewById(R.id.recyclerViewfamilial);

                Adaptadorfamilia adaptadore = new Adaptadorfamilia(todoslospedidos,getApplication());
                pedidose.setLayoutManager(new GridLayoutManager(Subirfamilia.this.getApplicationContext(), 1));

                pedidose.setAdapter(adaptadore);
                adaptadore.notifyDataSetChanged();


            } catch (JSONException e) {


                Log.d("erororor", e.toString());
            }


        }

    }
    public class grabaradicionalempresa extends AsyncTask<Familia, Void, String> {
        String resultado;
        HttpURLConnection conne;
        URL url = null;
        Familia ped;
        ProgressDialog pdLoading = new ProgressDialog(Subirfamilia.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tRegistrando familia..");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(Familia... params) {
            ped = params[0];
            try {
                url = new URL("https://sodapop.pe/sugest/apiguardarfamiliaempresa.php");
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
            try {
                conne = (HttpURLConnection) url.openConnection();
                conne.setReadTimeout(READ_TIMEOUT);
                conne.setConnectTimeout(CONNECTION_TIMEOUT);
                conne.setRequestMethod("POST");
                conne.setDoInput(true);
                conne.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()

                        .appendQueryParameter("nombrefamilia", String.valueOf(ped.getNombrefamilia()))
                        .appendQueryParameter("idempresa", String.valueOf(ped.getIdempresa()))
                        .appendQueryParameter("estadofamilia", String.valueOf(ped.getEstadofamilia()))

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
                Log.d("cirio", e1.toString());
                return null;
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
                    resultado = result.toString();
                    Log.d("paso", resultado.toString());
                    return resultado;

                } else {

                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("cirio2", e.toString());
                return null;
            } finally {
                conne.disconnect();
            }
            Log.d("cirio3", resultado);
            return resultado;

        }

        @Override
        protected void onPostExecute(final String resultado) {
            pdLoading.dismiss();
            super.onPostExecute(resultado);






            String idempresa = prefs.getString("idempresa", "");
            new Traertodaslasfamiliasendialogo().execute(idempresa);

            //Log.d("ii", resultado);
            //      String idempresa = prefs.getString("idempresa", "");



        }
    }

}