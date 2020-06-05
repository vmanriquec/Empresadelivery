package com.empresadelivery.empresadelivery;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.empresadelivery.empresadelivery.adaptadores.Adaptadorparaverenviarpedidos;
import com.empresadelivery.empresadelivery.modelos.AdicionalRealm;
import com.empresadelivery.empresadelivery.modelos.AdicionalRealmFirebase;
import com.empresadelivery.empresadelivery.modelos.CremaRealm;
import com.empresadelivery.empresadelivery.modelos.CremaRealmFirebase;
import com.empresadelivery.empresadelivery.modelos.CrudcremaRealm;
import com.empresadelivery.empresadelivery.modelos.Crudetallepedido;
import com.empresadelivery.empresadelivery.modelos.Crudpedido;
import com.empresadelivery.empresadelivery.modelos.DetallepedidoRealmFirebase;
import com.empresadelivery.empresadelivery.modelos.Detallepedidorealm;
import com.empresadelivery.empresadelivery.modelos.PedidoRealm;

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
import io.realm.RealmResults;

import static com.empresadelivery.empresadelivery.MainActivity.realmgrabaradicional;

public class Mostrarpedidos extends AppCompatActivity {
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    public ArrayList<DetallepedidoRealmFirebase> todoslosdetalles = new ArrayList<>();
    public ArrayList<CremaRealmFirebase> todaslascremas = new ArrayList<>();
    public ArrayList<AdicionalRealmFirebase> todoslosadicionales = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrarpedidos);
        new traerpedidos().execute();
