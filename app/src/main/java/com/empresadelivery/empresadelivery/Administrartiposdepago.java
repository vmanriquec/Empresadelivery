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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.empresadelivery.empresadelivery.adaptadores.Adapadortipodeservicio;
import com.empresadelivery.empresadelivery.adaptadores.Adaptadortipodepago;
import com.empresadelivery.empresadelivery.modelos.Tipodepagoempresa;
import com.empresadelivery.empresadelivery.modelos.Tipodeservicioempresa;
import com.empresadelivery.empresadelivery.modelos.Tiposdepago;
import com.empresadelivery.empresadelivery.modelos.Tiposdeservicio;
import com.empresadelivery.empresadelivery.modelos.Tiposdeserviciorealm;
import com.empresadelivery.empresadelivery.modelos.Tiposedepagorealm;

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

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class Administrartiposdepago extends AppCompatActivity {
    String FileName = "myfile";
    SharedPreferences prefs;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialogopedidos);
        Button vaadashboardw=(Button) findViewById(R.id.vaadashbo);
        vaadashboardw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Administrartiposdepago.this, Dashboardempresa.class);
                startActivity(intent);

            }
        });
        Realm.init(getApplicationContext());
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("pedido.realm")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(realmConfig);
        prefs = this.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        String idempresa=prefs.getString("idempresa","");
