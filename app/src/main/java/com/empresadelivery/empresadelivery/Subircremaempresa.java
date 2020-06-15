package com.empresadelivery.empresadelivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.empresadelivery.empresadelivery.adaptadores.Adaptadoradicionales;
import com.empresadelivery.empresadelivery.adaptadores.Adaptadorcrema;
import com.empresadelivery.empresadelivery.modelos.Adicional;
import com.empresadelivery.empresadelivery.modelos.Crema;
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

public class Subircremaempresa extends AppCompatActivity {
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    SharedPreferences prefs;
    String FileName ="myfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subircremaempresa);


        prefs =getApplication().getSharedPreferences(FileName, Context.MODE_PRIVATE);
        String idempresa = prefs.getString("idempresa", "");
        new traertodosloadicionalesempresa().execute(idempresa);

        Button guardarademp=(Button)findViewById(R.id.guardarcremaempresa);
        TextView nombrecrem=(TextView) findViewById(R.id.nombrecremaempresal);



        guardarademp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idempresa = prefs.getString("idempresa", "");
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
     Crema adie = new Crema(1, nombrecrem.getText().toString(), "1", Integer.parseInt(idempresa));
     new grabaradicionalempresa().execute(adie);
     nombrecrem.setText("");
 }
            }
        });
    }


    public class grabaradicionalempresa extends AsyncTask<Crema, Void, String> {
        String resultado;
        HttpURLConnection conne;
        URL url = null;
        Crema ped;
        ProgressDialog pdLoading = new ProgressDialog(Subircremaempresa.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tRegistrando crema..");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(Crema... params) {
            ped = params[0];
            try {
                url = new URL("https://sodapop.pe/sugest/apiguardarcremaempresa.php");
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
.appendQueryParameter("nombrecrema", String.valueOf(ped.getNombrecrema()))
                        .appendQueryParameter("estadocrema", String.valueOf(ped.getEstadocrema()))
                        .appendQueryParameter("idempresa", String.valueOf(ped.getIdempresa()))

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
            new traertodosloadicionalesempresa().execute(idempresa);

            //Log.d("ii", resultado);
            //      String idempresa = prefs.getString("idempresa", "");



        }
    }




    private class traertodosloadicionalesempresa extends AsyncTask<String, String, String> {
        ArrayList<Crema> people = new ArrayList<>();
        private String[] strArrData = {"No Suggestions"};
        ProgressDialog pdLoading = new ProgressDialog(Subircremaempresa.this);
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
                url = new URL("https://sodapop.pe/sugest/apitraertodoslascremasporempresa.php");
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
            RecyclerView recycler = (RecyclerView) findViewById(R.id.recyclercremaempresa);


            ArrayList<String> dataList = new ArrayList<String>();
            Crema meso;
            if (result.equals("no rows")) {
                Toast.makeText(Subircremaempresa.this.getApplicationContext(), "no existen datos a mostrar", Toast.LENGTH_LONG).show();

            } else {

                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.optJSONObject(i);
                        meso = new Crema(json_data.getInt("idcrema"),json_data.getString("nombrecrema"),
                                (json_data.getString("estadocrema")),json_data.getInt("idempresa"));

                        people.add(meso);
                    }

                    strArrData = dataList.toArray(new String[dataList.size()]);
                    Adaptadorcrema adapter = new Adaptadorcrema(people, Subircremaempresa.this.getApplicationContext());
                    recycler.setLayoutManager(new GridLayoutManager(Subircremaempresa.this.getApplicationContext(), 1));
                    recycler.setAdapter(adapter);
                } catch (JSONException e) {
                    Log.d("paso", e.toString());
                }
            }
            pdLoading.dismiss();

        }


    }

}


