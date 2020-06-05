package com.empresadelivery.empresadelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.empresadelivery.empresadelivery.modelos.Crudtipodeserviciosrealm;
import com.empresadelivery.empresadelivery.modelos.Crudtiposdepagorealm;
import com.empresadelivery.empresadelivery.modelos.Tiposdepago;
import com.empresadelivery.empresadelivery.modelos.Tiposdeservicio;
import com.empresadelivery.empresadelivery.modelos.Tiposdeserviciorealm;
import com.empresadelivery.empresadelivery.modelos.Tiposedepagorealm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Iniciosistema extends AppCompatActivity {
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciosistema);
        String FileName = "myfile";
        SharedPreferences prefs;

        Realm.init(getApplicationContext());
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("pedido.realm")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(realmConfig);
        eliminartododerealm();

        prefs = this.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        String idempresa=prefs.getString("idempresa","");
        new cargartiposdepago().execute();
        new cargartiposdeservicio().execute();
        if(idempresa.equals("")){

            Intent intent = new Intent (Iniciosistema.this, Datosdeempresa.class);
            startActivityForResult(intent, 0);

        }
        else{

            Intent intent = new Intent (Iniciosistema.this, Dashboardempresa.class);
            startActivityForResult(intent, 0);

        }
    }
    private class cargartiposdepago extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(Iniciosistema.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tCargando tipos de pago");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                url = new URL("https://www.sodapop.pe/sugest/apitraertodoslostiposdepago.php");
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);
                conn.connect();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }
            try {
                int response_code = conn.getResponseCode();

                if (response_code == HttpURLConnection.HTTP_OK) {

                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return (result.toString());
                } else {
                    return("Connection error");
                }
            } catch (IOException e) {
                e.printStackTrace();

                return e.toString();
            } finally {
                conn.disconnect();
            }
        }
        @Override
        protected void onPostExecute(String result) {



            Tiposdepago mes;
            if(result.equals("no rows")) {
                Toast.makeText(getApplicationContext(),"no existen datos a mostrar",Toast.LENGTH_LONG).show();

            }else{

                try {

                    JSONArray jArray = new JSONArray(result);

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);

                        mes = new Tiposdepago(json_data.getInt("idtiposdepago"), json_data.getString("nombretiposdepago"), json_data.getString("estadotiposdepago"));
                        grabartiposdepago(mes.getIdtiposdepago(),mes.getNombretiposdepago(),mes.getEstadotiposdepago());
                    }

                } catch (JSONException e) {
                }

            }
            pdLoading.dismiss();


        }

    }
    public   void grabartiposdepago(final int idtiposdepago, final String nombretiposdepago
            , final String estadotiposdepago) {
        Realm.init(getApplicationContext());
        Realm pedido = Realm.getDefaultInstance();
        pedido.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm pedido) {
                int index = Crudtiposdepagorealm.calculateIndex();

                Tiposedepagorealm tipos = pedido.createObject(Tiposedepagorealm.class, index);
                tipos.setIdtiposdepago(idtiposdepago);
                tipos.setNombretiposdepago(nombretiposdepago);
                tipos.setEstadotiposdepago(estadotiposdepago);


            }

        });

    }
    private void eliminartododerealm() {
        Realm pedido = Realm.getDefaultInstance();
        pedido.beginTransaction();
        pedido.deleteAll();
        pedido.commitTransaction();

    }
    public   void grabartiposdeservicio(final int idtipodeatencion, final String nombretipodeatencion
            , final String estadotipodeatencion) {
        Realm.init(getApplicationContext());
        Realm pedido = Realm.getDefaultInstance();
        pedido.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm pedido) {
                int index = Crudtipodeserviciosrealm.calculateIndex();

                Tiposdeserviciorealm tipos = pedido.createObject(Tiposdeserviciorealm.class, index);
                tipos.setIdtipodeatencion(idtipodeatencion);
                tipos.setNombretipodeatencion(nombretipodeatencion);
                tipos.setEstadotipodeatencion(estadotipodeatencion);


            }

        });

    }
    private class cargartiposdeservicio extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(Iniciosistema.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tCargando tipos de atencion");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                url = new URL("https://www.sodapop.pe/sugest/apitraertodoslostiposdeatencion.php");
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);
                conn.connect();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }
            try {
                int response_code = conn.getResponseCode();

                if (response_code == HttpURLConnection.HTTP_OK) {

                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return (result.toString());
                } else {
                    return("Connection error");
                }
            } catch (IOException e) {
                e.printStackTrace();

                return e.toString();
            } finally {
                conn.disconnect();
            }
        }
        @Override
        protected void onPostExecute(String result) {



            Tiposdeservicio mes;
            if(result.equals("no rows")) {
                Toast.makeText(getApplicationContext(),"no existen datos a mostrar",Toast.LENGTH_LONG).show();

            }else{

                try {

                    JSONArray jArray = new JSONArray(result);

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);

                        mes = new Tiposdeservicio(json_data.getInt("idtipodeatencion"), json_data.getString("nombretipodeatencion"), json_data.getString("estadotipodeatencion")
                        , json_data.getString("imagentipoatencion"));
                        grabartiposdeservicio(mes.getIdtipodeatencion(),mes.getNombretipodeatencion(),mes.getEstadotipodeatencion());
                    }

                } catch (JSONException e) {
                }

            }
            pdLoading.dismiss();


        }

    }
}