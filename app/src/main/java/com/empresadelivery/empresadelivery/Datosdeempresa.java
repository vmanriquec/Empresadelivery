package com.empresadelivery.empresadelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.empresadelivery.empresadelivery.adaptadores.Adaptadortipodepago;
import com.empresadelivery.empresadelivery.modelos.Almacen;
import com.empresadelivery.empresadelivery.modelos.Crudtiposdepagorealm;
import com.empresadelivery.empresadelivery.modelos.DescuentosRealm;
import com.empresadelivery.empresadelivery.modelos.Empresa;
import com.empresadelivery.empresadelivery.modelos.Productoguardar;
import com.empresadelivery.empresadelivery.modelos.Rubros;
import com.empresadelivery.empresadelivery.modelos.Tipodepagoempresa;
import com.empresadelivery.empresadelivery.modelos.Tiposdepago;
import com.empresadelivery.empresadelivery.modelos.Tiposedepagorealm;
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

import io.realm.Realm;
import io.realm.RealmResults;
import android.content.SharedPreferences;
public class Datosdeempresa extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    String FileName = "myfile";
    SharedPreferences prefs;
    ImageView foto_gallery;
    private Uri photoURI;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    public ArrayList<Tiposdepago> todoslotiposdepago = new ArrayList<>();

    private String mCurrentPhotoPath;
    public static final int REQUEST_CODE_TAKE_PHOTO = 0 /*1*/;
Button guardarempresa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datosdeempresa);
        foto_gallery = (ImageView) findViewById(R.id.foto1);
        Button eleccion = (Button) findViewById(R.id.camara1);

        new cargarrubro().execute();
Button sacarubicacion=(Button)findViewById(R.id.sacarubicacion);
sacarubicacion.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent ino= new Intent(Datosdeempresa.this,Mapacitonulo.class);
        startActivity(ino);


    }
});

        SharedPreferences prefs;
        String FileName = "myfile";
        prefs = this.getSharedPreferences(FileName, Context.MODE_PRIVATE);
