package com.empresadelivery.empresadelivery;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.empresadelivery.empresadelivery.modelos.Adicional;
import com.empresadelivery.empresadelivery.modelos.Crema;
import com.empresadelivery.empresadelivery.modelos.Productoadicional;
import com.empresadelivery.empresadelivery.modelos.Productocrema;

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


public class Agregaradicionales extends AppCompatActivity {
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    int idproducto;
    String crem="";
    int numerodeadiciones;
TextView cabeceralayoutadicional;
Switch estadocrema;
    String FileName = "myfile";
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregaradicionales);

        SharedPreferences sharedPreferences =getSharedPreferences(FileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        Button listo=(Button)findViewById(R.id.listo);
        Bundle datos = this.getIntent().getExtras();

        String idpro = datos.getString("idproducto");



        guardaridproducto(idpro);

       String nombreproducto = datos.getString("nombreproducto");
        cabeceralayoutadicional=(TextView)findViewById(R.id.cabeceralayoutadicional);
        cabeceralayoutadicional.setText(nombreproducto);
        prefs =getApplication().getSharedPreferences(FileName, Context.MODE_PRIVATE);
        String idempresa = prefs.getString("idempresa", "");



        new traeradicional().execute(idempresa);
        new traercrema().execute(idempresa);
       //new traercremas().execute("17");
listo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent ListSong = new Intent(getApplicationContext(), Listaproductos.class);
        startActivity(ListSong);

    }
});
    }
  private class traeradicional extends AsyncTask<String, String, String> {
        HttpURLConnection conne;
        ProgressDialog pdLoading = new ProgressDialog(Agregaradicionales.this);
        URL url = null;
        ArrayList<Adicional> listadeadicionales = new ArrayList<Adicional>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("\tCargando Adicionales");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("https://sodapop.pe/sugest/apitraertodosadicionales.php");
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
                return e.toString();

            } finally {
                conne.disconnect();
            }
        }

        @SuppressLint("ResourceAsColor")
        @Override
        protected void onPostExecute(String result) {
            Log.d("paso",result.toString());
            pdLoading.dismiss();
            ArrayList<Adicional> peopleadicional = new ArrayList<>();
            String[] stradicional = {"No Suggestions"};
            ArrayList<String> datalistadicional = new ArrayList<String>();


            Adicional mesoadiconal;
            peopleadicional.clear();
            RecyclerView.Adapter adapteradicional;

            if (result.equals("no rows")) {
            } else {
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.optJSONObject(i);
                        mesoadiconal = new Adicional(json_data.getInt("idadicional"), json_data.getString("nombreadicional"), json_data.getDouble("precioadicional"), json_data.getString("estadoadicional"),
                                json_data.getInt("idempresa"));
                        peopleadicional.add(mesoadiconal);
                    }
                    LinearLayout my_layout = (LinearLayout)findViewById(R.id.my_layout);

                    stradicional = datalistadicional.toArray(new String[datalistadicional.size()]);



                    TextView texto = new TextView(getApplication());
                    texto.setText("        AGREGA ALGUN ADICIONAL        ");
                    texto.setBackgroundDrawable(getApplication().getResources().getDrawable(R.drawable.fondotarjeta));
                    texto.setGravity(Gravity.CENTER);

                    //  texto.setLayoutParams(param);
                    texto.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                    texto.setTypeface(null, Typeface.NORMAL);
                    texto.setShadowLayer(2, 1, 1, R.color.colortres);
                    texto.setTextColor(getApplication().getResources().getColor(R.color.colortres));

                    TableRow.LayoutParams textoenlayout = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                    my_layout.addView(texto, textoenlayout);
                    for( numerodeadiciones= 0; numerodeadiciones < peopleadicional.size(); numerodeadiciones++) {
                        CheckBox cb = new CheckBox(getApplication());
                        cb.setText("   "+peopleadicional.get(numerodeadiciones).getNombreadicional()+ "               S/. "+String.valueOf(peopleadicional.get(numerodeadiciones).getPrecioadicional()));
                        cb.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                        Double ffff=peopleadicional.get(numerodeadiciones).getPrecioadicional();
                        String q=peopleadicional.get(numerodeadiciones).getNombreadicional();
                        Double l=peopleadicional.get(numerodeadiciones).getPrecioadicional();
                        final int ida=peopleadicional.get(numerodeadiciones).getIdadicional();
                   cb.setId(numerodeadiciones);
                        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(CompoundButton buttonView,
                                                         final boolean isChecked) {


                                CharSequence options[];
                                prefs = getApplicationContext().getSharedPreferences(FileName, Context.MODE_PRIVATE);
                                String idproductom=prefs.getString("idproducto","");
                                int ou=Integer.parseInt(idproductom);







                                if (isChecked) {



                                    ;
                                    //String preciodeadicional=String.valueOf(peopleadicional.get(numerodeadiciones).getPrecioadicional());
Productoadicional yu=new Productoadicional(1,ou,ida,crem);
                                    new grabaradicional().execute(yu);
                                } else {

                                    Productoadicional yu=new Productoadicional(1,ou,ida,crem);
                                    new eliminaradicional().execute(yu);

                                }
                            }});



                        my_layout.addView(cb);
                    }

                } catch (JSONException e) {
                    Log.d("erroro",e.toString());
                }
            }
        }

    }


    public class grabaradicional extends AsyncTask<Productoadicional, Void, String> {
        String resultado;
        HttpURLConnection conne;
        URL url = null;
        Productoadicional ped;
        ProgressDialog pdLoading = new ProgressDialog(Agregaradicionales.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tAsignando accion..");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(Productoadicional... params) {
            ped=params[0];
            try {
                url = new URL("https://sodapop.pe/sugest/grabaradicionalproducto.php");
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

                        .appendQueryParameter("idproducto",String.valueOf(ped.getIdproducto()))
                        .appendQueryParameter("idadicional", String.valueOf(ped.getIdadicional()))
                        .appendQueryParameter("estadocrema",String.valueOf(ped.getEstadocrema()))


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




        }
    }
    public class eliminaradicional extends AsyncTask<Productoadicional, Void, String> {
        String resultado;
        HttpURLConnection conne;
        URL url = null;
        Productoadicional ped;
        ProgressDialog pdLoading = new ProgressDialog(Agregaradicionales.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\teliminando accion..");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(Productoadicional... params) {
            ped=params[0];
            try {
                url = new URL("https://sodapop.pe/sugest/eliminaradicionalproducto.php");
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

                        .appendQueryParameter("idproducto",String.valueOf(ped.getIdproducto()))
                        .appendQueryParameter("idadicional", String.valueOf(ped.getIdadicional()))
                        .appendQueryParameter("estadocrema",String.valueOf(ped.getEstadocrema()))


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




        }
    }

    public   void guardaridproducto(String idproducto){
        SharedPreferences sharedPreferences =getSharedPreferences(FileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("idproducto",idproducto);

        editor.commit();

    }





    private class traercrema extends AsyncTask<String, String, String> {
        HttpURLConnection conne;
        ProgressDialog pdLoading = new ProgressDialog(Agregaradicionales.this);
        URL url = null;
        ArrayList<Crema> listadeadicionales = new ArrayList<Crema>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("\tCargando Acompañantes");
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
                return e.toString();

            } finally {
                conne.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("paso",result.toString());
            pdLoading.dismiss();
            ArrayList<Crema> peopleadicional = new ArrayList<>();
            String[] stradicional = {"No Suggestions"};
            ArrayList<String> datalistadicional = new ArrayList<String>();


            Crema mesoadiconal;
            peopleadicional.clear();
            RecyclerView.Adapter adapteradicional;

            if (result.equals("no rows")) {
            } else {
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {


                        JSONObject json_data = jArray.optJSONObject(i);
                        mesoadiconal = new Crema(
                                json_data.getInt("idcrema"), json_data.getString("nombrecrema"),
                                json_data.getString("estadocrema"), json_data.getInt("idempresa"));
                        peopleadicional.add(mesoadiconal);
                    }
                    LinearLayout mylayout = (LinearLayout)findViewById(R.id.mylayout1);
                    stradicional = datalistadicional.toArray(new String[datalistadicional.size()]);
                    TextView texto = new TextView(getApplication());
                    texto.setText("        Agrega algun Acompañante        ");
                    texto.setBackgroundDrawable(getApplication().getResources().getDrawable(R.drawable.fondotarjeta));
                    texto.setGravity(Gravity.CENTER);
                    texto.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                    texto.setTypeface(null, Typeface.NORMAL);
                    texto.setShadowLayer(2, 1, 1, R.color.accent);
                    texto.setTextColor(getApplication().getResources().getColor(R.color.colortres));
                    TableRow.LayoutParams textoenlayout = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                    mylayout.addView(texto, textoenlayout);


                    for( numerodeadiciones= 0; numerodeadiciones < peopleadicional.size(); numerodeadiciones++) {
                        CheckBox cb = new CheckBox(getApplication());
                        cb.setText("   "+peopleadicional.get(numerodeadiciones).getNombrecrema());
                        cb.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));

                        final int ida=peopleadicional.get(numerodeadiciones).getIdcrema();
                        cb.setId(numerodeadiciones);
                        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(CompoundButton buttonView,
                                                         final boolean isChecked) {
                                CharSequence options[];
                                prefs = getApplicationContext().getSharedPreferences(FileName, Context.MODE_PRIVATE);
                                String idproductom=prefs.getString("idproducto","");
                                int ou=Integer.parseInt(idproductom);

                                if (isChecked) {
                                    String idempresa = prefs.getString("idempresa", "");

                                    Productocrema yu=new Productocrema(1,ou,ida,"1",Integer.parseInt(idempresa));
                                    new grabarcremaemp().execute(yu);

                                }else{

                                    String idempresa = prefs.getString("idempresa", "");

                                    Productocrema yu=new Productocrema(1,ou,ida,"1",Integer.parseInt(idempresa));
                                    new eliminarcrema().execute(yu);




                                }




                            }});



                        mylayout.addView(cb);
                    }

                } catch (JSONException e) {
                    Log.d("erroro",e.toString());
                }
            }
        }

    }


    public class eliminarcrema extends AsyncTask<Productocrema, Void, String> {
        String resultado;
        HttpURLConnection conne;
        URL url = null;
        Productocrema ped;
        ProgressDialog pdLoading = new ProgressDialog(Agregaradicionales.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\teliminando accion..");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(Productocrema... params) {
            ped=params[0];
            try {
                url = new URL("https://sodapop.pe/sugest/apieliminarcremaempresa.php");
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
            try {
                conne = (HttpURLConnection) url.openConnection();
                conne.setReadTimeout(READ_TIMEOUT);
                conne.setConnectTimeout(CONNECTION_TIMEOUT);
                conne.setRequestMethod("GET");
                conne.setDoInput(true);
                conne.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()



                        .appendQueryParameter("idproducto",String.valueOf(ped.getIdproducto()))
                        .appendQueryParameter("idcrema", String.valueOf(ped.getIdcrema()))
                        .appendQueryParameter("estadocrema",String.valueOf(ped.getEstadoproductocrema()))
                        .appendQueryParameter("idempresa",String.valueOf(ped.getIdempresa()))


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




        }
    }
    public class grabarcremaemp extends AsyncTask<Productocrema, Void, String> {
        String resultado;
        HttpURLConnection conne;
        URL url = null;
        Productocrema ped;
        ProgressDialog pdLoading = new ProgressDialog(Agregaradicionales.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tAsignando accion..");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(Productocrema... params) {
            ped=params[0];
            try {
                url = new URL("https://sodapop.pe/sugest/apigrabarcremaproductoempresa.php");
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
            try {
                conne = (HttpURLConnection) url.openConnection();
                conne.setReadTimeout(READ_TIMEOUT);
                conne.setConnectTimeout(CONNECTION_TIMEOUT);
                conne.setRequestMethod("GET");
                conne.setDoInput(true);
                conne.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()

                        .appendQueryParameter("idproducto",String.valueOf(ped.getIdproducto()))
                        .appendQueryParameter("idcrema", String.valueOf(ped.getIdcrema()))
                        .appendQueryParameter("idempresa",String.valueOf(ped.getIdempresa()))


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




        }
    }


}