new manejartiposdepago().execute(idempresa);
new manejartiposdeservicio().execute(idempresa);

        Spinner des=(Spinner)findViewById(R.id.spintiposdepagost) ;
        Realm pedido = Realm.getDefaultInstance();
        RealmResults<Tiposedepagorealm> results =
                pedido.where(Tiposedepagorealm.class)
                        .findAll();
        ArrayList<Tiposedepagorealm> listaalmacen = new ArrayList<Tiposedepagorealm>();
        RealmResults<Tiposedepagorealm> resultst =
                pedido.where(Tiposedepagorealm.class)
                        .findAll();
        ArrayAdapter<Tiposedepagorealm> adaptadorlo= new ArrayAdapter<Tiposedepagorealm>(Administrartiposdepago.this,  android.R.layout.simple_spinner_item,resultst );
        des.setAdapter(adaptadorlo);






        Spinner desa=(Spinner)findViewById(R.id.spinnerdedespacho) ;
        RealmResults<Tiposdeserviciorealm> resultsa =
                pedido.where(Tiposdeserviciorealm.class)
                        .findAll();
        ArrayList<Tiposdeserviciorealm> listaalmacena = new ArrayList<Tiposdeserviciorealm>();
        RealmResults<Tiposdeserviciorealm> resultsta =
                pedido.where(Tiposdeserviciorealm.class)
                        .findAll();
        ArrayAdapter<Tiposdeserviciorealm> adaptadorloa= new ArrayAdapter<Tiposdeserviciorealm>(Administrartiposdepago.this,  android.R.layout.simple_spinner_item,resultsta );
        desa.setAdapter(adaptadorloa);




        Button adic = (Button) findViewById(R.id.adicionartipodepago);
        adic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String al =des.getItemAtPosition(des.getSelectedItemPosition()).toString();
                String mesei=al;
                int g= mesei.length();
                String mesi = mesei.substring(0,2);

                String  idtipodepago=mesi.trim();
                prefs = getApplication().getSharedPreferences(FileName, Context.MODE_PRIVATE);

                String idempresa=prefs.getString("idempresa","");
                 Tipodepagoempresa gt=new Tipodepagoempresa(0,Integer.parseInt(idempresa),Integer.parseInt(idtipodepago),"habilitado");
                new grabartipodepagoempresa().execute(gt);


            }
        });
        Button adic2 = (Button) findViewById(R.id.adicionartipodepago2);
        adic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String al =desa.getItemAtPosition(desa.getSelectedItemPosition()).toString();
                String mesei=al;
                int g= mesei.length();
                String mesi = mesei.substring(0,2);

                String  idtipodeservicio=mesi.trim();
                prefs = getApplication().getSharedPreferences(FileName, Context.MODE_PRIVATE);

                String idempresa=prefs.getString("idempresa","");

                Tipodeservicioempresa gta=new Tipodeservicioempresa(Integer.parseInt(idempresa),Integer.parseInt(idtipodeservicio),"no","habilitado");
                new grabartipodeservicioempresa().execute(gta);


            }
        });








    }

    public class manejartiposdepago extends AsyncTask<String, String, String> {

        HttpURLConnection conne;
        URL url = null;
        ProgressDialog pdLoading = new ProgressDialog(Administrartiposdepago.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tMostrando tipos de pago de la empresa");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                //  url = new URL("https://sodapop.pe/sugest/apitraertodoslostiposdepago.php");
                url = new URL("https://sodapop.pe/sugest/apitraertiposdepagoporempresa.php");
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
             ArrayList<Tiposdepago> todoslotiposdepago = new ArrayList<>();
            todoslotiposdepago.clear();
            pdLoading.dismiss();
            try {



                JSONArray jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    Tiposdepago pedidofirebase = new Tiposdepago(
                            (json_data.getInt("idtiposdepago")), json_data.getString("nombretiposdepago"), json_data.getString("estadotiposdepago"));
                    todoslotiposdepago.add(pedidofirebase);


                }

                final RecyclerView pedidose = findViewById(R.id.listadepedidos);

                Adaptadortipodepago adaptadore = new Adaptadortipodepago(todoslotiposdepago, Administrartiposdepago.this);
                pedidose.setLayoutManager(new GridLayoutManager(Administrartiposdepago.this.getApplicationContext(), 1));

                pedidose.setAdapter(adaptadore);

                adaptadore.notifyDataSetChanged();

            } catch (JSONException e) {
                Log.d("erororor", e.toString());
            }
        }

    }
    public class grabartipodepagoempresa extends AsyncTask<Tipodepagoempresa, Void, String> {
        String resultado;
        HttpURLConnection conne;
        URL url = null;
        Tipodepagoempresa ped;
        ProgressDialog pdLoading = new ProgressDialog(Administrartiposdepago.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tguardando..");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(Tipodepagoempresa... params) {
            ped=params[0];
            try {
                url = new URL("https://sodapop.pe/sugest/apiguardartipodepagoempresa.php");
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

                        .appendQueryParameter("idempresa",String.valueOf(ped.getIdempresa()))
                        .appendQueryParameter("idtiposdepago",String.valueOf(ped.getIdtiposdepago()))
                        .appendQueryParameter("estadotiposdepago",String.valueOf(ped.getEstadotiposdepago()))


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
                Log.d("cirio",e1.toString());
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
                    resultado=result.toString();
                    Log.d("paso",resultado.toString());
                    return resultado;

                } else {

                }
            } catch (IOException e) {
                e.printStackTrace()                ;
                Log.d("cirio2",e.toString());
                return null;
            } finally {
                conne.disconnect();
            }
            Log.d("cirio3",resultado);
            return resultado;

        }
        @Override
        protected void onPostExecute(final String resultado) {
            pdLoading.dismiss();
            super.onPostExecute(resultado);
            String idempresa=prefs.getString("idempresa","");
            new manejartiposdepago().execute(idempresa);
            if(resultado.equals("true")){
                Log.d("ii", resultado);







            }

        }
    }


    public class manejartiposdeservicio extends AsyncTask<String, String, String> {

        HttpURLConnection conne;
        URL url = null;
        ProgressDialog pdLoading = new ProgressDialog(Administrartiposdepago.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tMostrando tipos de atencion de la empresa");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                //  url = new URL("https://sodapop.pe/sugest/apitraertodoslostiposdepago.php");
                url = new URL("https://sodapop.pe/sugest/apitraertiposdeatencionporempresa.php");
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
            ArrayList<Tiposdeservicio> todoslotiposdeservicio = new ArrayList<>();
            todoslotiposdeservicio.clear();
            pdLoading.dismiss();
            try {
                JSONArray jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    Tiposdeservicio pedidofirebase = new Tiposdeservicio(

                            (json_data.getInt("idtipodeatencion")), json_data.getString("nombretipodeatencion"), json_data.getString("estadotipodeatencion"),
                            json_data.getString("imagentipoatencion"));
                    todoslotiposdeservicio.add(pedidofirebase);


                }

                final RecyclerView pedidose = findViewById(R.id.recyclerdedespacho);

                Adapadortipodeservicio adaptadore = new Adapadortipodeservicio(todoslotiposdeservicio, Administrartiposdepago.this);
                pedidose.setLayoutManager(new GridLayoutManager(Administrartiposdepago.this.getApplicationContext(), 1));

                pedidose.setAdapter(adaptadore);

                adaptadore.notifyDataSetChanged();

            } catch (JSONException e) {
                Log.d("erororor", e.toString());
            }
        }

    }
    public class grabartipodeservicioempresa extends AsyncTask<Tipodeservicioempresa, Void, String> {
        String resultado;
        HttpURLConnection conne;
        URL url = null;
        Tipodeservicioempresa ped;
        ProgressDialog pdLoading = new ProgressDialog(Administrartiposdepago.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tguardando..");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(Tipodeservicioempresa... params) {
            ped=params[0];
            try {
                url = new URL("https://sodapop.pe/sugest/apiguardartipodeservicioempresa.php");
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

                        .appendQueryParameter("idempresa",String.valueOf(ped.getIdempresa()))
                        .appendQueryParameter("idtipodeatencion",String.valueOf(ped.getIdtipodeatencion()))
                        .appendQueryParameter("estadoidtipodeatencion",String.valueOf(ped.getEstadotipodeatencion()))


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
                Log.d("cirio",e1.toString());
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
                    resultado=result.toString();
                    Log.d("paso",resultado.toString());
                    return resultado;

                } else {

                }
            } catch (IOException e) {
                e.printStackTrace()                ;
                Log.d("cirio2",e.toString());
                return null;
            } finally {
                conne.disconnect();
            }
            Log.d("cirio3",resultado);
            return resultado;

        }
        @Override
        protected void onPostExecute(final String resultado) {
            pdLoading.dismiss();
            super.onPostExecute(resultado);
            String idempresa=prefs.getString("idempresa","");
            new manejartiposdeservicio().execute(idempresa);
            if(resultado.equals("true")){
                Log.d("ii", resultado);







            }

        }
    }

}