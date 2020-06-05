package com.empresadelivery.empresadelivery;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.empresadelivery.empresadelivery.adaptadores.Adaptadorclientes;
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

public class Nuevodeclientes extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevodeclientes);
        new traerproductosporidalmacenidfamilia().execute("1");
    }

    private class traerproductosporidalmacenidfamilia extends AsyncTask<String, String, String> {
        ArrayList<Usuario> people = new ArrayList<>();
        private String[] strArrData = {"No Suggestions"};
        ProgressDialog pdLoading = new ProgressDialog(Nuevodeclientes.this);
        HttpURLConnection conne;
        URL url = null;
        ArrayList<Usuario> listaalmaceno = new ArrayList<Usuario>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tCargando todos");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("https://sodapop.pe/sugest/apitraerclientesmaestra.php");
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
                        .appendQueryParameter("idusuario", params[0]);
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
                    return ("Connection error");
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("pasoe", e.toString());
                return e.toString();
            } finally {
                conne.disconnect();
            }
        }
        @Override
        protected void onPostExecute(String result) {

            people.clear();
            RecyclerView recycler = (RecyclerView) findViewById(R.id.recyclerdeclientes);


            ArrayList<String> dataList = new ArrayList<String>();
            Usuario meso;
            if (result.equals("no rows")) {
                Toast.makeText(Nuevodeclientes.this.getApplicationContext(), "no existen datos a mostrar", Toast.LENGTH_LONG).show();

            } else {

                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.optJSONObject(i);
                        meso = new Usuario(json_data.getInt("idusuario"), json_data.getString("nombreusuario"), json_data.getString("claveusuario")
                                , json_data.getString("estadousuario"), json_data.getInt(("idalmacen")), json_data.getString("montodescuento")
                                , json_data.getString("nombredescuento"), json_data.getString("imagen")
                                , json_data.getString("apellidos"), json_data.getString("idfirebase"), json_data.getString("telefono"),
                                json_data.getString("contrasena"), json_data.getString("correo"), json_data.getString("direccion"),
                                json_data.getString("longitud")
                                , json_data.getString("latitud"), json_data.getString("referencia"));

                        people.add(meso);
                    }

                    strArrData = dataList.toArray(new String[dataList.size()]);
                    adapter = new Adaptadorclientes(people, Nuevodeclientes.this.getApplicationContext());
                    recycler.setLayoutManager(new GridLayoutManager(Nuevodeclientes.this.getApplicationContext(), 1));
                    recycler.setAdapter(adapter);
                } catch (JSONException e) {
                    Log.d("paso", e.toString());
                }
            }
            pdLoading.dismiss();

        }


    }}


