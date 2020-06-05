package com.empresadelivery.empresadelivery;

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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.empresadelivery.empresadelivery.modelos.Almacen;
import com.empresadelivery.empresadelivery.modelos.Familia;
import com.empresadelivery.empresadelivery.modelos.Productoguardar;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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

public class Subirproductos extends AppCompatActivity {
    private static final String TAG = "";
    String FileName = "myfile";
    SharedPreferences prefs;

    Button eleccion,guardarproducto;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    ImageView foto_gallery;
    TextView precioproducto,descripcionproducto,nombreproducto,ingredientes;
    Spinner almacen,familia;


    public static final int REQUEST_CODE_TAKE_PHOTO = 0 /*1*/;
    private String mCurrentPhotoPath;
    private Uri photoURI;


    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subirproductos);
        prefs = this.getSharedPreferences(FileName, Context.MODE_PRIVATE);

        foto_gallery=(ImageView)findViewById(R.id.foto);
precioproducto=(TextView)findViewById(R.id.precio);
descripcionproducto=(TextView)findViewById(R.id.descripcion);
        nombreproducto=(TextView)findViewById(R.id.nombreproducto);
        ingredientes=(TextView)findViewById(R.id.ingredientes);
almacen =(Spinner) findViewById(R.id.spinnerio);
        familia =(Spinner) findViewById(R.id.spinnerio2);
        eleccion=(Button)findViewById(R.id.camara);

guardarproducto=(Button)findViewById(R.id.guardarproducto);
        new cargaralmacen().execute();

        new cargarfamilias().execute();


guardarproducto.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        final FirebaseStorage storage= FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();

