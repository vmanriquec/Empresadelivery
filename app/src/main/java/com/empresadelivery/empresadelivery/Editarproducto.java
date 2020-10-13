package com.empresadelivery.empresadelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.empresadelivery.empresadelivery.adaptadores.Adaptadormaestraproducto;
import com.empresadelivery.empresadelivery.modelos.Familia;
import com.empresadelivery.empresadelivery.modelos.Productoguardar;
import com.empresadelivery.empresadelivery.modelos.Productos;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jp.wasabeef.picasso.transformations.CropSquareTransformation;

public class Editarproducto extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    ImageView foto_gallery;
    public static final int REQUEST_CODE_TAKE_PHOTO = 0 /*1*/;
    private String mCurrentPhotoPath;
    private Uri photoURI;
    Spinner almacen;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    String FileName = "myfile", idproducto;
    SharedPreferences prefs;

    Spinner familia;
    Button eleccion,guardarproductoe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarproducto);

        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();
         idproducto= extra.getString("idproducto");
        eleccion=(Button)findViewById(R.id.camarae);
         almacen=(Spinner)findViewById(R.id.spinnerio2e);
        TextView idpro=(TextView) findViewById(R.id.idproductoeditar);
        idpro.setText(idproducto);
        familia =(Spinner) findViewById(R.id.spinnerio2e);
        EditText nombreproductoe=(EditText) findViewById(R.id.nombreproductoe);
                EditText precioprodc=(EditText) findViewById(R.id.precioe);
        EditText descri=(EditText) findViewById(R.id.descripcione);
        EditText ingrediente=(EditText) findViewById(R.id.ingredientese);
        guardarproductoe=(Button) findViewById(R.id.guardarproductoe);


        foto_gallery=(ImageView)findViewById(R.id.fotoe);

        prefs = this.getSharedPreferences(FileName, Context.MODE_PRIVATE);

        String idempresa=prefs.getString("idempresa","");

        new traerproductos().execute(idempresa,idproducto);
        new cargarfamilias().execute(idempresa);


        eleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Editarproducto.this);
                builder.setMessage("Elija una Opcion")
                        .setCancelable(false)
                        .setPositiveButton("Tomar foto", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (ContextCompat.checkSelfPermission(Editarproducto.this,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(Editarproducto.this,
                                        Manifest.permission.CAMERA)
                                        != PackageManager.PERMISSION_GRANTED) {


                                    if (ActivityCompat.shouldShowRequestPermissionRationale(Editarproducto.this,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                                    } else {
                                        ActivityCompat.requestPermissions(Editarproducto.this,
                                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                225);
                                    }


                                    if (ActivityCompat.shouldShowRequestPermissionRationale(Editarproducto.this,
                                            Manifest.permission.CAMERA)) {

                                    } else {
                                        ActivityCompat.requestPermissions(Editarproducto.this,
                                                new String[]{Manifest.permission.CAMERA},
                                                226);
                                    }
                                } else {
                                    dispatchTakePictureIntent();
                                }
                            }


                        })

                        .setNegativeButton("Galeria de Fotos", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                openGallery();
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });



guardarproductoe.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {     BottomSheetFragment bottomSheetDialog = BottomSheetFragment.newInstance();

        // String nombre = prefs.getString("nombreusuariof", "");

        final FirebaseStorage storage= FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();

// Create a reference to "mountains.jpg"
        String nombre=nombreproductoe.getText().toString();
        StorageReference mountainsRef = storageRef.child(nombre+".jpg");
        foto_gallery.setDrawingCacheEnabled(true);
        foto_gallery.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) foto_gallery.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Bundle bundle = new Bundle();
                bundle.putString("test", "Woooooooow nose pudo atualizar la informacion ");
                bundle.putString("nombreusuario", "Upsss!");
                bundle.putString("imagen", "https://www.sodapop.pe/ii.gif");


                bottomSheetDialog.setArguments(bundle);
                bottomSheetDialog.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                if (taskSnapshot.getMetadata() != null) {
                    if (taskSnapshot.getMetadata().getReference() != null) {
                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {


                                String imageUrl = uri.toString();
                                Log.d("imagen",imageUrl);
                                String al =almacen.getItemAtPosition(almacen.getSelectedItemPosition()).toString();
                                String mesei=al;
                                int g= mesei.length();
                                String mesi = mesei.substring(0,2);

                                String  idalmacen=mesi.trim();

                                String mesio = mesei.substring(3,g);
                                String nombrealmacen=mesio.trim();



                                Spinner familia=(Spinner)findViewById(R.id.spinnerio2e);
                                String alf =familia.getItemAtPosition(familia.getSelectedItemPosition()).toString();
                                String meseif=alf;
                                int gf= meseif.length();
                                String mesif = meseif.substring(0,2);

                                String  idfamilia=mesif.trim();

                                String mesiof = meseif.substring(3,gf);
                                String nombrefamilia=mesiof.trim();

                                //                      pdLoading.dismiss();

                                EditText precioprodc=(EditText) findViewById(R.id.precioe);
                                EditText descri=(EditText) findViewById(R.id.descripcione);
                                EditText ingrediente=(EditText) findViewById(R.id.ingredientese);

                                String idempresa=prefs.getString("idempresa","");
                                Productoguardar pg=new Productoguardar(Integer.parseInt(idproducto),nombreproductoe.getText().toString(),"1",Double.parseDouble(precioprodc.getText().toString())
                                        ,"no",Integer.parseInt(idfamilia),Double.parseDouble(descri.getText().toString()),
                                        imageUrl,"no hay",nombre+".jpg","sin qr"
                                        ,1,ingrediente.getText().toString(),Integer.parseInt(idalmacen),Integer.parseInt(idempresa));


                                new actualizaproducto().execute(pg);

                            }
                        });
                    }
                }





            }
        });












    }
});
    }
    private class traerproductos extends AsyncTask<String, String, String> {
        ArrayList<Productoguardar> people=new ArrayList<>();
        private String[] strArrData = {"No Suggestions"};

        HttpURLConnection conne;
        URL url = null;
        ArrayList<Productoguardar> listaalmaceno = new ArrayList<Productoguardar>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                url = new URL("https://sodapop.pe/sugest/traerproductosporidyempresa.php");
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

                        .appendQueryParameter("idempresa", params[0])
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

            people.clear();

            ArrayList<String> dataList = new ArrayList<String>();
            Productoguardar meso;
            if(result.equals("no rows")) {
                Toast.makeText(getApplicationContext(),"no existen datos a mostrar",Toast.LENGTH_LONG).show();

            }else{

                try {


                    JSONArray jArray = new JSONArray(result);


                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.optJSONObject(i);

                        meso = new Productoguardar(
                                json_data.getInt("idproducto"),
                                json_data.getString("nombreproducto"),
                                json_data.getString("estadoproducto"),
                                json_data.getDouble(("precventa")),
                                json_data.getString("imagen"),
                                json_data.getInt("idfamilia"),
                                json_data.getDouble("preccosto"),
                                json_data.getString(("descripcion")),
                                json_data.getString("codigobarras"),
                                json_data.getString("foto"),

                                json_data.getString(("qr")),
                                json_data.getInt("idproveedor"),
                                json_data.getString("ingredientes"),
                                json_data.getInt("idempresa"),
                                json_data.getInt("idempresa")
                            )
                        ;
                        people.add(meso);
                        EditText nombreproductoe=(EditText) findViewById(R.id.nombreproductoe);
                        nombreproductoe.setText(people.get(0).getNombreproducto());

                        EditText precioprodc=(EditText) findViewById(R.id.precioe);
                        precioprodc.setText(String.valueOf(people.get(0).getPrecventa()));

                        EditText descri=(EditText) findViewById(R.id.descripcione);
                        descri.setText(String.valueOf(people.get(0).getPreccosto()));


                        EditText ingrediente=(EditText) findViewById(R.id.ingredientese);
                        ingrediente.setText(people.get(0).getIngredientes());
                       String fotow=people.get(0).getDescripcion();

                     Picasso.get().load(fotow).transform(new CropSquareTransformation()).resize(200, 200)
                             .into( foto_gallery);


                    }

                } catch (JSONException e) {
                    e.printStackTrace()                ;

                     e.toString();
                }

            }

        }

    }
    private class cargarfamilias extends AsyncTask<String, String, String> {

        private String[] strArrData = {"No Suggestions"};
        ProgressDialog pdLoading = new ProgressDialog(Editarproducto.this);
        HttpURLConnection conn;
        URL url = null;
        ArrayList<Familia> listaalmacen = new ArrayList<Familia>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tCargando Familias de Productos");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            Spinner  spin=(Spinner) findViewById(R.id.spinnerio2);
            try {
                url = new URL("https://www.sodapop.pe/sugest/apitraertodaslasfamiliasporempresa.php");
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

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()

                        .appendQueryParameter("idempresa", params[0]);
                String query = builder.build().getEncodedQuery();
                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
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
            pdLoading.dismiss();
            Spinner spin=(Spinner) findViewById(R.id.spinnerio2e);

            ArrayList<String> dataList = new ArrayList<String>();
            Familia mes;
            if(result.equals("no rows")) {
                Toast.makeText(Editarproducto.this,"no existen datos a mostrar",Toast.LENGTH_LONG).show();
            }else{
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                        ;

                        // dataList.add(json_data.getString("nombrealm"));
                        mes = new Familia(
                                json_data.getInt("idfamilia"),
                                json_data.getInt("idempresa"),
                                json_data.getString("nombrefamilia"),
                                json_data.getString("estadofamilia"));
                        listaalmacen.add(mes);
                    }
                    strArrData = dataList.toArray(new String[dataList.size()]);
                    ArrayAdapter<Familia> adaptadorl= new ArrayAdapter<Familia>(Editarproducto.this, android.R.layout.simple_spinner_item,listaalmacen );
                    spin.setAdapter(adaptadorl);
                } catch (JSONException e) {
                }

            }

        }

    }




    public class actualizaproducto extends AsyncTask<Productoguardar, Void, String> {
        String resultado;
        HttpURLConnection conne;
        URL url = null;
        Productoguardar ped;
        ProgressDialog pdLoading = new ProgressDialog(Editarproducto.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tactualizando producto..");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(Productoguardar... params) {
            ped=params[0];
            try {
                url = new URL("https://sodapop.pe/sugest/apiactualizarproducto.php");
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
                        .appendQueryParameter("nombreproducto",String.valueOf(ped.getNombreproducto()))
                        .appendQueryParameter("estadoproducto",String.valueOf(ped.getEstadoproducto()))
                        .appendQueryParameter("precventa",String.valueOf(ped.getPrecventa()))
                        .appendQueryParameter("imagen",String.valueOf(ped.getImagen()))
                        .appendQueryParameter("idfamilia",String.valueOf(ped.getIdfamilia()))
                        .appendQueryParameter("preccosto",String.valueOf(ped.getPreccosto()))
                        .appendQueryParameter("descripcion",String.valueOf(ped.getDescripcion()))
                        .appendQueryParameter("codigobarras", String.valueOf(ped.getCodigobarras()))
                        .appendQueryParameter("foto",String.valueOf(ped.getFoto()))
                        .appendQueryParameter("qr",String.valueOf(ped.getQr()))
                        .appendQueryParameter("idproveedor",String.valueOf(ped.getIdproveedor()))
                        .appendQueryParameter("ingredientes", String.valueOf(ped.getIngredientes()))
                        .appendQueryParameter("idalmacen",String.valueOf(ped.getIdalmacen()))
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

            if(resultado.equals("true")){
                Log.d("ii", resultado);


                Intent intent = new Intent(Editarproducto.this, Listaproductos.class);
                startActivity(intent);




            }else{


            }



        }
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    private void checkExternalStoragePermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        225);
            }
        }if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        226);
            }
        }

    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "MyPicture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
                photoURI = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                //Uri photoURI = FileProvider.getUriForFile(AddActivity.this, "com.example.android.fileprovider", photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
            }
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Producto" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            foto_gallery.setImageURI(imageUri);
        }




        if (requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == RESULT_OK) {

            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
                foto_gallery.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*if (requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras(); // Aquí es null
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                mPhotoImageView.setImageBitmap(imageBitmap);
            }*/

        }

    }


}