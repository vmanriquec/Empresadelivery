package com.empresadelivery.empresadelivery.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.empresadelivery.empresadelivery.Dashboardempresa;
import com.empresadelivery.empresadelivery.Editarproducto;
import com.empresadelivery.empresadelivery.R;
import com.empresadelivery.empresadelivery.modelos.Detallepedido;
import com.empresadelivery.empresadelivery.modelos.Productos;
import com.empresadelivery.empresadelivery.modelos.Tipodepagoempresa;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

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
import java.util.concurrent.atomic.AtomicMarkableReference;

import io.realm.Realm;
import jp.wasabeef.picasso.transformations.CropSquareTransformation;


public class Adaptadormaestraproducto extends RecyclerView.Adapter<Adaptadormaestraproducto.AdaptadorViewHolder> {
    public Context mainContext;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    Intent pi;
    String foto;
    SharedPreferences prefs;
    String FileName ="myfile";
    private List<Productos> items;
    ArrayList<Detallepedido> detallepedido=new ArrayList<>();
    Detallepedido objdetallepedido;


    Realm realm = Realm.getDefaultInstance();

    public Adaptadormaestraproducto(List<Productos> items, Context contexto){
        this.mainContext=contexto;
        this.items=items;
        prefs = mainContext.getSharedPreferences(FileName, Context.MODE_PRIVATE);

          String idalmacenactiv = prefs.getString("idalmacenactivo", "");


    }
    static class AdaptadorViewHolder extends RecyclerView.ViewHolder{
        protected TextView productonombre;
        protected TextView idproducto;
        protected TextView productoprecio;
        protected TextView productoingredientes,stockp;
        protected ImageView productoimagen;
        protected TextView inventario;
    protected  ImageView getProductoimagen;
        protected Button editar,eliminaro;
        protected Switch habilitado;
        ;

        public AdaptadorViewHolder(View v){
            super(v);
            this.productonombre=(TextView) v.findViewById(R.id.nombreproductoproducto);
            this.productoprecio=(TextView) v.findViewById(R.id.precioproducto);
            this.idproducto=(TextView) v.findViewById(R.id.idproductoproducto);
            this.inventario=(TextView) v.findViewById(R.id.inventarioproducto);
            this.productoingredientes=(TextView) v.findViewById(R.id.ingredientesproductos);
            this.productoimagen=(ImageView) v.findViewById(R.id.imagenproductos);
            this.habilitado=(Switch)v.findViewById(R.id.switch1);

this.eliminaro=(Button)v.findViewById(R.id.eliminarproducto);
this.editar=(Button)v.findViewById(R.id.editarproducfindto);

        }
    }
    @Override
    public Adaptadormaestraproducto.AdaptadorViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tarjetamuestraproducto,viewGroup,false);
        return new Adaptadormaestraproducto.AdaptadorViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final Adaptadormaestraproducto.AdaptadorViewHolder viewHolder, final int position) {
        final Productos item = items.get(position);
        viewHolder.itemView.setTag(item);
        viewHolder.productonombre.setText(item.getNombreproducto());
        viewHolder.productoingredientes.setText(item.getIngredientes());
        viewHolder.productoprecio.setText("S/. "+ String.valueOf(item.getPrecventa()));
        viewHolder.idproducto.setText(String.valueOf(item.getIdproducto()));
String ty=item.getEstadoproducto();

        if (ty.equals("0")){
            viewHolder.inventario.setText("no disponible");
            viewHolder.habilitado.setChecked(false);


        }else{
            viewHolder.inventario.setText("");
            viewHolder.habilitado.setChecked(true);



        }
        viewHolder.habilitado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    new actualizarabiertocerrado().execute("1",String.valueOf(item.getIdproducto()));
                    viewHolder.inventario.setText("");
                } else {
                    new actualizarabiertocerrado().execute("0",String.valueOf(item.getIdproducto()));
                    viewHolder.inventario.setText("no disponible");


                }
            }
        });


        foto=item.getDescripcion().toString();

        Picasso.get().load(foto).transform(new CropSquareTransformation()).resize(200, 200)
                .into( viewHolder.productoimagen);

        viewHolder.productoimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast ImageToast = new Toast(mainContext.getApplicationContext());
                LinearLayout toastLayout = new LinearLayout(mainContext.getApplicationContext());
                toastLayout.setOrientation(LinearLayout.VERTICAL);
                ImageView image = new ImageView(mainContext.getApplicationContext());
                TextView text = new TextView(mainContext.getApplicationContext());
                foto=item.getDescripcion().toString();
                Picasso.get().load("image_URL").into(viewHolder.productoimagen);
                Picasso.get().load(foto).transform(new CropSquareTransformation())
                        .resize(350, 350)
                        .into( image);
                text.setText(item.getIngredientes());
                text.setTextColor(Color.RED);
                text.setBackgroundColor(Color.WHITE);
                text.setGravity(12);
                toastLayout.addView(image);
                toastLayout.addView(text);
                ImageToast.setView(toastLayout);
                ImageToast.setGravity (Gravity.TOP | Gravity.LEFT, 40, 40);
                ImageToast.setDuration(Toast.LENGTH_LONG);
                ImageToast.show();
                ImageToast.getView().setPadding( 20, 100, 20, 20);

                foto=item.getDescripcion().toString();

                Picasso.get().load(foto).transform(new CropSquareTransformation()).resize(200, 200)
                        .into( viewHolder.productoimagen);

            }
        });
        viewHolder.eliminaro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String oo=String.valueOf(item.getIdproducto());
           eliminarimagendestorage(item.getFoto());
            new eliminarproducto().execute(oo);
                items.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());


            }
            });