// Create a reference to "mountains.jpg"
        String nombre=nombreproducto.getText().toString();
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

                                Spinner almacen=(Spinner)findViewById(R.id.spinnerio);
                                String al =almacen.getItemAtPosition(almacen.getSelectedItemPosition()).toString();
                                String mesei=al;
                                int g= mesei.length();
                                String mesi = mesei.substring(0,2);

                                String  idalmacen=mesi.trim();

                                String mesio = mesei.substring(3,g);
                                String nombrealmacen=mesio.trim();



                                Spinner familia=(Spinner)findViewById(R.id.spinnerio2);
                                String alf =familia.getItemAtPosition(familia.getSelectedItemPosition()).toString();
                                String meseif=alf;
                                int gf= meseif.length();
                                String mesif = meseif.substring(0,2);

                                String  idfamilia=mesif.trim();

                                String mesiof = meseif.substring(3,gf);
                                String nombrefamilia=mesiof.trim();

          //                      pdLoading.dismiss();



                                 String idempresa=prefs.getString("idempresa","");
                                Productoguardar pg=new Productoguardar(0,nombreproducto.getText().toString(),"1",Double.parseDouble(precioproducto.getText().toString())
                                        ,"no",Integer.parseInt(idfamilia),Double.parseDouble(descripcionproducto.getText().toString()),imageUrl,"no hay","sin foto","sin qr"
                                ,1,ingredientes.getText().toString(),Integer.parseInt(idalmacen),Integer.parseInt(idempresa));
                                new grabarproducto().execute(pg);

                            }
                        });
                    }
                }





                    }
        });












    }
});

        eleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(Subirproductos.this);
                builder.setMessage("Elija una Opcion")
                        .setCancelable(false)
                        .setPositiveButton("Tomar foto", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (ContextCompat.checkSelfPermission(Subirproductos.this,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(Subirproductos.this,
                                        Manifest.permission.CAMERA)
                                        != PackageManager.PERMISSION_GRANTED) {


                                    if (ActivityCompat.shouldShowRequestPermissionRationale(Subirproductos.this,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                                    } else {
                                        ActivityCompat.requestPermissions(Subirproductos.this,
                                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                225);
                                    }


                                    if (ActivityCompat.shouldShowRequestPermissionRationale(Subirproductos.this,
                                            Manifest.permission.CAMERA)) {

                                    } else {
                                        ActivityCompat.requestPermissions(Subirproductos.this,
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






    }
    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
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





    private class cargaralmacen extends AsyncTask<String, String, String> {

        private String[] strArrData = {"No Suggestions"};
        ProgressDialog pdLoading = new ProgressDialog(Subirproductos.this);
        HttpURLConnection conn;
        URL url = null;
        ArrayList<Almacen> listaalmacen = new ArrayList<Almacen>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tCargando Locales disponibles");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            Spinner  spin=(Spinner) findViewById(R.id.spinnerio);
            try {
                url = new URL("https://www.sodapop.pe/sugest/fetch-all-fish.php");
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
            pdLoading.dismiss();
            Spinner spin=(Spinner) findViewById(R.id.spinnerio);

            ArrayList<String> dataList = new ArrayList<String>();
            Almacen mes;
            if(result.equals("no rows")) {
                Toast.makeText(Subirproductos.this,"no existen datos a mostrar",Toast.LENGTH_LONG).show();
            }else{
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                        dataList.add(json_data.getString("nombrealm"));
                        mes = new Almacen(json_data.getInt("idalmacen"), json_data.getString("nombrealm"), json_data.getString("telfonoalm"), json_data.getString("correoalm"));
                        listaalmacen.add(mes);
                    }
                    strArrData = dataList.toArray(new String[dataList.size()]);
                    ArrayAdapter<Almacen> adaptadorl= new ArrayAdapter<Almacen>(Subirproductos.this, android.R.layout.simple_spinner_item,listaalmacen );
                    spin.setAdapter(adaptadorl);
                } catch (JSONException e) {
                }

            }

        }

    }

    private class cargarfamilias extends AsyncTask<String, String, String> {

        private String[] strArrData = {"No Suggestions"};
        ProgressDialog pdLoading = new ProgressDialog(Subirproductos.this);
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
                url = new URL("https://www.sodapop.pe/sugest/apitraertodalasfamilias.php");
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
            pdLoading.dismiss();
            Spinner spin=(Spinner) findViewById(R.id.spinnerio2);

            ArrayList<String> dataList = new ArrayList<String>();
            Familia mes;
            if(result.equals("no rows")) {
                Toast.makeText(Subirproductos.this,"no existen datos a mostrar",Toast.LENGTH_LONG).show();
            }else{
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                       // dataList.add(json_data.getString("nombrealm"));
                        mes = new Familia(json_data.getInt("idfamilia"), json_data.getString("nombrefamilia"));
                        listaalmacen.add(mes);
                    }
                    strArrData = dataList.toArray(new String[dataList.size()]);
                    ArrayAdapter<Familia> adaptadorl= new ArrayAdapter<Familia>(Subirproductos.this, android.R.layout.simple_spinner_item,listaalmacen );
                    spin.setAdapter(adaptadorl);
                } catch (JSONException e) {
                }

            }

        }

    }
    private void checkExternalStoragePermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            Log.e(TAG, "Permission not granted WRITE_EXTERNAL_STORAGE.");
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
            Log.e(TAG, "Permission not granted CAMERA.");
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
    public class grabarproducto extends AsyncTask<Productoguardar, Void, String> {
        String resultado;
        HttpURLConnection conne;
        URL url = null;
        Productoguardar ped;
        ProgressDialog pdLoading = new ProgressDialog(Subirproductos.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tsubiendo producto..");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(Productoguardar... params) {
            ped=params[0];
            try {
                url = new URL("https://sodapop.pe/sugest/apiguardarproducto.php");
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







            }else{
           //     String ii =resultado.toString();
             //   Log.d("jj", "producto valido");


                // lanzarsistema();
                AlertDialog.Builder builder = new AlertDialog.Builder(Subirproductos.this);
                builder.setMessage("Agregar Adicionales y Cremas?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Intent ListSong = new Intent(getApplicationContext(), Agregaradicionales.class);
                                ListSong.putExtra("idproducto", resultado);
                                ListSong.putExtra("nombreproducto",nombreproducto.getText().toString());
                                startActivity(ListSong);

                            }


                        })

                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Intent ListSong = new Intent(getApplicationContext(), Listaproductos.class);
                                startActivity(ListSong);

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


            }



        }
    }




}

