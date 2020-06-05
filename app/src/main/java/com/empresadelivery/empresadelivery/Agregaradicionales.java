package com.empresadelivery.empresadelivery;

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
        new traeradicional().execute("17");
       //new traercremas().execute("17");
listo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent ListSong = new Intent(getApplicationContext(), Listaproductos.class);
        startActivity(ListSong);

    }
});
    }


    private class traercremas extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(Agregaradicionales.this);
        HttpURLConnection conne;
        URL url = null;
        ArrayList<Adicional> listadeadicionales = new ArrayList<Adicional>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tCargando Cremas disponibles");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {


            try {
                url = new URL("https://sodapop.pe/sugest/apitraertodaslascremas.php");
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

                        .appendQueryParameter("idproducto", params[0]);

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
            ArrayList<Crema> peoplecrema = new ArrayList<>();
            String[] strcrema = {"No Suggestions"};
            ArrayList<String> datalistcrema = new ArrayList<String>();
            Crema mesocrema;
            peoplecrema.clear();
            if (result.equals("no rows")) {
            } else {
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.optJSONObject(i);
                        mesocrema = new Crema(json_data.getInt("idcrema"), json_data.getString("nombrecrema"),  json_data.getString("estadocrema"));
                        peoplecrema.add(mesocrema);
                    }


                    LinearLayout my_layout = (LinearLayout)findViewById(R.id.my_layout);
                    strcrema = datalistcrema.toArray(new String[datalistcrema.size()]);
                    TextView texto = new TextView(getApplication());
                    texto.setText("        ESCOGE TUS CREMAS        ");
                    texto.setBackgroundDrawable(getApplication().getResources().getDrawable(R.drawable.blue_leftcorner_bkg));
                    texto.setGravity(Gravity.CENTER);

                    //  texto.setLayoutParams(param);
                    texto.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    texto.setTypeface(null, Typeface.BOLD);
                    texto.setShadowLayer(2, 1, 1, R.color.accent);
                    texto.setTextColor(getApplication().getResources().getColor(R.color.colortres));


                    TableRow.LayoutParams textoenlayout = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                    my_layout.addView(texto, textoenlayout);

                    int numerodecrema;



                    for(numerodecrema= 0; numerodecrema < peoplecrema.size(); numerodecrema++) {
                        CheckBox cbc = new CheckBox(getApplication());
                        cbc.setText("   "+peoplecrema.get(numerodecrema).getNombrecrema());
                        cbc.setId(numerodecrema+1);



                        int idcremita=peoplecrema.get(numerodecrema).getIdcrema();
                        String nombrecremita=peoplecrema.get(numerodecrema).getNombrecrema();


                        cbc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView,
                                                         final boolean isChecked) {

                                CharSequence options[];

                                if (isChecked) {
                                  //  realgrabarcrema(nombrecremita,Integer.parseInt(idproductoseleccionado));


                                } else {


                                    //eliminaracrema(idsupremodedetalle,Integer.parseInt(idproductoseleccionado),nombrecremita);
                                }
                            }});



                        my_layout.addView(cbc);
                    }








                } catch (JSONException e) {
                    Log.d("erroro",e.toString());
                }
            }
        }
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
                        .appendQueryParameter("idproducto", params[0]);
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
                        mesoadiconal = new Adicional(json_data.getInt("idadicional"), json_data.getString("nombreadicional"), json_data.getDouble("precioadicional"), json_data.getString("estadoadicional"));
                        peopleadicional.add(mesoadiconal);
                    }
                    LinearLayout my_layout = (LinearLayout)findViewById(R.id.my_layout);

                    stradicional = datalistadicional.toArray(new String[datalistadicional.size()]);



                    TextView texto = new TextView(getApplication());
                    texto.setText("        AGREGA ALGUN ADICIONAL        ");
                    texto.setBackgroundDrawable(getApplication().getResources().getDrawable(R.drawable.blue_leftcorner_bkg));
                    texto.setGravity(Gravity.CENTER);

                    //  texto.setLayoutParams(param);
                    texto.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    texto.setTypeface(null, Typeface.BOLD);
                    texto.setShadowLayer(2, 1, 1, R.color.accent);
                    texto.setTextColor(getApplication().getResources().getColor(R.color.colortres));

                    TableRow.LayoutParams textoenlayout = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                    my_layout.addView(texto, textoenlayout);
                    for( numerodeadiciones= 0; numerodeadiciones < peopleadicional.size(); numerodeadiciones++) {
                        CheckBox cb = new CheckBox(getApplication());
                        cb.setText("   "+peopleadicional.get(numerodeadiciones).getNombreadicional()+ "               S/. "+String.valueOf(peopleadicional.get(numerodeadiciones).getPrecioadicional()));
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


Switch lo=(Switch)findViewById(R.id.estadocrema);




                                if (isChecked) {


                                    lo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            if (isChecked) {
                                                // The toggle is enabled
                                                crem="1";
                                            } else {
                                                // The toggle is disabled
                                                crem="0";
                                            }
                                        }
                                    });
                                    ;
                                    //String preciodeadicional=String.valueOf(peopleadicional.get(numerodeadiciones).getPrecioadicional());
Productoadicional yu=new Productoadicional(1,ou,ida,crem);
                                    new grabaradicional().execute(yu);
                                } else {
                                    lo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            if (isChecked) {
                                                // The toggle is enabled
                                                crem="1";
                                            } else {
                                                // The toggle is disabled
                                                crem="0";
                                            }
                                        }
                                    });

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



}


