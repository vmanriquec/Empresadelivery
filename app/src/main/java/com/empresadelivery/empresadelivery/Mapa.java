package com.empresadelivery.empresadelivery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.Manifest.permission;
import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class Mapa extends FragmentActivity
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private static final int REQUEST_FINE_LOCATION = 0;
    String FileName = "myfile";
    SharedPreferences prefs;
    String apiKey = "MY API KEY";
    private GoogleMap mapa;
    private FusedLocationProviderClient mFusedLocationClient;
    TextView direccion;
    EditText referencia;
    Button listo, aregistro;
    //private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    //private long FASTEST_INTERVAL = 2000; /* 2 sec */

    public Double longituduser;
    public Double latituduser;


    LocationRequest mLocationRequest;
    private final LatLng Sopdapop = new LatLng(-11.495692495131408, -77.208248);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        Button atras=(Button) findViewById(R.id.atras);
        String longitudiso = getIntent().getStringExtra("longitud");
        String latitudiso = getIntent().getStringExtra("latitud");
        String nombreiso = getIntent().getStringExtra("nombre");
        String dire = getIntent().getStringExtra("direccion");
        float la = Float.parseFloat(latitudiso);
        float lon = Float.parseFloat(longitudiso);
        final LatLng cliente = new LatLng(la, lon);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);


        direccion = (TextView) findViewById(R.id.direc);
        listo = (Button) findViewById(R.id.listo);

        mFusedLocationClient = getFusedLocationProviderClient(this);

//        checkRunTimePermission();



        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Intent intent = new Intent (v.getContext(), Manejodeusuarios.class);
                //startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        checkPermissions();
        String longitudiso = getIntent().getStringExtra("longitud");
        String latitudiso = getIntent().getStringExtra("latitud");
        String nombreiso = getIntent().getStringExtra("nombre");
        String dire = getIntent().getStringExtra("direccion");
        String refe = getIntent().getStringExtra("referencia");
        direccion.setText(dire);
        float la = Float.parseFloat(latitudiso);
        float lon = Float.parseFloat(longitudiso);
        final LatLng cliente = new LatLng(la, lon);

        Toast.makeText(this, "Activa tu gps por favor", Toast.LENGTH_LONG).show();
        mapa = googleMap;
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mapa.getUiSettings().setZoomControlsEnabled(true);
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(cliente, 15));
        //  mapa.setMyLocationEnabled(true);
        // mapa.getUiSettings().setCompassEnabled(true);

        Marker marker = mapa.addMarker(new MarkerOptions()

                .position(cliente)
                .title(nombreiso)
                .snippet(refe)

                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_success))
                .anchor(0.5f, 0.5f)
        );

        // mapa.setInfoWindowAdapter(new Marketclaselocal(LayoutInflater.from(getApplicationContext())));
        marker.showInfoWindow();

        Polyline line = mapa.addPolyline(new PolylineOptions()
                .add(new LatLng(-11.495692495131408,-77.208248), new LatLng(la, lon))
                .width(4)
                .color(Color.RED));


        String url ="https://maps.googleapis.com/maps/api/directions/json?origin=" +
                ""+latitudiso+","+longitudiso+"&destination=-11.495692495131408,-77.208248";

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject        jso = new JSONObject(response);
                    trazarRuta(jso);
                    Log.i("jsonRuta: ",""+response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }


        });

        queue.add(stringRequest);






    }









    public void onLocationChanged(Location location) {


    }


    public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);

        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }


    private boolean checkPermissions() {

        if ((ContextCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) ){


            return true;

        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission.ACCESS_FINE_LOCATION},REQUEST_FINE_LOCATION);
            return false;

        }

    }

    private void revaamapa() {
        Intent intent = new Intent(getApplicationContext(), Mapa.class);

        startActivity(intent);

    }


    public void guardardireccionlatitudylongitud(String direccion, String referencia, Double latitud, Double longitud) {
        SharedPreferences sharedPreferences = getSharedPreferences(FileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("direccion", direccion);
        editor.putString("referencia", referencia);
        editor.putString("latitud", String.valueOf(latitud));
        editor.putString("longitud", String.valueOf(longitud));
        editor.commit();

    }


    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.gpsc);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40,
                vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }




    private void trazarRuta(JSONObject jso) {

        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;

        try {
            jRoutes = jso.getJSONArray("routes");
            for (int i = 0; i < jRoutes.length(); i++) {

                jLegs = ((JSONObject) (jRoutes.get(i))).getJSONArray("legs");
                for (int j = 0; j < jLegs.length(); j++) {
                    jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");
                    for (int k = 0; k < jSteps.length(); k++) {


                        String polyline = "" + ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
                        Log.i("end", "" + polyline);
                        List<LatLng> list = PolyUtil.decode(polyline);
                        mapa.addPolyline(new PolylineOptions().addAll(list).color(Color.RED).width(5));

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}