viewHolder.editar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        Intent i = new Intent().setClass(mainContext, Editarproducto.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        int idp=item.getIdproducto();

        i.putExtra("idproducto",String.valueOf(idp));



// Launch the new activity and add the additional flags to the intent
        mainContext.startActivity(i);


    }
});
    }


    private void eliminarimagendestorage(String ima) {
        // Create a storage reference from our app
        final FirebaseStorage storage= FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();

// Cr
// Create a reference to the file to delete
        StorageReference desertRef = storageRef.child("images/"+ima);

// Delete the file
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }




    private class eliminarproducto extends AsyncTask<String, String, String> {
        ArrayList<Productos> people=new ArrayList<>();
        private String[] strArrData = {"No Suggestions"};

        HttpURLConnection conne;
        URL url = null;
        ArrayList<Productos> listaalmaceno = new ArrayList<Productos>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                url = new URL("https://sodapop.pe/sugest/apieliminarproducto.php");
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

                        .appendQueryParameter("idproducto", params[0])
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
                    return("Connection error");
                }
            } catch (IOException e) {
                e.printStackTrace()                ;

                return e.toString();
            } finally {
                conne.disconnect();
            }
        }


        @Override
        protected void onPostExecute(String result) {
            Log.d("paso",result.toString());

        }

        }


    private class actualizarabiertocerrado extends AsyncTask<String, String, String> {
        ArrayList<Tipodepagoempresa> people=new ArrayList<>();
        private String[] strArrData = {"No Suggestions"};

        HttpURLConnection conne;
        URL url = null;
        ArrayList<Tipodepagoempresa> listaalmaceno = new ArrayList<Tipodepagoempresa>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                url = new URL("https://sodapop.pe/sugest/habilitarproductow.php");
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

                        .appendQueryParameter("idestado", params[0])
                        .appendQueryParameter("idproducto", params[1])
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
                    return("Connection error");
                }
            } catch (IOException e) {
                e.printStackTrace()                ;

                return e.toString();
            } finally {
                conne.disconnect();
            }
        }


        @Override
        protected void onPostExecute(String result) {
            Log.d("paso",result.toString());

        }

    }
    }