guardarempresa=(Button) findViewById(R.id.guardarproducto2);
        TextView dire=(TextView)findViewById(R.id.direccionempresal);

        String direccionempresar=prefs.getString("direccionempresa", "");
        String referenciaempresa = prefs.getString("referenciaempresa", "");
        String latitudempresa = prefs.getString("latitudempresa", "");
        String longitudempresa=prefs.getString("longitudempresa", "");
        dire.setText(direccionempresar);







        guardarempresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dire.getText().toString().equals("")){

                    BottomSheetFragment bottomSheetDialog = BottomSheetFragment.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putString("test", "debes agregar un nombre");
                    bundle.putString("nombreusuario", "");
                    bundle.putString("imagen", getResources().getString(R.string.gifadmiracion));


                    bottomSheetDialog.setArguments(bundle);
                    bottomSheetDialog.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");




                }


                else{

                BottomSheetFragment bottomSheetDialog = BottomSheetFragment.newInstance();

                // String nombre = prefs.getString("nombreusuariof", "");

                Bundle bundle = new Bundle();
                bundle.putString("test", "Espera un momento por favor estoy Guardando tu informacion");
                bundle.putString("nombreusuario", "");

                bottomSheetDialog.setArguments(bundle);
                bottomSheetDialog.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");

                final FirebaseStorage storage= FirebaseStorage.getInstance();

                StorageReference storageRef = storage.getReference();
                TextView razon=(TextView)findViewById(R.id.razonsociall);

                TextView telefemp=(TextView)findViewById(R.id.telefonoempresal);
                TextView corre=(TextView)findViewById(R.id.emailempresal);
                TextView pagin=(TextView)findViewById(R.id.paginawebempresl);
                TextView slog=(TextView)findViewById(R.id.sloganempresal);
                TextView admine=(TextView)findViewById(R.id.nombreadministradorl);
                TextView teleadmin=(TextView)findViewById(R.id.telefonoadministradorl);
                TextView montomin=(TextView)findViewById(R.id.montominimol);
                TextView tiempodem=(TextView)findViewById(R.id.demoraempresal);
                TextView costodeliveryempresa=(TextView)findViewById(R.id.costodeliveryempresa);

               String razonsocialempresa=razon.getText().toString();
                String direccionempresa=dire.getText().toString();
                String telefonoempresa=telefemp.getText().toString();
                String correoempresa=corre.getText().toString();
                String paginawebempresa=pagin.getText().toString();
                String sloganempresa=slog.getText().toString();
                String nombreadministradorempresa=admine.getText().toString();
                String telefonoadministradorempresa=teleadmin.getText().toString();
                String montominimodeliveryempresa=montomin.getText().toString();
                String tiempoestimadodedemora=tiempodem.getText().toString();
                String costodelivery=costodeliveryempresa.getText().toString();

                StorageReference mountainsRef = storageRef.child(razonsocialempresa+".jpg");
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


                                        if(imageUrl.equals("")){

                                            Toast.makeText(Datosdeempresa.this,"SELECCIONA UNA IMAGEN POR FAVOR",Toast.LENGTH_LONG).show();
                                        }
                                        else{
                                            Spinner almacen=(Spinner)findViewById(R.id.spinnerrubroempresal);
                                            String al =almacen.getItemAtPosition(almacen.getSelectedItemPosition()).toString();
                                            String mesei=al;
                                            int g= mesei.length();
                                            String mesi = mesei.substring(0,2);

                                            String  idrubro=mesi.trim();

                                            String mesio = mesei.substring(3,g);
                                            String nombrerubro=mesio.trim();





                                            //                      pdLoading.dismiss();
                                            Empresa pg=new Empresa(0,razonsocialempresa,direccionempresa,telefonoempresa,correoempresa
                                                    ,paginawebempresa,"habilitado",sloganempresa,nombreadministradorempresa
                                                    ,telefonoadministradorempresa,imageUrl,idrubro,montominimodeliveryempresa,tiempoestimadodedemora,costodelivery,latitudempresa,longitudempresa);
                                            new grabarempresa().execute(pg);


                                        }

                                    }
                                });
                            }
                        }





                    }
                });


            }

            }
        });


        eleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(Datosdeempresa.this);
                builder.setMessage("Elija una Opcion")
                        .setCancelable(false)
                        .setPositiveButton("Tomar foto", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (ContextCompat.checkSelfPermission(Datosdeempresa.this,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(Datosdeempresa.this,
                                        Manifest.permission.CAMERA)
                                        != PackageManager.PERMISSION_GRANTED) {


                                    if (ActivityCompat.shouldShowRequestPermissionRationale(Datosdeempresa.this,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                                    } else {
                                        ActivityCompat.requestPermissions(Datosdeempresa.this,
                                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                225);
                                    }


                                    if (ActivityCompat.shouldShowRequestPermissionRationale(Datosdeempresa.this,
                                            Manifest.permission.CAMERA)) {

                                    } else {
                                        ActivityCompat.requestPermissions(Datosdeempresa.this,
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

    private void openGallery() {
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
        String imageFileName = "Empresa" + timeStamp + "_";
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


    private class cargarrubro extends AsyncTask<String, String, String> {

        private String[] strArrData = {"No Suggestions"};
        ProgressDialog pdLoading = new ProgressDialog(Datosdeempresa.this);
        HttpURLConnection conn;
        URL url = null;
        ArrayList<Rubros> listaalmacen = new ArrayList<Rubros>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tCargando Rubros");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                url = new URL("https://www.sodapop.pe/sugest/apitraerrubros.php");
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
                    return ("Connection error");
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
            Spinner spin = (Spinner) findViewById(R.id.spinnerrubroempresal);

            ArrayList<String> dataList = new ArrayList<String>();
            Rubros mes;
            if (result.equals("no rows")) {
                Toast.makeText(Datosdeempresa.this, "no existen datos a mostrar", Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                        dataList.add(json_data.getString("nombrerubro"));
                        mes = new Rubros(
                                json_data.getInt("idrubroempresa"), json_data.getString("nombrerubro"), json_data.getString("estadorubro"));
                        listaalmacen.add(mes);
                    }
                    strArrData = dataList.toArray(new String[dataList.size()]);
                    ArrayAdapter<Rubros> adaptadorl = new ArrayAdapter<Rubros>(Datosdeempresa.this, android.R.layout.simple_spinner_item, listaalmacen);
                    spin.setAdapter(adaptadorl);
                } catch (JSONException e) {
                }

            }

        }

    }







    public class grabarempresa extends AsyncTask<Empresa, Void, String> {
        String resultado;
        HttpURLConnection conne;
        URL url = null;
        Empresa ped;
        ProgressDialog pdLoading = new ProgressDialog(Datosdeempresa.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tRegistrando Empresa..");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(Empresa... params) {
            ped = params[0];
            try {
                url = new URL("https://sodapop.pe/sugest/apiguardarempresa.php");
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

                        .appendQueryParameter("razonsocialempresa", String.valueOf(ped.getRazonsocialempresa()))
                        .appendQueryParameter("direccionempresa", String.valueOf(ped.getDireccionempresa()))
                        .appendQueryParameter("telefonoempresa", String.valueOf(ped.getTelefonoempresa()))
                        .appendQueryParameter("emailempresa", String.valueOf(ped.getEmailempresa()))
                        .appendQueryParameter("paginawebempresa", String.valueOf(ped.getPaginawebempresa()))
                        .appendQueryParameter("estadoempresa", String.valueOf(ped.getEstadoempresa()))
                        .appendQueryParameter("sloganempresa", String.valueOf(ped.getSloganempresa()))
                        .appendQueryParameter("nombreadministrador", String.valueOf(ped.getNombreadministrador()))
                        .appendQueryParameter("telefonoadministrador", String.valueOf(ped.getTelefonoadministrador()))
                        .appendQueryParameter("logotipoempresa", String.valueOf(ped.getLogotipoempresa()))
                        .appendQueryParameter("idrubroempresa", String.valueOf(ped.getIdrubroempresa()))
                        .appendQueryParameter("montominimodeventa", String.valueOf(ped.getMontominimodeventa()))
                        .appendQueryParameter("tiempodedemoraempresa", String.valueOf(ped.getTiempodedemoraempresa()))
                        .appendQueryParameter("costodelivery", String.valueOf(ped.getCostodelivery()))
                        .appendQueryParameter("latitudempresa", String.valueOf(ped.getLatitudempresa()))
                        .appendQueryParameter("longitudempresa", String.valueOf(ped.getLongitudempresa()))

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

          String idempresa;

            idempresa=resultado.toString();
            Spinner almacen=(Spinner)findViewById(R.id.spinnerrubroempresal);
            String al =almacen.getItemAtPosition(almacen.getSelectedItemPosition()).toString();
            String mesei=al;
            int g= mesei.length();
            String mesi = mesei.substring(0,2);

            String  idrubro=mesi.trim();

            String mesio = mesei.substring(3,g);
            String nombrerubro=mesio.trim();



            grabarempresaenshared(idempresa,ped.getRazonsocialempresa(),ped.getDireccionempresa(),ped.getEstadoempresa()
                    ,ped.getSloganempresa(),ped.getNombreadministrador(),ped.getIdrubroempresa(),ped.getMontominimodeventa(),
                    ped.getLogotipoempresa(),nombrerubro);
            Intent intent = new Intent(Datosdeempresa.this, Iniciosistema.class);
            startActivity(intent);

            //Log.d("ii", resultado);
                  //      String idempresa = prefs.getString("idempresa", "");



                  }
    }




        public   void grabarempresaenshared(String idempresa,
            String razonsocialempresa,
                                        String direccionempresa,
                                        String estadoempresa,
                                        String sloganempresa,
                                        String nombreadministrador
                                        ,String idrubroempresa,
                                        String montominimodeventa,String imagenempresa,String nombrerubro){
        SharedPreferences sharedPreferences =getSharedPreferences(FileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("idempresa",idempresa);
        editor.putString("razonsocialempresa",razonsocialempresa);
        editor.putString("direccionempresa",direccionempresa);
        editor.putString("estadoempresa",estadoempresa);
        editor.putString("sloganempresa",sloganempresa);
        editor.putString("nombreadministrador",nombreadministrador);
        editor.putString("idrubroempresa",idrubroempresa);
        editor.putString("montominimodeventa",montominimodeventa);
        editor.putString("imagenempresa",imagenempresa);
        editor.putString("nombrerubro",nombrerubro);
        editor.commit();

    }




}
