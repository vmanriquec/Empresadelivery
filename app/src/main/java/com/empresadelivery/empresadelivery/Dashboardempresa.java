package com.empresadelivery.empresadelivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.empresadelivery.empresadelivery.modelos.CruddescuentosRealm;
import com.empresadelivery.empresadelivery.modelos.Crudtiposdepagorealm;
import com.empresadelivery.empresadelivery.modelos.Descuentos;
import com.empresadelivery.empresadelivery.modelos.DescuentosRealm;
import com.empresadelivery.empresadelivery.modelos.Tiposdepago;
import com.empresadelivery.empresadelivery.modelos.Tiposedepagorealm;
import com.squareup.picasso.Picasso;

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
import jp.wasabeef.picasso.transformations.CropSquareTransformation;

public class Dashboardempresa extends AppCompatActivity {
    String FileName = "myfile";
    SharedPreferences prefs;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    ImageView logoempresa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboardempresa);

        new cargardescuentos().execute();
        new cargartiposdepago().execute();

        Realm.init(getApplicationContext());

        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("pedido.realm")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(realmConfig);


        prefs = this.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        String idempresa=prefs.getString("idempresa","");
        String razonsocialempresa=prefs.getString("razonsocialempresa","");
        String direccionempresa=prefs.getString("direccionempresa","");
        String estadoempresa=prefs.getString("estadoempresa","");
        String sloganempresa=prefs.getString("sloganempresa","");
        String nombreadministrador=prefs.getString("idempresa","");
        String idrubroempresa=prefs.getString("idrubroempresa","");
        String montominimodeventa=prefs.getString("montominimodeventa","");
        String imagenempresa=prefs.getString("imagenempresa","");
        TextView razon=(TextView) findViewById(R.id.razonsocialdashboard);
        TextView dire=(TextView) findViewById(R.id.direccionempresadashboard);

        logoempresa=(ImageView) findViewById(R.id.logoempresa);
        Picasso.get().load(imagenempresa).transform(new CropSquareTransformation()).resize(200, 200)
                .into( logoempresa);
razon.setText(razonsocialempresa);
dire.setText(direccionempresa);
//new cargartiposdepago().execute();
        CardView irapedido=(CardView) findViewById(R.id.cardirapedido);
        irapedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboardempresa.this, MainActivity.class);
                startActivity(intent);

            }
        });
        CardView editarempresa=(CardView) findViewById(R.id.cardempresa);
        editarempresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboardempresa.this, EditarEmpresa.class);
                startActivity(intent);

            }


    });

        CardView cardtiposdepago=(CardView) findViewById(R.id.cardtiposdepagoair);
        cardtiposdepago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboardempresa.this, Administrartiposdepago.class);
                startActivity(intent);

            }


        });


        CardView cardcliente=(CardView) findViewById(R.id.cardclientes);
        cardcliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboardempresa.this, Manejodeusuarios.class);
                startActivity(intent);

            }


        });
        CardView carproductos=(CardView) findViewById(R.id.cardproductos);
        carproductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboardempresa.this, Listaproductos.class);
                startActivity(intent);

            }


        });
    }
    private class cargardescuentos extends AsyncTask<String, String, String> {

      ProgressDialog pdLoading = new ProgressDialog(Dashboardempresa.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tCargando Descuentos");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                url = new URL("https://www.sodapop.pe/sugest/apitraertodoslosdescuentos.php");
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



            Descuentos mes;
            if(result.equals("no rows")) {
                Toast.makeText(getApplicationContext(),"no existen datos a mostrar",Toast.LENGTH_LONG).show();

            }else{

                try {

                    JSONArray jArray = new JSONArray(result);

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                        mes = new Descuentos(json_data.getInt("idvaledescuento"), json_data.getString("nombredescuento"), json_data.getString("montodescuento"), json_data.getString("estadodescuento"));
                        grabatodoslosdecuentos(mes.getIdvaledescuento(),mes.getNombredescuento(),mes.getMontodescuento(),mes.getEstadodescuento());
                    }





                } catch (JSONException e) {
                }

            }
            pdLoading.dismiss();


        }

    }



    private class cargartiposdepago extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(Dashboardempresa.this);
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




    public   void grabatodoslosdecuentos(final int idvaledescuento, final String nombredescuento
            , final String montodescuento, final String estadodescuento) {
        Realm pedido = Realm.getDefaultInstance();
        pedido.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm pedido) {
                int index = CruddescuentosRealm.calculateIndex();

                DescuentosRealm descuentorealm = pedido.createObject(DescuentosRealm.class, index);
                descuentorealm.setIdvaledescuento(idvaledescuento);
                descuentorealm.setNombredescuento(nombredescuento);
                descuentorealm.setMontodescuento(montodescuento);
                descuentorealm.setEstadodescuento(estadodescuento);

            }

        });

    }

    public   void grabartiposdepago(final int idtiposdepago, final String nombretiposdepago
            , final String estadotiposdepago) {
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





}