mostrartodo();
    }
    public void mostrartodo(){

        Realm.init(getApplicationContext());
        ArrayList<DetallepedidoRealmFirebase> todoslosdetalles = new ArrayList<>();
        Realm pedido = Realm.getDefaultInstance();
        RealmResults<Detallepedidorealm> results =
                pedido.where(Detallepedidorealm.class)
                        .findAll();

        int w = results.size();
        DetallepedidoRealmFirebase detalleainsertar ;
        for (int i = 0; i < w; i++) {
            int cantidad = results.get(i).getCantidadrealm();
            int idd = results.get(i).getIddetallepedido();
            int idpedido = results.get(i).getIdpedido();
            String nombreproducto = results.get(i).getNombreproductorealm();
            String subtotal = results.get(i).getSubtotal();
            Double precvente = results.get(i).getPrecventarealm();
            int idproductorealm = results.get(i).getIdproductorealm();
            String comentariococina = results.get(i).getComentarioacocina();
            ArrayList<CremaRealmFirebase> todaslascremas = new ArrayList<>();
            RealmResults<CremaRealm> resulcremaao =
                    pedido.where(CremaRealm.class)
                            .equalTo("iddetalle", idd)
                            .findAll();
            int lsrgaa = resulcremaao.size();
            for (int ic = 0; ic < lsrgaa; ic++) {
                RealmResults<CremaRealm> resultsoa =
                        pedido.where(CremaRealm.class)
                                .equalTo("iddetalle", idd)
                                .findAll();
                CremaRealmFirebase dett = new CremaRealmFirebase(ic, resultsoa.get(ic).getNombrecrema().toString(), "1", 1, idproductorealm);
                todaslascremas.add(dett);
            }
            int g=todaslascremas.size();
            String fodat="";
            String foda="";
            for (int u=0;u<g;u++){
                foda=todaslascremas.get(u).getNombrecrema().toString();
                fodat=fodat+foda+"\r\n";



            }
////////////////////////////////////////////adicionales
            ArrayList<AdicionalRealmFirebase> todoslosadicionalesa = new ArrayList<>();
            RealmResults<AdicionalRealm> resulcremaad =
                    pedido.where(AdicionalRealm.class)
                            .equalTo("idadicionaldetalle", idd)
                            .findAll();
            int lsrgaad = resulcremaad.size();
            for (int ica = 0; ica < lsrgaad; ica++) {
                RealmResults<AdicionalRealm> resultsoad =
                        pedido.where(AdicionalRealm.class)
                                .equalTo("idadicionaldetalle", idd)
                                .findAll();
                AdicionalRealmFirebase dettd = new AdicionalRealmFirebase(ica,resultsoad.get(ica).getNombreadicional(),
                        Double.parseDouble(resultsoad.get(ica).getPrecioadicional().toString()),
                        resultsoad.get(ica).getEstadoadicional(),resultsoad.get(ica).getIdproducto(),resultsoad.get(ica).getId(),resultsoad.get(ica).getId());
                todoslosadicionalesa.add(dettd);
            }

            int ga=todoslosadicionalesa.size();
            String fodata="";
            String fodaa="";
            for (int ua=0;ua<ga;ua++){
                fodaa=todoslosadicionalesa.get(ua).getNombreadicional().toString();
                fodata=fodata+fodaa+"\r\n";
            }
            detalleainsertar = new DetallepedidoRealmFirebase(1,1  , cantidad, precvente, Double.parseDouble(subtotal), idpedido, 1, fodata, fodat, comentariococina,1,fodata,comentariococina,nombreproducto,"1");


            ///////////////////////////////////////////////////////

            RecyclerView todo=(RecyclerView)findViewById(R.id.todos);




            todoslosdetalles.add(detalleainsertar);


            Adaptadorparaverenviarpedidos adapterproducto = new Adaptadorparaverenviarpedidos(todoslosdetalles,getApplicationContext());
           todo.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
            todo.setAdapter(adapterproducto);


        }




    }

    public  static void realmgrbarenbasedatosdetallepedido(final int idproducto, final int cantidad, final String nombre, final Double precio, final int idpedido, final String subtotal, final String comentariococina, final int detallepedido) {
        Realm pedido = Realm.getDefaultInstance();
        pedido.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm pedido) {
                int index = Crudetallepedido.calculateIndex();

                Detallepedidorealm realmDetallepedidorealm = pedido.createObject(Detallepedidorealm.class, index);
                realmDetallepedidorealm.setIdproductorealm(idproducto);
                realmDetallepedidorealm.setCantidadrealm(cantidad);
                realmDetallepedidorealm.setNombreproductorealm(nombre);
                realmDetallepedidorealm.setPrecventarealm(precio);
                realmDetallepedidorealm.setSubtotal(subtotal);
                realmDetallepedidorealm.setComentarioacocina(comentariococina);
                realmDetallepedidorealm.setIdpedido(idpedido);

                realmDetallepedidorealm.setIddetallepedido(detallepedido);

            }

        });


    }


    private class traeradicionales extends AsyncTask<String, String, String> {
        HttpURLConnection conne;
        URL url = null;
        ArrayList<DetallepedidoRealmFirebase> listaalmaceno = new ArrayList<DetallepedidoRealmFirebase>();
        ProgressDialog pdLoading = new ProgressDialog(getApplicationContext());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // pdLoading.setMessage("\tCargando cremas :)");
            //pdLoading.setCancelable(false);
            //pdLoading.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("https://sodapop.pe/sugest/traeradicionalesdetallefirebase.php");
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

                        .appendQueryParameter("iddetalle", params[0]);

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

                return e.toString();
            } finally {
                conne.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            pdLoading.dismiss();


            AdicionalRealmFirebase mesoproducto;
            String[] strArrDatausuario = {"No Suggestions"};
            ArrayList<String> dataListusuario = new ArrayList<String>();
            if (result.equals("no rows")) {
            } else {
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.optJSONObject(i);
                        // mesoproducto = new AdicionalRealmFirebase(json_data.getInt("idadicional"),json_data.getString("nombreadicional")
                        //       ,json_data.getDouble("precioadicional"),json_data.getString("estadoadicional"), json_data.getInt("idproducto"),1,json_data.getInt("iddetalle"));

                        realmgrabaradicional(json_data.getString("nombreadicional"),json_data.getDouble("precioadicional"),json_data.getInt("idadicional")
                                , json_data.getInt("idproducto"),json_data.getInt("iddetalle"),json_data.getInt("iddetalle"));

                    }

                } catch (JSONException e) {
                    Log.d("erroro",e.toString());
                }
            }

        }
    }
    private class traercremas extends AsyncTask<String, String, String> {
        HttpURLConnection conne;
        URL url = null;
        ArrayList<DetallepedidoRealmFirebase> listaalmaceno = new ArrayList<DetallepedidoRealmFirebase>();
        ProgressDialog pdLoading = new ProgressDialog(getApplicationContext());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //pdLoading.setMessage("\tCargando cremas :)");
            //pdLoading.setCancelable(false);
            // pdLoading.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("https://sodapop.pe/sugest/traercremasdedetallesfirebase.php");
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

                        .appendQueryParameter("iddetalle", params[0]);

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

                return e.toString();
            } finally {
                conne.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            pdLoading.dismiss();


            CremaRealmFirebase mesoproducto;
            String[] strArrDatausuario = {"No Suggestions"};
            ArrayList<String> dataListusuario = new ArrayList<String>();
            if (result.equals("no rows")) {
            } else {
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.optJSONObject(i);
                        mesoproducto = new CremaRealmFirebase(json_data.getInt("idcremadetalle"),json_data.getString("nombrecrema")
                                ,json_data.getString("estadocrema"),json_data.getInt("iddetalle"),json_data.getInt("idproducto"));

                        realgrabarcrema(json_data.getString("nombrecrema"),json_data.getInt("idproducto"),json_data.getInt("iddetalle"),json_data.getInt("iddetalle"));


                    }

                } catch (JSONException e) {
                    Log.d("erroro",e.toString());
                }
            }

        }
    }


    public  static void realgrabarcrema(final String nombrecrema, final int idproducto, final int idcremadetalle, final int iddetalle) {
        Realm pedido = Realm.getDefaultInstance();
        pedido.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm pedido) {

                int index = CrudcremaRealm.calculateIndex();
                CremaRealm CremaRealm = pedido.createObject(CremaRealm.class, index);
                CremaRealm.setEstadocrema("1");
                CremaRealm.setIdproducto(idproducto);
                CremaRealm.setNombrecrema(nombrecrema);
                CremaRealm.setIdcrema(iddetalle);
                CremaRealm.setIddetalle(iddetalle);

            }
        });


    }

    private class traerdetallesdepedidofirebase extends AsyncTask<String, String, String> {
        HttpURLConnection conne;
        URL url = null;
        ArrayList<DetallepedidoRealmFirebase> listaalmaceno = new ArrayList<DetallepedidoRealmFirebase>();


        ProgressDialog pdLoading = new ProgressDialog(getApplicationContext());

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            //  pdLoading.setMessage("\tCargando detalles :)");
            //pdLoading.setCancelable(false);
            //pdLoading.show();


        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("https://sodapop.pe/sugest/traerdetallesporidfirebase.php");
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

                        .appendQueryParameter("idfirebase", params[0]);

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

                return e.toString();
            } finally {
                conne.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            pdLoading.dismiss();



            DetallepedidoRealmFirebase mesoproducto;
            String[] strArrDatausuario = {"No Suggestions"};
            ArrayList<String> dataListusuario = new ArrayList<String>();
            if (result.equals("no rows")) {
            } else {

                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.optJSONObject(i);
                        mesoproducto = new DetallepedidoRealmFirebase(json_data.getInt("iddetallepedido"), json_data.getInt("idproducto"), json_data.getInt("cantidad")
                                , json_data.getDouble("precventa"), json_data.getDouble("subtotal"), json_data.getInt("idpedido"), json_data.getInt("idalmacen")
                                , json_data.getString("adicionales"), json_data.getString("cremas"), json_data.getString("comentario"), json_data.getInt("ojo"),
                                json_data.getString("imagenreal"), json_data.getString("comentarioacocina"), json_data.getString("nombreproductorealm"), "1");
                        todoslosdetalles.add(mesoproducto);
                        realmgrbarenbasedatosdetallepedido(json_data.getInt("idproducto"), json_data.getInt("cantidad"), json_data.getString("nombreproductorealm")
                                , json_data.getDouble("precventa"), json_data.getInt("idpedido"), json_data.getString("subtotal"), json_data.getString("comentarioacocina"), json_data.getInt("iddetallepedido"));
                    }

                    int g = todoslosdetalles.size();
                    for (int u = 0; u < g; u++) {
                        int ty = todoslosdetalles.get(u).getIddetallepedido();
                        String yh = String.valueOf(ty);
                        new Mostrarpedidos.traercremas().execute(yh);
                        new Mostrarpedidos.traeradicionales().execute(yh);

                    }

                } catch (JSONException e) {
                    Log.d("erroro", e.toString());
                }
            }
        }
    }
    public  static void realgrabarpedido(final int idpedido, final int idcliente, final int idmesa, final Double totalpedido,final String estadopedido, final String fechapedido,
                                         final int idusuario, final int idalmacen, final String idfacebook, final String descripcionpedido, final String llevar, final String direccionallevar) {
        Realm pedido = Realm.getDefaultInstance();
        pedido.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm pedido) {
                int index = Crudpedido.calculateIndex();
                PedidoRealm realmDetallepedidorealm = pedido.createObject(PedidoRealm.class, index);
                realmDetallepedidorealm.setIdpedido(idpedido);
                realmDetallepedidorealm.setIdcliente(idcliente);
                realmDetallepedidorealm.setIdmesa(idmesa);
                realmDetallepedidorealm.setTotalpedido(totalpedido);
                realmDetallepedidorealm.setEstadopedido(estadopedido);
                realmDetallepedidorealm.setFechapedido(fechapedido);
                realmDetallepedidorealm.setIdalmacen(idalmacen);
                realmDetallepedidorealm.setIdfacebook(idfacebook);
                realmDetallepedidorealm.setIdusuario(idusuario);
                realmDetallepedidorealm.setIdalmacen(idalmacen);
                realmDetallepedidorealm.setDescripcionpedido(descripcionpedido);
               // realmDetallepedidorealm.setLlevar(llevar);
                //realmDetallepedidorealm.setDireccionallevar(direccionallevar);

                //realmDetallepedidorealm.setIdpedido(index);

            }

        });


    }
    private class traerpedidos extends AsyncTask<String, String, String> {
        HttpURLConnection conne;
        URL url = null;
        ArrayList<DetallepedidoRealmFirebase> listaalmaceno = new ArrayList<DetallepedidoRealmFirebase>();
        ProgressDialog pdLoading = new ProgressDialog(getApplicationContext());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // pdLoading.setMessage("\tCargando cremas :)");
            //pdLoading.setCancelable(false);
            //pdLoading.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("https://sodapop.pe/sugest/traertodoslospedidosdelivery.php");
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

                        .appendQueryParameter("iddetalle", params[0]);

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

                return e.toString();
            } finally {
                conne.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            pdLoading.dismiss();


            AdicionalRealmFirebase mesoproducto;
            String[] strArrDatausuario = {"No Suggestions"};
            ArrayList<String> dataListusuario = new ArrayList<String>();
            if (result.equals("no rows")) {
            } else {
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.optJSONObject(i);
                        // mesoproducto = new AdicionalRealmFirebase(json_data.getInt("idadicional"),json_data.getString("nombreadicional")
                        //       ,json_data.getDouble("precioadicional"),json_data.getString("estadoadicional"), json_data.getInt("idproducto"),1,json_data.getInt("iddetalle"));

                        realgrabarpedido(json_data.getInt("idpedido"),json_data.getInt("idcliente"),
                                json_data.getInt("idmesa")
                                , json_data.getDouble("totalpedido"),json_data.getString("estadopedido")
                                ,json_data.getString("fechapedido"),json_data.getInt("idusuario"),json_data.getInt("idalmacen")
                        ,json_data.getString("idfacebook"),json_data.getString("descripcionpedido"),json_data.getString("llevar")
                                ,json_data.getString("direccionallevar")
                                );

                        new Mostrarpedidos.traerdetallesdepedidofirebase().execute(String.valueOf(json_data.getInt("idfirebase")));
                    }

                } catch (JSONException e) {
                    Log.d("erroro",e.toString());
                }
            }

        }
    }
}
