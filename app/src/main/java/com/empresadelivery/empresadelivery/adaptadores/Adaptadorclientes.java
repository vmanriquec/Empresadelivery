package com.empresadelivery.empresadelivery.adaptadores;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.empresadelivery.empresadelivery.Datosdeempresa;
import com.empresadelivery.empresadelivery.Mapa;
import com.empresadelivery.empresadelivery.R;
import com.empresadelivery.empresadelivery.modelos.Descuentos;
import com.empresadelivery.empresadelivery.modelos.DescuentosRealm;
import com.empresadelivery.empresadelivery.modelos.Usuario;

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
import io.realm.RealmResults;


public class Adaptadorclientes extends RecyclerView.Adapter<Adaptadorclientes.AdaptadorViewHolder>{
    public Context mainContext;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    ArrayList<String> dataList;
    String foto;
    SharedPreferences prefs;
    String FileName ="myfile";
    private List<Usuario> items;
    public Spinner vale;

    ArrayList<Descuentos> listaalmacen = new ArrayList<Descuentos>();

    Realm realm = Realm.getDefaultInstance();

    public Adaptadorclientes(List<Usuario> items, Context contexto){
        this.mainContext=contexto;
        this.items=items;
        prefs = mainContext.getSharedPreferences(FileName, Context.MODE_PRIVATE);

        String idalmacenactiv = prefs.getString("idalmacenactivo", "");


    }
    static class AdaptadorViewHolder extends RecyclerView.ViewHolder{
        protected TextView nombreu;
        protected TextView direccionu;
        protected TextView telefonou;
        protected TextView referenciau,correou,nombredescuentou,montodescuentou,idfirebaseu;

        protected Button wasa,mapau,descuento,eliminar;
        protected Spinner vale;


        public AdaptadorViewHolder(View v){
            super(v);
            this.nombreu=(TextView) v.findViewById(R.id.nombreu);
            this.direccionu=(TextView) v.findViewById(R.id.direccionu);
            this.telefonou=(TextView) v.findViewById(R.id.telefonou);
            this.referenciau=(TextView) v.findViewById(R.id.referenciau);
            this.correou=(TextView) v.findViewById(R.id.correou);
            this.nombredescuentou=(TextView) v.findViewById(R.id.nombredescuento);
            this.montodescuentou=(TextView) v.findViewById(R.id.montodescuento);

            this.wasa=(Button)v.findViewById(R.id.wasa);
            this.mapau=(Button)v.findViewById(R.id.mapau);
            this.descuento=(Button)v.findViewById(R.id.descuento);
this.eliminar=(Button)v.findViewById(R.id.eliminar);

        }
    }

    @Override
    public Adaptadorclientes.AdaptadorViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tarjetausuario,viewGroup,false);
        return new Adaptadorclientes.AdaptadorViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final Adaptadorclientes.AdaptadorViewHolder viewHolder, final int position) {
        final Usuario item = items.get(position);
        viewHolder.itemView.setTag(item);
        viewHolder.nombreu.setText(item.getNombreusuario());
        viewHolder.direccionu.setText(item.getDireccion());
        viewHolder.telefonou.setText(item.getTelefono());
        viewHolder.correou.setText(item.getCorreo());
        viewHolder.referenciau.setText(item.getReferencia());
        viewHolder.direccionu.setText(item.getDireccion());
        viewHolder.nombredescuentou.setText(item.getNombrefacebook());
        viewHolder.montodescuentou.setText(item.getIdfacebook());


viewHolder.mapau.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent().setClass(mainContext, Mapa.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        i.putExtra("longitud", item.getLongitud().toString());
        i.putExtra("latitud", item.getLatitud().toString());
        i.putExtra("nombre", item.getNombreusuario().toString());
        i.putExtra("direccion", item.getDireccion().toString());
        i.putExtra("referencia", item.getReferencia().toString());

// Launch the new activity and add the additional flags to the intent
        mainContext.startActivity(i);



    }
});

        viewHolder.wasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+viewHolder.telefonou.getText().toString()+"&text="+"Hola..."));
                    mainContext.getApplicationContext().startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }




            }
        });

        viewHolder.descuento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(v.getRootView().getContext());
                dialog.setContentView(R.layout.doalogodescuento);
                dialog.setTitle(viewHolder.nombreu.getText().toString());
Spinner des=(Spinner)dialog.findViewById(R.id.spindescuento) ;

               TextView text = (TextView) dialog.findViewById(R.id.nombredialog);
                final TextView firebase = (TextView) dialog.findViewById(R.id.idfirebase);
                text.setText(viewHolder.nombreu.getText().toString());
                firebase.setText(item.getIdfirebase().toString());
                Button dialogButton = (Button) dialog.findViewById(R.id.vaallistado);


                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


String idfirebase=firebase.getText().toString();
                        Spinner s=(Spinner)dialog.findViewById(R.id.spindescuento);
                        String al =s.getItemAtPosition(s.getSelectedItemPosition()).toString();
                        String mesei=al;
                        int g= mesei.length();
                        String mesi = mesei.substring(0,2);

                        String  iddescuento=mesi.trim();

                        new actualizardescuento().execute(idfirebase,iddescuento);
                        dialog.dismiss();

                    }
                });
                Realm pedido = Realm.getDefaultInstance();

                RealmResults<DescuentosRealm> results =
                        pedido.where(DescuentosRealm.class)

                                .findAll();
                ArrayList<DescuentosRealm> listaalmacen = new ArrayList<DescuentosRealm>();

                RealmResults<DescuentosRealm> resultst =
                        pedido.where(DescuentosRealm.class)

                                .findAll();
                ArrayAdapter<DescuentosRealm> adaptadorl= new ArrayAdapter<DescuentosRealm>(mainContext,  android.R.layout.simple_spinner_item,resultst );
                des.setAdapter(adaptadorl);


                dialog.show();
            }
        });
viewHolder.eliminar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});
    }
    @Override
    public int getItemCount() {
        return items.size();
    }






    public class actualizardescuento extends AsyncTask<String, String, String> {

        HttpURLConnection conne;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                url = new URL("https://sodapop.pe/sugest/apiactualizardescuentocliente.php");
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
                        .appendQueryParameter("idfirebase", params[0])
                        .appendQueryParameter("idvaledescuento", params[1]);
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
                    return (result.toString());

                } else {
                    return("Connection error");
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
            Log.d("waaaaaaa",result);
            //if(result.equals("no rows")) {
            //}else{



        }

    }

    }







